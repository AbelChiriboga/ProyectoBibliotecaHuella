package servicio;

import dao.EjemplarDAO;
import excepciones.NegocioException;
import java.util.List;
import java.util.function.Predicate;
import modelo.Ejemplar;
import modelo.EstadoEjemplar;
import modelo.Libro;
import modelo.Usuario;

/**
 * Ejemplares (copias físicas) de un libro. Reglas:
 *  - El estado PRESTADO no se asigna a mano: lo ponen los triggers al prestar y devolver.
 *  - Un ejemplar prestado no se edita ni se elimina hasta que se devuelva.
 */
public class EjemplarService extends ServicioCrud<Ejemplar> {

    private final EjemplarDAO ejemplarDAO;

    public EjemplarService() {
        this(new EjemplarDAO());
    }

    public EjemplarService(EjemplarDAO dao) {
        super(dao);
        this.ejemplarDAO = dao;
    }

    @Override
    protected Predicate<Usuario> permiso() {
        return Usuario::puedeGestionarCatalogo;
    }

    @Override
    protected String accion() {
        return "gestionar el catálogo";
    }

    public List<Ejemplar> listarPorLibro(Libro libro) {
        return ejemplarDAO.listarPorLibro(libro.getId());
    }

    @Override
    protected void validar(Ejemplar e) {
        if (!e.getEstado().esEditableManualmente()) {
            throw new NegocioException("El estado " + e.getEstado().getDescripcion() + " no se puede elegir.");
        }
        exigirQueNoEstePrestado(e);
    }

    @Override
    protected void antesDeEliminar(Ejemplar e) {
        exigirQueNoEstePrestado(e);
    }

    /** Revisa el estado ACTUAL en la BD (no el del formulario, que el usuario pudo cambiar). */
    private void exigirQueNoEstePrestado(Ejemplar e) {
        if (e.esNuevo()) {
            return;
        }
        ejemplarDAO.buscarPorId(e.getId())
                .filter(guardado -> guardado.getEstado() == EstadoEjemplar.PRESTADO)
                .ifPresent(guardado -> {
                    throw new NegocioException("El ejemplar está prestado. Espere a que lo devuelvan.");
                });
    }
}
