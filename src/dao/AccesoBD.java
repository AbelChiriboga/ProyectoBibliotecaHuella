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

/**
 * Clase ABSTRACTA con lo común a TODO acceso a la BD: obtener la conexión, preparar los
 * parámetros (PreparedStatement), recorrer el ResultSet, cerrar recursos (try-with-resources),
 * transacciones y traducir los errores SQL a excepciones del sistema.
 *
 * BaseDAO hereda de aquí y agrega lo de las tablas de entidades (buscarPorId, listar).
 * IndicadorDAO también hereda de aquí, pero como solo cuenta registros no necesita
 * ser un ConsultaDAO (así no se le obliga a tener métodos que no usa).
 */
public abstract class AccesoBD {

    /** Código de error que lanzan los triggers con SIGNAL SQLSTATE '45000'. */
    private static final String ESTADO_TRIGGER = "45000";

    /** Los mensajes de los triggers vienen sin tildes: se muestran con un texto simple. */
    private static final java.util.Map<String, String> MENSAJES_TRIGGER = java.util.Map.of(
            "El prestamo requiere verificacion de huella", "Ingrese la huella del estudiante.",
            "La devolucion requiere verificacion de huella", "Ingrese la huella del estudiante.",
            "El estudiante no esta activo", "El estudiante está inactivo.",
            "El ejemplar no esta disponible", "El libro no está disponible.",
            "El estudiante alcanzo el maximo de prestamos activos", "El estudiante llegó al máximo de préstamos.",
            "Un prestamo devuelto no se puede reactivar", "Ese préstamo ya fue devuelto.");
    private static final int ERROR_DUPLICADO = 1062;
    private static final int ERROR_LLAVE_FORANEA = 1451;
    private static final int ERROR_REFERENCIA_INEXISTENTE = 1452;

    // ---------- Ayudantes para las hijas ----------

    /** Conexión nueva usando la clase Conexion del proyecto. */
    protected Connection obtenerConexion() {
        Connection cn = new Conexion().conectar();
        if (cn == null) {
            throw new DatosException("No hay conexión con la base de datos. Encienda MySQL (XAMPP).");
        }
        return cn;
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
            return new NegocioException(MENSAJES_TRIGGER.getOrDefault(e.getMessage(), e.getMessage()), e);
        }
        if (e.getErrorCode() == ERROR_DUPLICADO) {
            return new ValidacionException("Ya existe un registro con " + valorDuplicado(e.getMessage()) + ".");
        }
        if (e.getErrorCode() == ERROR_LLAVE_FORANEA) {
            return new NegocioException("No se puede eliminar porque está en uso.", e);
        }
        if (e.getErrorCode() == ERROR_REFERENCIA_INEXISTENTE) {
            return new ValidacionException("El dato seleccionado ya no existe.");
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
