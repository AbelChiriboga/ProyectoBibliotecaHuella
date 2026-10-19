package biometria;

/** Lo usa el lector para avisar el avance del registro (se usa con una lambda). */
@FunctionalInterface
public interface AvisoCaptura {

    /**
     * @param muestrasListas cuántas muestras van (0 a 3)
     * @param mensaje        instrucción para el usuario ("Coloque el dedo", "Levante el dedo"...)
     */
    void avisar(int muestrasListas, String mensaje);

    /**
     * Imagen de la huella recién leída (para dibujarla en el recuadro). Es opcional:
     * por defecto no hace nada, así se puede seguir usando AvisoCaptura con una lambda.
     */
    default void imagen(java.awt.image.BufferedImage huella) {
    }
}
