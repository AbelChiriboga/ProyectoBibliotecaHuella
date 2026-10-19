package modelo;

import java.time.LocalDateTime;
import util.Validaciones;

/**
 * Fila de la bitácora: cada intento de usar la huella (éxito o fallo).
 * Si la huella no se reconoció, no hay estudiante (cédula nula).
 */
public class RegistroAutenticacion extends Entidad {

    private Integer idEstudiante;      // nulo si la huella no se reconoció
    private String cedulaEstudiante;   // solo para mostrar en la tabla
    private String nombreEstudiante;   // solo para mostrar en la tabla
    private OperacionAutenticacion operacion;
    private ResultadoAutenticacion resultado;
    private Integer puntaje;
    private String detalle;
    private LocalDateTime fechaHora;

    public RegistroAutenticacion() {
    }

    public RegistroAutenticacion(Integer idEstudiante, OperacionAutenticacion operacion,
                                 ResultadoAutenticacion resultado, Integer puntaje, String detalle) {
        this.idEstudiante = idEstudiante;
        setOperacion(operacion);
        setResultado(resultado);
        setPuntaje(puntaje);
        setDetalle(detalle);
    }

    public boolean fueExitoso() {
        return resultado == ResultadoAutenticacion.EXITO;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
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

    /**
     * Puntaje que devuelve el SDK del lector (no negativo; el ZKTeco puede dar valores mayores a 100).
     * Nulo cuando no aplica (por ejemplo, un registro de huella exitoso).
     */
    public final void setPuntaje(Integer puntaje) {
        if (puntaje != null) {
            Validaciones.rango(puntaje, "puntaje", 0, Integer.MAX_VALUE);
        }
        this.puntaje = puntaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public final void setDetalle(String detalle) {
        this.detalle = Validaciones.maximo(Validaciones.opcional(detalle), 255, "detalle");
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
