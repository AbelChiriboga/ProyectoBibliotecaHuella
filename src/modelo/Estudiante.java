package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.Validaciones;

/**
 * Estudiante: hereda los datos personales de Persona y agrega carrera, semestre, estado y huellas.
 * No inicia sesión: se identifica con su huella (préstamo/devolución) o con su cédula (consulta).
 */
public class Estudiante extends Persona {

    public static final int SEMESTRE_MIN = 1;
    public static final int SEMESTRE_MAX = 10;

    private Carrera carrera;
    private int semestre = SEMESTRE_MIN;
    private EstadoEstudiante estado = EstadoEstudiante.ACTIVO;
    private final List<Huella> huellas = new ArrayList<>();

    public Estudiante() {
    }

    public Estudiante(String cedula, String nombres, String apellidos, Carrera carrera, int semestre) {
        super(cedula, nombres, apellidos);
        setCarrera(carrera);
        setSemestre(semestre);
    }

    // ---------- Implementación de los abstractos de Persona ----------

    @Override
    public String getTipo() {
        return "Estudiante";
    }

    @Override
    public boolean puedeIniciarSesion() {
        return false;
    }

    // ---------- Comportamiento propio ----------

    /** Solo un estudiante activo puede pedir préstamos (la BD también lo valida con un trigger). */
    public boolean puedePrestar() {
        return estado.puedePrestar();
    }

    public boolean tieneHuella() {
        return !huellas.isEmpty();
    }

    /** Agrega una huella; no permite registrar dos veces el mismo dedo. */
    public void agregarHuella(Huella huella) {
        Validaciones.requerido(huella, "la huella");
        for (Huella h : huellas) {
            if (h.getDedo() == huella.getDedo()) {
                throw new excepciones.ValidacionException("El dedo " + huella.getDedo() + " ya está registrado.");
            }
        }
        huella.setCedulaEstudiante(getCedula());
        huellas.add(huella);
    }

    /** Lista de solo lectura: desde afuera no se puede modificar sin pasar por agregarHuella. */
    public List<Huella> getHuellas() {
        return Collections.unmodifiableList(huellas);
    }

    public void limpiarHuellas() {
        huellas.clear();
    }

    // ---------- Getters y setters ----------

    public Carrera getCarrera() {
        return carrera;
    }

    public final void setCarrera(Carrera carrera) {
        this.carrera = Validaciones.requerido(carrera, "la carrera");
    }

    public int getSemestre() {
        return semestre;
    }

    public final void setSemestre(int semestre) {
        this.semestre = Validaciones.rango(semestre, "semestre", SEMESTRE_MIN, SEMESTRE_MAX);
    }

    public EstadoEstudiante getEstado() {
        return estado;
    }

    public void setEstado(EstadoEstudiante estado) {
        this.estado = Validaciones.requerido(estado, "el estado");
    }
}
