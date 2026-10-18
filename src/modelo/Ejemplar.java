package modelo;

import util.Validaciones;

/** Copia física de un libro, identificada por su código de inventario. */
public class Ejemplar extends Entidad {

    private Libro libro;
    private String codigoInventario;
    private String ubicacion;
    private EstadoEjemplar estado = EstadoEjemplar.DISPONIBLE;

    public Ejemplar() {
    }

    public Ejemplar(Libro libro, String codigoInventario, String ubicacion) {
        setLibro(libro);
        setCodigoInventario(codigoInventario);
        setUbicacion(ubicacion);
    }

    public boolean estaDisponible() {
        return estado.esPrestable();
    }

    public Libro getLibro() {
        return libro;
    }

    public final void setLibro(Libro libro) {
        this.libro = Validaciones.requerido(libro, "el libro");
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public final void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = Validaciones.maximo(
                Validaciones.obligatorio(codigoInventario, "código de inventario").toUpperCase(), 30, "código de inventario");
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public final void setUbicacion(String ubicacion) {
        this.ubicacion = Validaciones.maximo(Validaciones.opcional(ubicacion), 60, "ubicación");
    }

    public EstadoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(EstadoEjemplar estado) {
        this.estado = Validaciones.requerido(estado, "el estado");
    }

    @Override
    public String toString() {
        return codigoInventario + (libro == null ? "" : " - " + libro.getTitulo());
    }
}
