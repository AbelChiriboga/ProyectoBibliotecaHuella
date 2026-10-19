package biometria;

/** Lo usa el lector para avisar el avance del registro (se usa con una lambda). */
@FunctionalInterface
public interface AvisoCaptura {

    /**
     * @param muestrasListas cuántas muestras van (0 a 3)
     * @param mensaje        instrucción para el usuario ("Coloque el dedo", "Levante el dedo"...)
     */
    void avisar(int muestrasListas, String mensaje);
}
