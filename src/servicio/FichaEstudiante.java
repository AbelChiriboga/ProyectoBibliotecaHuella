package servicio;

import java.math.BigDecimal;
import java.util.List;
import modelo.Estudiante;
import modelo.Prestamo;

/**
 * El estudiante que acaba de poner el dedo en Préstamo o Devolución.
 * HERENCIA: es una SituacionEstudiante (préstamos activos, cupos, multas) más el puntaje
 * con el que el lector reconoció su huella (se anota en la bitácora).
 */
public final class FichaEstudiante extends SituacionEstudiante {

    private final int puntaje;

    public FichaEstudiante(Estudiante estudiante, int puntaje, List<Prestamo> prestamosActivos,
                           int maximoPrestamos, BigDecimal multasPendientes) {
        super(estudiante, prestamosActivos, maximoPrestamos, multasPendientes);
        this.puntaje = puntaje;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
