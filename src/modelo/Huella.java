package modelo;

import java.time.LocalDateTime;
import java.util.Arrays;
import util.Validaciones;

/**
 * Plantilla (template) de una huella de un estudiante. Un estudiante puede tener varias
 * (se recomiendan 2 dedos). La plantilla son los bytes que genera el SDK de ZKTeco.
 */
public class Huella extends Entidad {

    private String cedulaEstudiante;
    private Dedo dedo;
    private byte[] plantilla;
    private LocalDateTime fechaRegistro;

    public Huella() {
    }

    public Huella(Dedo dedo, byte[] plantilla) {
        setDedo(dedo);
        setPlantilla(plantilla);
    }

    public String getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public Dedo getDedo() {
        return dedo;
    }

    public final void setDedo(Dedo dedo) {
        this.dedo = Validaciones.requerido(dedo, "el dedo");
    }

    /** Devuelve una COPIA: nadie de afuera puede modificar la plantilla guardada. */
    public byte[] getPlantilla() {
        return plantilla == null ? null : Arrays.copyOf(plantilla, plantilla.length);
    }

    public final void setPlantilla(byte[] plantilla) {
        if (plantilla == null || plantilla.length == 0) {
            throw new excepciones.ValidacionException("La plantilla de la huella está vacía.");
        }
        this.plantilla = Arrays.copyOf(plantilla, plantilla.length);
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
