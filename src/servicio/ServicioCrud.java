package servicio;

import dao.CrudDAO;
import java.util.List;
import java.util.function.Predicate;
import modelo.Entidad;
import modelo.Usuario;
import util.Validaciones;

/**
 * Servicio CRUD GENÉRICO y ABSTRACTO para las entidades con id numérico.
 * Define el flujo común (método plantilla):
 *
 *   guardar():  verificar permiso → validar (gancho) → insertar si es nuevo, si no actualizar
 *   eliminar(): verificar permiso → antesDeEliminar (gancho) → eliminar
 *
 * Cada hija solo dice qué permiso exige y, si hace falta, redefine los ganchos
 * validar() y antesDeEliminar() con sus reglas propias.
 */
public abstract class ServicioCrud<T extends Entidad> {

    protected final CrudDAO<T, Integer> dao;

    protected ServicioCrud(CrudDAO<T, Integer> dao) {
        this.dao = dao;
    }

    // ---------- Lo que define cada hija ----------

    /** Permiso necesario para modificar (por ejemplo Usuario::puedeGestionarCatalogo). */
    protected abstract Predicate<Usuario> permiso();

    /** Texto de la acción para el mensaje de "sin permiso" (por ejemplo "gestionar el catálogo"). */
    protected abstract String accion();

    /** Gancho: reglas extra antes de guardar. Por defecto no hace nada. */
    protected void validar(T objeto) {
    }

    /** Gancho: reglas extra antes de eliminar. Por defecto no hace nada. */
    protected void antesDeEliminar(T objeto) {
    }

    // ---------- Flujo común ----------

    public List<T> listar() {
        return dao.listar();
    }

    /** Inserta si el objeto es nuevo (id 0) o actualiza si ya existe. */
    public T guardar(T objeto) {
        exigirPermiso();
        Validaciones.requerido(objeto, "un registro");
        validar(objeto);
        if (objeto.esNuevo()) {
            return dao.insertar(objeto);
        }
        dao.actualizar(objeto);
        return objeto;
    }

    public void eliminar(T objeto) {
        exigirPermiso();
        Validaciones.requerido(objeto, "un registro");
        antesDeEliminar(objeto);
        dao.eliminar(objeto.getId());
    }

    protected void exigirPermiso() {
        Sesion.getInstancia().exigir(permiso(), accion());
    }
}
