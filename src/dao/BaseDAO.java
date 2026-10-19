package dao;

import conexion.Conexion;
import excepciones.BibliotecaException;
import excepciones.DatosException;
import excepciones.NegocioException;
import excepciones.ValidacionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase ABSTRACTA con todo lo común a los DAO: obtener la conexión, preparar los parámetros,
 * recorrer el ResultSet, cerrar recursos (try-with-resources) y traducir los errores SQL.
 *
 * Patrón "método plantilla": buscarPorId() y listar() ya están escritos aquí y funcionan
 * para cualquier tabla, porque cada hija solo completa las partes abstractas:
 * sqlSelect(), columnaId(), orden() y mapear().
 */
public abstract class BaseDAO<T, ID> implements ConsultaDAO<T, ID> {

    /** Código de error que lanzan los triggers con SIGNAL SQLSTATE '45000'. */
    private static final String ESTADO_TRIGGER = "45000";
    private static final int ERROR_DUPLICADO = 1062;
    private static final int ERROR_LLAVE_FORANEA = 1451;
    private static final int ERROR_REFERENCIA_INEXISTENTE = 1452;

    // ---------- Partes que completa cada hija ----------

    /** SELECT ... FROM ... JOIN ... sin WHERE ni ORDER BY. */
    protected abstract String sqlSelect();

    /** Columna de la llave primaria, con su alias de tabla (por ejemplo "a.id_autor"). */
    protected abstract String columnaId();

    /** Orden para listar (por ejemplo "a.apellidos, a.nombres"). */
    protected abstract String orden();

    /** Convierte la fila actual del ResultSet en un objeto del modelo. */
    protected abstract T mapear(ResultSet rs) throws SQLException;

    // ---------- Consultas que ya funcionan para todas las hijas ----------

    @Override
    public Optional<T> buscarPorId(ID id) {
        return consultarUno(sqlSelect() + " WHERE " + columnaId() + " = ?", id);
    }

    @Override
    public List<T> listar() {
        return consultarLista(sqlSelect() + " ORDER BY " + orden());
    }

    // ---------- Ayudantes para las hijas ----------

    /** Conexión nueva usando la clase Conexion del proyecto. */
    protected Connection obtenerConexion() {
        Connection cn = new Conexion().conectar();
        if (cn == null) {
            throw new DatosException("No se pudo conectar a la base de datos. Verifique que MySQL (XAMPP) esté encendido.");
        }
        return cn;
    }

    /** Lista de objetos de ESTE DAO. */
    protected List<T> consultarLista(String sql, Object... parametros) {
        return consultar(sql, this::mapear, parametros);
    }

    /** Un solo objeto de ESTE DAO (el primero), o vacío. */
    protected Optional<T> consultarUno(String sql, Object... parametros) {
        List<T> lista = consultarLista(sql, parametros);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    /**
     * Consulta genérica: el Mapeador decide qué objeto se arma con cada fila.
     * Sirve para listas de cualquier tipo (por ejemplo contar o sumar).
     */
    protected <R> List<R> consultar(String sql, Mapeador<R> mapeador, Object... parametros) {
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            asignarParametros(ps, parametros);
            try (ResultSet rs = ps.executeQuery()) {
                List<R> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapeador.mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    /** Un número (COUNT, SUM...) de la primera columna; 0 si viene nulo. */
    protected int consultarEntero(String sql, Object... parametros) {
        List<Integer> r = consultar(sql, rs -> rs.getInt(1), parametros);
        return r.isEmpty() ? 0 : r.get(0);
    }

    /** INSERT, UPDATE o DELETE. Devuelve las filas afectadas. */
    protected int ejecutar(String sql, Object... parametros) {
        try (Connection cn = obtenerConexion()) {
            return ejecutar(cn, sql, parametros);
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    /** Igual que el anterior pero sobre una conexión ya abierta (para transacciones). */
    protected int ejecutar(Connection cn, String sql, Object... parametros) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            asignarParametros(ps, parametros);
            return ps.executeUpdate();
        }
    }

    /** INSERT en una tabla AUTO_INCREMENT. Devuelve el id generado. */
    protected int insertarConId(String sql, Object... parametros) {
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(ps, parametros);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    /** INSERT con id generado sobre una conexión ya abierta (para transacciones). */
    protected int insertarConId(Connection cn, String sql, Object... parametros) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(ps, parametros);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    /**
     * Ejecuta varias sentencias como una sola TRANSACCIÓN: o se guardan todas o ninguna.
     * Se usa, por ejemplo, para prestar o devolver varios libros a la vez.
     */
    protected void enTransaccion(AccionSQL accion) {
        try (Connection cn = obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                accion.ejecutar(cn);
                cn.commit();
            } catch (SQLException | RuntimeException e) {
                cn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    /** Asigna los ? en orden. Los enums y fechas se convierten antes con bd() y ts(). */
    private void asignarParametros(PreparedStatement ps, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i + 1, parametros[i]);
        }
    }

    /**
     * Convierte la SQLException en una excepción propia con un mensaje entendible.
     * Los mensajes de los triggers (SIGNAL '45000') se muestran tal cual: son reglas de negocio.
     */
    protected BibliotecaException traducir(SQLException e) {
        if (ESTADO_TRIGGER.equals(e.getSQLState())) {
            return new NegocioException(e.getMessage(), e);
        }
        if (e.getErrorCode() == ERROR_DUPLICADO) {
            return new ValidacionException("Ya existe un registro con ese dato: " + valorDuplicado(e.getMessage()));
        }
        if (e.getErrorCode() == ERROR_LLAVE_FORANEA) {
            return new NegocioException("No se puede eliminar porque tiene registros relacionados.", e);
        }
        if (e.getErrorCode() == ERROR_REFERENCIA_INEXISTENTE) {
            return new ValidacionException("El registro relacionado (estudiante, libro, autor...) no existe.");
        }
        return new DatosException("Error en la base de datos: " + e.getMessage(), e);
    }

    /** De "Duplicate entry 'admin' for key 'usuario'" saca "admin". */
    private static String valorDuplicado(String mensaje) {
        int a = mensaje.indexOf('\'');
        int b = a < 0 ? -1 : mensaje.indexOf('\'', a + 1);
        return b > a ? mensaje.substring(a + 1, b) : mensaje;
    }

    // ---------- Conversiones Java <-> BD ----------

    /** Enum → texto de la BD (o null). */
    protected static String bd(modelo.ValorBD valor) {
        return valor == null ? null : valor.getValorBD();
    }

    /** LocalDateTime → Timestamp (o null). */
    protected static Timestamp ts(LocalDateTime fecha) {
        return fecha == null ? null : Timestamp.valueOf(fecha);
    }

    /** Columna DATETIME → LocalDateTime (o null). */
    protected static LocalDateTime fecha(ResultSet rs, String columna) throws SQLException {
        Timestamp t = rs.getTimestamp(columna);
        return t == null ? null : t.toLocalDateTime();
    }

    /** Columna INT que puede ser NULL → Integer. */
    protected static Integer enteroONulo(ResultSet rs, String columna) throws SQLException {
        int v = rs.getInt(columna);
        return rs.wasNull() ? null : v;
    }

    /** Texto para buscar con LIKE: "%texto%". */
    protected static String like(String texto) {
        return "%" + (texto == null ? "" : texto.trim()) + "%";
    }

    // ---------- Interfaces funcionales (se usan con lambdas) ----------

    /** Arma un objeto con la fila actual del ResultSet. */
    @FunctionalInterface
    public interface Mapeador<R> {
        R mapear(ResultSet rs) throws SQLException;
    }

    /** Trabajo a ejecutar dentro de una transacción. */
    @FunctionalInterface
    public interface AccionSQL {
        void ejecutar(Connection cn) throws SQLException;
    }
}
