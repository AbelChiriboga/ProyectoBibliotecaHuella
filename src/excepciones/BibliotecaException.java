package excepciones;

/**
 * Padre de las excepciones propias del sistema.
 * Las ventanas capturan SOLO esta clase para mostrar el mensaje al usuario,
 * en vez de capturar Exception en todos lados.
 * Es abstracta: nunca se lanza "una BibliotecaException", sino uno de sus hijos.
 */
public abstract class BibliotecaException extends RuntimeException {

    protected BibliotecaException(String mensaje) {
        super(mensaje);
    }

    protected BibliotecaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /** Título para el cuadro de diálogo (cada hijo lo redefine). */
    public abstract String getTitulo();

    /** true = falla técnica (se muestra como error); false = aviso para el usuario. */
    public boolean esErrorTecnico() {
        return false;
    }

    /** Qué hace la lectura automática de huella si ocurre este error (por defecto, mostrarlo). */
    public Reaccion getReaccion() {
        return Reaccion.MOSTRAR_ERROR;
    }
}
