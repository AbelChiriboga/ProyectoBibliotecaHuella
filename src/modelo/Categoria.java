package modelo;

import util.Validaciones;

/** Categoría de libros (Ingeniería de Software, Bases de Datos, Literatura...). */
public class Categoria extends Entidad {

    private String nombre;

    public Categoria() {
    }

    public Categoria(String nombre) {
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        this.nombre = Validaciones.maximo(Validaciones.obligatorio(nombre, "nombre de la categoría"), 80, "nombre");
    }

    @Override
    public String toString() {
        return nombre;
    }
}
