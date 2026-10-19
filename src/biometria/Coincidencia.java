package biometria;

/** Resultado de una identificación 1:N: qué huella coincidió y con qué puntaje. */
public final class Coincidencia {

    private final int idHuella;
    private final int puntaje;

    public Coincidencia(int idHuella, int puntaje) {
        this.idHuella = idHuella;
        this.puntaje = puntaje;
    }

    public int getIdHuella() {
        return idHuella;
    }

    /** Puntaje del SDK (más alto = más parecido). Se compara con el parámetro UMBRAL_HUELLA. */
    public int getPuntaje() {
        return puntaje;
    }
}
