package servicio;

import dao.MultaDAO;
import dao.ParametroDAO;
import dao.PrestamoDAO;
import modelo.OperacionAutenticacion;
import modelo.Parametro;
import modelo.Usuario;

/**
 * Lo que tienen en común el préstamo y la devolución: las dos se atienden SOLO con huella.
 *
 * MÉTODO PLANTILLA: identificar() siempre hace los mismos pasos (permiso → huella 1:N → ficha);
 * lo único que cambia es la operación que se anota en la bitácora, y eso lo dice cada hija
 * con operacion(). Así el código de identificar no se repite en PrestamoService y DevolucionService.
 */
public abstract class AtencionConHuella {

    protected final BiometriaService biometria;
    protected final PrestamoDAO prestamoDAO = new PrestamoDAO();
    protected final MultaDAO multaDAO = new MultaDAO();
    protected final ParametroDAO parametroDAO = new ParametroDAO();

    protected AtencionConHuella() {
        this(new BiometriaService());
    }

    protected AtencionConHuella(BiometriaService biometria) {
        this.biometria = biometria;
    }

    /** PRESTAMO o DEVOLUCION (para la bitácora). */
    protected abstract OperacionAutenticacion operacion();

    /** Nombre de la acción para el mensaje de permiso: "registrar préstamos"... */
    protected abstract String accion();

    /**
     * El estudiante pone el dedo y se arma su ficha. Bloquea hasta que se pone el dedo:
     * llamarlo con util.Tareas.
     */
    public final FichaEstudiante identificar() {
        exigirPermiso();
        BiometriaService.Identificacion id = biometria.identificarEstudiante(operacion());
        return armarFicha(id.getEstudiante(), id.getPuntaje());
    }

    /** Ficha con los datos actuales de la BD (préstamos activos, máximo y multas). */
    protected FichaEstudiante armarFicha(modelo.Estudiante e, int puntaje) {
        return new FichaEstudiante(e, puntaje, prestamoDAO.listarActivosPorEstudiante(e.getIdEstudiante()),
                parametroDAO.obtenerEntero(Parametro.MAX_PRESTAMOS), multaDAO.totalPendiente(e.getIdEstudiante()));
    }

    protected void exigirPermiso() {
        Sesion.getInstancia().exigir(Usuario::puedeGestionarPrestamos, accion());
    }

    /** Sin estudiante identificado con huella no hay préstamo ni devolución. */
    protected static void exigirIdentificado(FichaEstudiante ficha) {
        if (ficha == null) {
            throw new excepciones.ValidacionException("Primero identifique al estudiante con su huella "
                    + "(presione Leer huella y pida que coloque el dedo).");
        }
    }

    /** Anota en la bitácora la operación que salió bien, con el puntaje de la huella. */
    protected void anotarExito(FichaEstudiante ficha, String detalle) {
        biometria.anotar(ficha.getEstudiante().getIdEstudiante(), operacion(),
                modelo.ResultadoAutenticacion.EXITO, ficha.getPuntaje(), detalle);
    }
}
