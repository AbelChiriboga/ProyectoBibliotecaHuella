package modelo;

import java.time.LocalDateTime;
import util.Validaciones;

/**
 * Fila de la bitácora: cada intento de usar la huella (éxito o fallo).
 * Si la huella no se reconoció, no hay estudiante (cédula nula).
 */
public class RegistroAutenticacion extends Entidad {

    private String cedulaEstudiante;
    private String nombreEstudiante;   // solo para mostrar en la tabla
    private OperacionAutenticacion operacion;
    private ResultadoAutenticacion resultado;
    private Integer puntaje;
    private String detalle;
    private LocalDateTime fechaHora;

    public RegistroAutenticacion() {
    }

    public RegistroAutenticacion(String cedulaEstudiante, OperacionAutenticacion operacion,
                                 ResultadoAutenticacion resultado, Integer puntaje, String detalle) {
        this.cedulaEstudiante = cedulaEstudiante;
        setOperacion(operacion);
        setResultado(resultado);
        setPuntaje(puntaje);
        setDetalle(detalle);
    }

    public boolean fueExitoso() {
        return resultado == ResultadoAutenticacion.EXITO;
    }

    public String getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public OperacionAutenticacion getOperacion() {
        return operacion;
    }

    public final void setOperacion(OperacionAutenticacion operacion) {
        this.operacion = Validaciones.requerido(operacion, "la operación");
    }

    public ResultadoAutenticacion getResultado() {
        return resultado;
    }

    public final void setResultado(ResultadoAutenticacion resultado) {
        this.resultado = Validaciones.requerido(resultado, "el resultado");
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    /** Puntaje del SDK (0 a 100); nulo cuando no aplica (por ejemplo, registro de huella). */
    public final void setPuntaje(Integer puntaje) {
        if (puntaje != null) {
            Validaciones.rango(puntaje, "puntaje", 0, 100);
        }
        this.puntaje = puntaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public final void setDetalle(String detalle) {
        this.detalle = Validaciones.maximo(Validaciones.opcional(detalle), 200, "detalle");
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
