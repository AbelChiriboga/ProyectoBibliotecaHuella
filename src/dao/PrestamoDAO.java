package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import modelo.EstadoPrestamo;
import modelo.Multa;
import modelo.Prestamo;
import modelo.ValorBD;

/**
 * DAO de préstamos. No implementa CrudDAO a propósito: un préstamo nunca se edita ni se borra,
 * solo se REGISTRA y luego se DEVUELVE (y queda como historial).
 *
 * Toda la validación fuerte la hacen los triggers de la BD (huella, estudiante activo,
 * ejemplar disponible, máximo de préstamos, fecha límite, multa); si algo falla, el mensaje
 * del trigger llega como NegocioException gracias a BaseDAO.traducir().
 */
public class PrestamoDAO extends BaseDAO<Prestamo, Integer> {

    static final String COLUMNAS =
            "p.id_prestamo AS pre_id, p.fecha_prestamo AS pre_fecha, p.fecha_limite AS pre_limite, "
            + "p.fecha_devolucion AS pre_devolucion, p.estado AS pre_estado, p.huella_prestamo AS pre_huella_p, "
            + "p.huella_devolucion AS pre_huella_d, p.observaciones AS pre_obs, "
            + EjemplarDAO.COLUMNAS + ", " + EstudianteDAO.COLUMNAS + ", "
            + UsuarioDAO.COLUMNAS + ", " + MultaDAO.COLUMNAS;

    /** Tablas del préstamo; el JOIN de multas es LEFT porque la mayoría no tiene multa. */
    static final String TABLAS = "prestamos p "
            + "JOIN ejemplares ej ON ej.id_ejemplar = p.id_ejemplar "
            + "JOIN libros l ON l.id_libro = ej.id_libro " + LibroDAO.JOINS + " "
            + "JOIN estudiantes e ON e.id_estudiante = p.id_estudiante "
            + "JOIN carreras ca ON ca.id_carrera = e.id_carrera "
            + "LEFT JOIN usuarios u ON u.cedula = p.cedula_usuario "
            + "LEFT JOIN multas m ON m.id_prestamo = p.id_prestamo";

    private static final String INSERT = "INSERT INTO prestamos "
            + "(id_estudiante, id_ejemplar, cedula_usuario, huella_prestamo, observaciones) VALUES (?,?,?,?,?)";

    private static final String DEVOLVER = "UPDATE prestamos SET estado = ?, huella_devolucion = 1 "
            + "WHERE id_prestamo = ? AND estado = ?";

    private final EjemplarDAO ejemplarDAO = new EjemplarDAO();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected String sqlSelect() {
        return "SELECT " + COLUMNAS + " FROM " + TABLAS;
    }

    @Override
    protected String columnaId() {
        return "p.id_prestamo";
    }

    @Override
    protected String orden() {
        return "p.fecha_prestamo DESC";
    }

    @Override
    protected Prestamo mapear(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo(ejemplarDAO.mapear(rs), estudianteDAO.mapear(rs),
                rs.getString("usu_cedula") == null ? null : usuarioDAO.mapear(rs));
        p.setId(rs.getInt("pre_id"));
        p.setFechaPrestamo(fecha(rs, "pre_fecha"));
        p.setFechaLimite(fecha(rs, "pre_limite"));
        p.setFechaDevolucion(fecha(rs, "pre_devolucion"));
        p.setEstado(ValorBD.desde(EstadoPrestamo.class, rs.getString("pre_estado")));
        p.setHuellaPrestamo(rs.getBoolean("pre_huella_p"));
        p.setHuellaDevolucion(rs.getBoolean("pre_huella_d"));
        p.setObservaciones(rs.getString("pre_obs"));
        if (rs.getObject("mul_id") != null) {
            Multa m = MultaDAO.mapearMulta(rs);
            m.setPrestamo(p);
            p.setMulta(m);
        }
        return p;
    }

    // ---------- Registrar préstamos ----------

    /** Un préstamo. La fecha límite la calcula el trigger con DIAS_PRESTAMO. */
    public Prestamo insertar(Prestamo p) {
        p.setId(insertarConId(INSERT, valoresInsert(p)));
        return p;
    }

    /**
     * Varios préstamos del mismo estudiante en UNA transacción: si el trigger rechaza uno
     * (por ejemplo, superó el máximo), no se guarda ninguno.
     */
    public void insertarVarios(List<Prestamo> prestamos) {
        enTransaccion(cn -> {
            for (Prestamo p : prestamos) {
                p.setId(insertarConId(cn, INSERT, valoresInsert(p)));
            }
        });
    }

    private Object[] valoresInsert(Prestamo p) {
        return new Object[]{p.getEstudiante().getIdEstudiante(), p.getEjemplar().getId(),
            p.getUsuario() == null ? null : p.getUsuario().getCedula(), p.isHuellaPrestamo(), p.getObservaciones()};
    }

    // ---------- Devolver ----------

    /**
     * Devuelve varios préstamos en UNA transacción. Marca huella_devolucion = 1;
     * los triggers ponen la fecha, liberan el ejemplar y generan la multa si hay atraso.
     */
    public void registrarDevoluciones(List<Integer> idsPrestamo) {
        enTransaccion(cn -> {
            for (Integer id : idsPrestamo) {
                ejecutar(cn, DEVOLVER, bd(EstadoPrestamo.DEVUELTO), id, bd(EstadoPrestamo.ACTIVO));
            }
        });
    }

    // ---------- Consultas ----------

    public List<Prestamo> listarActivosPorEstudiante(int idEstudiante) {
        return consultarLista(sqlSelect() + " WHERE p.id_estudiante = ? AND p.estado = ? ORDER BY p.fecha_limite",
                idEstudiante, bd(EstadoPrestamo.ACTIVO));
    }

    public int contarActivos(int idEstudiante) {
        return consultarEntero("SELECT COUNT(*) FROM prestamos WHERE id_estudiante = ? AND estado = ?",
                idEstudiante, bd(EstadoPrestamo.ACTIVO));
    }

    /** Historial completo de un estudiante (panel del estudiante). */
    public List<Prestamo> listarPorEstudiante(int idEstudiante) {
        return consultarLista(sqlSelect() + " WHERE p.id_estudiante = ? ORDER BY " + orden(), idEstudiante);
    }

    /** Préstamos ya devueltos con multa SIN PAGAR (pantalla Atrasos → Multas pendientes). */
    public List<Prestamo> listarConMultaPendiente() {
        return consultarLista(sqlSelect() + " WHERE m.id_multa IS NOT NULL AND m.pagada = 0 ORDER BY m.fecha_generacion");
    }

    /** Todos los préstamos activos (para contar por estudiante en la consulta de estudiantes). */
    public List<Prestamo> listarActivos() {
        return consultarLista(sqlSelect() + " WHERE p.estado = ? ORDER BY p.fecha_limite", bd(EstadoPrestamo.ACTIVO));
    }

    /**
     * Préstamos atrasados (pantalla Atrasos). Usa la vista v_prestamos_activos de la BD,
     * que ya calcula dias_restantes (negativo = atrasado).
     */
    public List<Prestamo> listarAtrasados(String texto) {
        String t = like(texto);
        return consultarLista(sqlSelect()
                + " JOIN v_prestamos_activos va ON va.id_prestamo = p.id_prestamo"
                + " WHERE va.dias_restantes < 0 AND (e.cedula LIKE ? OR CONCAT(e.nombres, ' ', e.apellidos) LIKE ?"
                + " OR l.titulo LIKE ?) ORDER BY va.dias_restantes", t, t, t);
    }
}
