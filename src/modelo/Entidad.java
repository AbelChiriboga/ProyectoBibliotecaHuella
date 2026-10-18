package modelo;

/**
 * Base de las clases que en la BD tienen una llave numérica autoincremental
 * (carrera, autor, categoría, libro, ejemplar, préstamo, multa, huella, bitácora).
 * Evita repetir en cada clase el id, equals y hashCode (herencia para reutilizar código).
 */
public abstract class Entidad {

    private int id;

    public int getId() {
        return id;
    }

    /** 0 significa "todavía no guardado en la BD". */
    public final void setId(int id) {
        if (id < 0) {
            throw new excepciones.ValidacionException("El id no puede ser negativo.");
        }
        this.id = id;
    }

    public boolean esNuevo() {
        return id == 0;
    }

    /** Mismo tipo y mismo id = mismo registro (sirve para seleccionar en combos y tablas). */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entidad otra = (Entidad) o;
        return id != 0 && id == otra.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
