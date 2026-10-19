package servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Estudiante;
import modelo.Prestamo;

/**
 * Lo que el bibliotecario necesita saber del estudiante que acaba de poner el dedo:
 * quién es, con qué puntaje lo reconoció el lector, qué préstamos tiene activos,
 * cuántos puede tener y cuánto debe en multas.
 *
 * Objeto INMUTABLE (todo final, la lista se copia): una vez identificado el estudiante,
 * nadie puede cambiar la ficha "por detrás" antes de confirmar el préstamo o la devolución.
 */
public final class FichaEstudiante {

    private final Estudiante estudiante;
    private final int puntaje;
    private final List<Prestamo> prestamosActivos;
    private final int maximoPrestamos;
    private final BigDecimal multasPendientes;

    public FichaEstudiante(Estudiante estudiante, int puntaje, List<Prestamo> prestamosActivos,
                           int maximoPrestamos, BigDecimal multasPendientes) {
        this.estudiante = estudiante;
        this.puntaje = puntaje;
        this.prestamosActivos = Collections.unmodifiableList(new ArrayList<>(prestamosActivos));
        this.maximoPrestamos = maximoPrestamos;
        this.multasPendientes = multasPendientes == null ? BigDecimal.ZERO : multasPendientes;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public List<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    public int getCantidadActivos() {
        return prestamosActivos.size();
    }

    public int getMaximoPrestamos() {
        return maximoPrestamos;
    }

    /** Cuántos libros más puede llevar (nunca negativo). */
    public int getCupos() {
        return Math.max(0, maximoPrestamos - prestamosActivos.size());
    }

    public BigDecimal getMultasPendientes() {
        return multasPendientes;
    }

    public boolean tieneMultasPendientes() {
        return multasPendientes.signum() > 0;
    }

    /** "Cédula: 1805...   ·   Carrera: Software" (para la tarjeta del panel de huella). */
    public String getDatos() {
        return "Cédula: " + estudiante.getCedula() + "   ·   Carrera: " + estudiante.getCarrera();
    }

    /** "Préstamos activos: 1 de 3". */
    public String getTextoActivos() {
        return "Préstamos activos: " + getCantidadActivos() + " de " + maximoPrestamos;
    }
}
