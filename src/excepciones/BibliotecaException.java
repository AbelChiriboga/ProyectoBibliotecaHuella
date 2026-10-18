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
}
