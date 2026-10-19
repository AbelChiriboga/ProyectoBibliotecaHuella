package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import util.Validaciones;

/**
 * Préstamo de UN ejemplar a un estudiante, registrado por un usuario del personal.
 * Asociaciones: tiene un Ejemplar, un Estudiante, el Usuario que atendió y, si devolvió tarde, una Multa.
 * Las reglas fuertes (huella, estudiante activo, ejemplar disponible, máximo de préstamos,
 * fecha límite y multa) las aplican los triggers de la BD; aquí solo se modelan.
 */
public class Prestamo extends Entidad {

    private Ejemplar ejemplar;
    private Estudiante estudiante;
    private Usuario usuario;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaLimite;        // si va nula al insertar, la calcula el trigger
    private LocalDateTime fechaDevolucion;
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;
    private boolean huellaPrestamo;           // 1 = huella verificada al prestar
    private boolean huellaDevolucion;         // 1 = huella verificada al devolver
    private String observaciones;
    private Multa multa;                      // nula si no hubo retraso

    public Prestamo() {
    }

    public Prestamo(Ejemplar ejemplar, Estudiante estudiante, Usuario usuario) {
        setEjemplar(ejemplar);
        setEstudiante(estudiante);
        setUsuario(usuario);
    }

    // ---------- Comportamiento ----------

    public boolean estaActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }

    /** Días de atraso a la fecha indicada (0 si no está atrasado). Igual que DATEDIFF de la BD. */
    public int diasAtraso(LocalDate aLaFecha) {
        if (fechaLimite == null || !aLaFecha.isAfter(fechaLimite.toLocalDate())) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(fechaLimite.toLocalDate(), aLaFecha);
    }

    /** Días de atraso a hoy (o a la fecha de devolución si ya se devolvió). */
    public int diasAtraso() {
        return diasAtraso(fechaDevolucion != null ? fechaDevolucion.toLocalDate() : LocalDate.now());
    }

    public boolean estaAtrasado() {
        return estaActivo() && diasAtraso() > 0;
    }

    public boolean tieneMulta() {
        return multa != null;
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

    /** Usuario que registró el préstamo (columna cedula_usuario; puede ser nulo). */
    public final void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
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

    public boolean isHuellaPrestamo() {
        return huellaPrestamo;
    }

    public void setHuellaPrestamo(boolean huellaPrestamo) {
        this.huellaPrestamo = huellaPrestamo;
    }

    public boolean isHuellaDevolucion() {
        return huellaDevolucion;
    }

    public void setHuellaDevolucion(boolean huellaDevolucion) {
        this.huellaDevolucion = huellaDevolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = Validaciones.maximo(Validaciones.opcional(observaciones), 255, "observaciones");
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }
}
