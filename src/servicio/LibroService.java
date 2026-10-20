package servicio;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.LibroDAO;
import java.util.List;
import java.util.function.Predicate;
import modelo.Autor;
import modelo.Categoria;
import modelo.Libro;
import modelo.Usuario;

/** Libros del catálogo. */
public class LibroService extends ServicioCrud<Libro> {

    private final AutorDAO autorDAO = new AutorDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public LibroService() {
        super(new LibroDAO());
    }

    @Override
    protected Predicate<Usuario> permiso() {
        return Usuario::puedeGestionarCatalogo;
    }

    @Override
    protected String accion() {
        return "gestionar el catálogo";
    }

    public List<Autor> listarAutores() {
        return autorDAO.listar();
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listar();
    }

    @Override
    protected void antesDeEliminar(Libro l) {
        if (l.getEjemplaresTotales() > 0) {
            throw new excepciones.NegocioException("No se puede eliminar: el libro tiene ejemplares.");
        }
    }
}
