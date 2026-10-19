package biometria;

import excepciones.LectorException;

/**
 * Lo común a cualquier lector (clase ABSTRACTA + método plantilla).
 *
 * registrar() es igual para el lector real y el simulado: pide 3 muestras, verifica que sean
 * del mismo dedo y las une. Lo que cambia (cómo capturar, cómo comparar, cómo unir)
 * lo implementa cada hija.
 */
public abstract class LectorBase implements LectorBiometrico {

    public static final int MUESTRAS_REGISTRO = 3;

    /** ¿Las dos muestras son del mismo dedo? */
    protected abstract boolean mismoDedo(byte[] a, byte[] b);

    /** Une las muestras en una plantilla de registro. */
    protected abstract byte[] fusionar(byte[][] muestras);

    @Override
    public byte[] registrar(AvisoCaptura aviso) {
        exigirAbierto();
        byte[][] muestras = new byte[MUESTRAS_REGISTRO][];
        for (int i = 0; i < MUESTRAS_REGISTRO; i++) {
            aviso.avisar(i, "Coloque el dedo en el lector (muestra " + (i + 1) + " de " + MUESTRAS_REGISTRO + ").");
            muestras[i] = capturar();
            if (i > 0 && !mismoDedo(muestras[i - 1], muestras[i])) {
                aviso.avisar(i, "Las muestras no coinciden.");
                throw new LectorException("Las muestras no son del mismo dedo. Vuelva a capturar las "
                        + MUESTRAS_REGISTRO + " muestras con el mismo dedo.");
            }
            aviso.avisar(i + 1, i + 1 < MUESTRAS_REGISTRO ? "Muestra lista. Levante y vuelva a colocar el dedo."
                    : "Muestras completas. Generando la plantilla...");
        }
        return fusionar(muestras);
    }

    protected void exigirAbierto() {
        if (!estaAbierto()) {
            throw new LectorException("Lector de huella desconectado. Conéctelo e intente de nuevo.");
        }
    }
}
