package modelo;

import util.Validaciones;

/**
 * Clase base ABSTRACTA de todas las personas del sistema (personal y estudiantes).
 * Reúne los datos comunes para no repetirlos en cada hija (herencia) y obliga a cada hija
 * a decir qué tipo de persona es y si puede iniciar sesión (métodos abstractos).
 * No se puede hacer "new Persona()": solo existen Administrador, Bibliotecario y Estudiante.
 */
public abstract class Persona {

    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;

    protected Persona() {
    }

    protected Persona(String cedula, String nombres, String apellidos) {
        setCedula(cedula);
        setNombres(nombres);
        setApellidos(apellidos);
    }

    // ---------- Métodos abstractos: cada hija los implementa a su manera ----------

    /** Texto del tipo de persona ("Administrador", "Bibliotecario", "Estudiante"). */
    public abstract String getTipo();

    /** El personal inicia sesión con usuario y contraseña; el estudiante no. */
    public abstract boolean puedeIniciarSesion();

    // ---------- Comportamiento común ----------

    /** "Nombres Apellidos", para el encabezado y las tablas. */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    // ---------- Getters y setters con validación (encapsulamiento) ----------

    public String getCedula() {
        return cedula;
    }

    /** Solo verifica el formato (10 dígitos); el dígito verificador se valida al registrar. */
    public final void setCedula(String cedula) {
        this.cedula = Validaciones.formatoCedula(cedula);
    }

    public String getNombres() {
        return nombres;
    }

    public final void setNombres(String nombres) {
        this.nombres = Validaciones.maximo(Validaciones.obligatorio(nombres, "nombres"), 100, "nombres");
    }

    public String getApellidos() {
        return apellidos;
    }

    public final void setApellidos(String apellidos) {
        this.apellidos = Validaciones.maximo(Validaciones.obligatorio(apellidos, "apellidos"), 100, "apellidos");
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = Validaciones.correo(correo);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = Validaciones.telefono(telefono);
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }

    /** Dos personas son la misma si tienen la misma cédula. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return cedula != null && cedula.equals(((Persona) o).cedula);
    }

    @Override
    public int hashCode() {
        return cedula == null ? 0 : cedula.hashCode();
    }
}
