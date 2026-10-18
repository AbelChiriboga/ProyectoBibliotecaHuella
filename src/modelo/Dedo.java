package modelo;

/** Dedo de la huella registrada (solo se elige al registrar). */
public enum Dedo implements ValorBD {

    PULGAR_DERECHO("PULGAR_DERECHO", "Pulgar derecho"),
    INDICE_DERECHO("INDICE_DERECHO", "Índice derecho"),
    MEDIO_DERECHO("MEDIO_DERECHO", "Medio derecho"),
    ANULAR_DERECHO("ANULAR_DERECHO", "Anular derecho"),
    MENIQUE_DERECHO("MENIQUE_DERECHO", "Meñique derecho"),
    PULGAR_IZQUIERDO("PULGAR_IZQUIERDO", "Pulgar izquierdo"),
    INDICE_IZQUIERDO("INDICE_IZQUIERDO", "Índice izquierdo"),
    MEDIO_IZQUIERDO("MEDIO_IZQUIERDO", "Medio izquierdo"),
    ANULAR_IZQUIERDO("ANULAR_IZQUIERDO", "Anular izquierdo"),
    MENIQUE_IZQUIERDO("MENIQUE_IZQUIERDO", "Meñique izquierdo");

    private final String valorBD;
    private final String descripcion;

    Dedo(String valorBD, String descripcion) {
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

    /** Los combos muestran la descripción. */
    @Override
    public String toString() {
        return descripcion;
    }
}
