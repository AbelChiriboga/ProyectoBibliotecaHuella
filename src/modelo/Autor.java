package modelo;

import util.Validaciones;

/** Autor de libros. */
public class Autor extends Entidad {

    private String nombres;
    private String apellidos;
    private String nacionalidad;

    public Autor() {
    }

    public Autor(String nombres, String apellidos) {
        setNombres(nombres);
        setApellidos(apellidos);
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public final void setNombres(String nombres) {
        this.nombres = Validaciones.maximo(Validaciones.obligatorio(nombres, "nombres"), 100, "nombres");
    }

    public String getApellidos() {
        return apellidos;
    }

    public final void setApellidos(String apellidos) {
        this.apellidos = Validaciones.maximo(Validaciones.obligatorio(apellidos, "apellidos"), 100, "apellidos");
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = Validaciones.maximo(Validaciones.opcional(nacionalidad), 60, "nacionalidad");
    }

    /** El combo de autores en CRUDLibros muestra el nombre completo. */
    @Override
    public String toString() {
        return getNombreCompleto();
    }
}
