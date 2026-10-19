package biometria;

/**
 * Estado del lector de huella. Cada constante sabe su texto para el pie de página
 * y si permite capturar huellas (polimorfismo en el enum, sin if en las ventanas).
 */
public enum EstadoLector {

    CONECTADO("Conectado", true),
    DESCONECTADO("Desconectado", false),
    SIN_DRIVER("Sin driver instalado", false),
    SIMULADO("Simulado", true);

    private final String descripcion;
    private final boolean permiteCapturar;

    EstadoLector(String descripcion, boolean permiteCapturar) {
        this.descripcion = descripcion;
        this.permiteCapturar = permiteCapturar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /** Solo con el lector listo se pueden registrar o leer huellas. */
    public boolean permiteCapturar() {
        return permiteCapturar;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
