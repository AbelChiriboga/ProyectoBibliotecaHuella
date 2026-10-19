package excepciones;

/** Problema con el lector de huella (no conectado, dedo no detectado, muestras distintas...). */
public class LectorException extends BibliotecaException {

    private final boolean tecnico;

    /** Aviso para el usuario (por ejemplo, "no se detectó ningún dedo"). */
    public LectorException(String mensaje) {
        this(mensaje, false, null);
    }

    /** Falla técnica del dispositivo o del SDK. */
    public LectorException(String mensaje, Throwable causa) {
        this(mensaje, true, causa);
    }

    private LectorException(String mensaje, boolean tecnico, Throwable causa) {
        super(mensaje, causa);
        this.tecnico = tecnico;
    }

    @Override
    public String getTitulo() {
        return "Lector de huella";
    }

    @Override
    public boolean esErrorTecnico() {
        return tecnico;
    }
}
