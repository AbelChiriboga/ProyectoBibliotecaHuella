package modelo;

/** Estado de una copia física. PRESTADO lo ponen los triggers de la BD. */
public enum EstadoEjemplar implements ValorBD {

    DISPONIBLE("DISPONIBLE", "Disponible"),
    PRESTADO("PRESTADO", "Prestado"),
    DANADO("DANADO", "Dañado"),
    PERDIDO("PERDIDO", "Perdido");

    private final String valorBD;
    private final String descripcion;

    EstadoEjemplar(String valorBD, String descripcion) {
        this.valorBD = valorBD;
        this.descripcion = descripcion;
    }

    @Override
    public String getValorBD() {
        return valorBD;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /** Solo un ejemplar disponible se puede prestar. */
    public boolean esPrestable() {
        return this == DISPONIBLE;
    }

    /** El bibliotecario no puede asignar PRESTADO a mano: lo maneja el préstamo. */
    public boolean esEditableManualmente() {
        return this != PRESTADO;
    }

    /** Los combos muestran la descripción. */
    @Override
    public String toString() {
        return descripcion;
    }
}
