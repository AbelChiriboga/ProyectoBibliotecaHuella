package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.Validaciones;

/**
 * Libro del catálogo (la obra). Las copias físicas son objetos Ejemplar.
 * Composición: un libro TIENE un Autor y una Categoría (objetos, no solo ids).
 */
public class Libro extends Entidad {

    private String isbn;
    private String titulo;
    private Autor autor;
    private Categoria categoria;
    private String editorial;
    private Integer anio;
    private LocalDateTime fechaRegistro;

    // Datos calculados (vienen de la vista v_catalogo, no se guardan)
    private int ejemplaresTotales;
    private int ejemplaresDisponibles;

    public Libro() {
    }

    public Libro(String isbn, String titulo, Autor autor, Categoria categoria) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setCategoria(categoria);
    }

    /** Texto de la columna "Disponibles" de CRUDLibros, por ejemplo "1 / 2". */
    public String getResumenDisponibles() {
        return ejemplaresDisponibles + " / " + ejemplaresTotales;
    }

    public String getIsbn() {
        return isbn;
    }

    /** ISBN-10 o ISBN-13 (se aceptan guiones). Opcional. */
    public final void setIsbn(String isbn) {
        String i = Validaciones.opcional(isbn);
        if (i != null && !i.replace("-", "").matches("\\d{9}[\\dXx]|\\d{13}")) {
            throw new excepciones.ValidacionException("El ISBN debe tener 10 o 13 dígitos.");
        }
        this.isbn = i;
    }

    public String getTitulo() {
        return titulo;
    }

    public final void setTitulo(String titulo) {
        this.titulo = Validaciones.maximo(Validaciones.obligatorio(titulo, "título"), 200, "título");
    }

    public Autor getAutor() {
        return autor;
    }

    public final void setAutor(Autor autor) {
        this.autor = Validaciones.requerido(autor, "el autor");
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public final void setCategoria(Categoria categoria) {
        this.categoria = Validaciones.requerido(categoria, "la categoría");
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = Validaciones.maximo(Validaciones.opcional(editorial), 100, "editorial");
    }

    public Integer getAnio() {
        return anio;
    }

    /** Año de publicación: opcional, entre 1450 y el año actual. */
    public void setAnio(Integer anio) {
        if (anio != null) {
            Validaciones.rango(anio, "año", 1450, LocalDate.now().getYear());
        }
        this.anio = anio;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /** La llena la BD; no se muestra en el formulario. */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    public void setEjemplaresTotales(int ejemplaresTotales) {
        this.ejemplaresTotales = ejemplaresTotales;
    }

    public int getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(int ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
