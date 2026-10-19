package servicio;

import dao.AutorDAO;
import java.util.function.Predicate;
import modelo.Autor;
import modelo.Usuario;

/**
 * Autores. Solo el bibliotecario los modifica.
 * Si el autor tiene libros, la BD impide eliminarlo y el usuario ve
 * "No se puede eliminar porque tiene registros relacionados."
 */
public class AutorService extends ServicioCrud<Autor> {

    public AutorService() {
        super(new AutorDAO());
    }

    @Override
    protected Predicate<Usuario> permiso() {
        return Usuario::puedeGestionarCatalogo;
    }

    @Override
    protected String accion() {
        return "gestionar el catálogo";
    }
}
