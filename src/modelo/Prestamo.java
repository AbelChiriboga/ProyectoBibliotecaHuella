package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import util.Validaciones;

/**
 * Préstamo de UN ejemplar a un estudiante, registrado por un usuario del personal.
 * Asociaciones: tiene un Ejemplar, un Estudiante y un Usuario (el que atendió).
 * Las reglas fuertes (huella, máximo de préstamos, ejemplar disponible) también las valida la BD.
 */
public class Prestamo extends Entidad {

    private Ejemplar ejemplar;
    private Estudiante estudiante;
    private Usuario usuario;
    private LocalDateTime fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDateTime fechaDevolucion;
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

    public Prestamo() {
    }

    public Prestamo(Ejemplar ejemplar, Estudiante estudiante, Usuario usuario, LocalDate fechaLimite) {
        setEjemplar(ejemplar);
        setEstudiante(estudiante);
        setUsuario(usuario);
        setFechaLimite(fechaLimite);
    }

    // ---------- Comportamiento ----------

    public boolean estaActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }

    /** Días de atraso a la fecha indicada (0 si no está atrasado). */
    public int diasAtraso(LocalDate aLaFecha) {
        if (fechaLimite == null || !aLaFecha.isAfter(fechaLimite)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(fechaLimite, aLaFecha);
    }

    /** Días de atraso a hoy. */
    public int diasAtraso() {
        return diasAtraso(LocalDate.now());
    }

    public boolean estaAtrasado() {
        return estaActivo() && diasAtraso() > 0;
    }

    // ---------- Getters y setters ----------

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public final void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = Validaciones.requerido(ejemplar, "el ejemplar");
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public final void setEstudiante(Estudiante estudiante) {
        this.estudiante = Validaciones.requerido(estudiante, "el estudiante");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /** Usuario que registró el préstamo (columna cedula_usuario). */
    public final void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public final void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = Validaciones.requerido(fechaLimite, "la fecha límite");
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = Validaciones.requerido(estado, "el estado");
    }
}
