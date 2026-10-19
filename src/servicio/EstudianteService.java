package servicio;

import dao.CarreraDAO;
import dao.EstudianteDAO;
import dao.HuellaDAO;
import dao.PrestamoDAO;
import excepciones.NegocioException;
import excepciones.ValidacionException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.Carrera;
import modelo.Dedo;
import modelo.EstadoEstudiante;
import modelo.Estudiante;
import modelo.Huella;
import modelo.OperacionAutenticacion;
import modelo.RegistroAutenticacion;
import modelo.ResultadoAutenticacion;
import modelo.Usuario;
import util.Validaciones;

/**
 * Estudiantes. El ADMINISTRADOR los registra (con huella); el BIBLIOTECARIO solo los activa
 * o desactiva. Cada operación exige el permiso polimórfico que corresponde.
 */
public class EstudianteService {

    private final EstudianteDAO dao = new EstudianteDAO();
    private final HuellaDAO huellaDAO = new HuellaDAO();
    private final CarreraDAO carreraDAO = new CarreraDAO();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final dao.RegistroAutenticacionDAO bitacora = new dao.RegistroAutenticacionDAO();

    /** Estudiantes con sus huellas ya cargadas (2 consultas en total, no una por estudiante). */
    public List<Estudiante> listarConHuellas() {
        List<Estudiante> estudiantes = dao.listar();
        Map<Integer, List<Huella>> huellas = huellaDAO.listar().stream()
                .collect(Collectors.groupingBy(Huella::getIdEstudiante));
        for (Estudiante e : estudiantes) {
            huellas.getOrDefault(e.getIdEstudiante(), List.of()).forEach(e::agregarHuella);
        }
        return estudiantes;
    }

    /** Las 5 carreras fijas (para el combo). */
    public List<Carrera> listarCarreras() {
        return carreraDAO.listar();
    }

    /**
     * Inserta o actualiza el estudiante junto con las huellas capturadas en el formulario.
     * Regla: un estudiante NO se guarda sin al menos una huella.
     * La cédula ecuatoriana completa se valida al registrar.
     */
    public Estudiante guardar(Estudiante e, Map<Dedo, byte[]> huellasCapturadas) {
        exigir(Usuario::puedeRegistrarEstudiantes, "registrar estudiantes");
        if (!e.tieneHuella() && huellasCapturadas.isEmpty()) {
            throw new ValidacionException("Registre al menos una huella del estudiante antes de guardar "
                    + "(elija el dedo y presione Capturar huella).");
        }
        if (e.esNuevo()) {
            Validaciones.cedulaEcuatoriana(e.getCedula());
        }
        dao.guardarConHuellas(e, huellasCapturadas);
        huellasCapturadas.keySet().forEach(dedo -> bitacora.insertar(new RegistroAutenticacion(e.getIdEstudiante(),
                OperacionAutenticacion.REGISTRO_HUELLA, ResultadoAutenticacion.EXITO, null,
                dedo.getDescripcion() + ", 3 muestras")));
        return e;
    }

    /** Solo se elimina si nunca pidió préstamos; si tiene historial, se debe desactivar. */
    public void eliminar(Estudiante e) {
        exigir(Usuario::puedeRegistrarEstudiantes, "eliminar estudiantes");
        if (!prestamoDAO.listarPorEstudiante(e.getIdEstudiante()).isEmpty()) {
            throw new NegocioException(e.getNombreCompleto() + " tiene préstamos registrados y no se puede eliminar. "
                    + "Desactívelo en su lugar.");
        }
        dao.eliminar(e.getIdEstudiante());
    }

    /** Activar / desactivar (lo puede hacer el bibliotecario). */
    public void cambiarEstado(Estudiante e, EstadoEstudiante nuevo) {
        exigir(Usuario::puedeCambiarEstadoEstudiantes, "cambiar el estado de los estudiantes");
        dao.cambiarEstado(e.getIdEstudiante(), Validaciones.requerido(nuevo, "el estado"));
        e.setEstado(nuevo);
    }

    private void exigir(java.util.function.Predicate<Usuario> permiso, String accion) {
        Sesion.getInstancia().exigir(permiso, accion);
    }
}
