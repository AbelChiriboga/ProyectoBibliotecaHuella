package modelo;

/** Estado de la cuenta del estudiante (lo cambia el bibliotecario). */
public enum EstadoEstudiante implements ValorBD {

    ACTIVO("ACTIVO", "Activo"),
    INACTIVO("INACTIVO", "Inactivo");

    private final String valorBD;
    private final String descripcion;

    EstadoEstudiante(String valorBD, String descripcion) {
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

    /** Solo un estudiante activo puede pedir préstamos. */
    public boolean puedePrestar() {
        return this == ACTIVO;
    }

    /** Los combos muestran la descripción. */
    @Override
    public String toString() {
        return descripcion;
    }
}
