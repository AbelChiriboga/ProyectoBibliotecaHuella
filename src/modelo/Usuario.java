package modelo;

import java.time.LocalDateTime;
import util.Validaciones;

/**
 * Personal del sistema (tabla usuarios). Sigue siendo ABSTRACTA: un usuario siempre es
 * Administrador o Bibliotecario.
 *
 * Los permisos son métodos abstractos: cada hija responde a su manera (polimorfismo).
 * Así las ventanas preguntan "usuario.puedeGestionarCatalogo()" y nunca
 * "¿es un Bibliotecario?" con if o instanceof.
 */
public abstract class Usuario extends Persona {

    private String usuario;
    private String passwordHash;
    private String direccion;
    private boolean activo = true;
    private LocalDateTime fechaCreacion;

    protected Usuario() {
    }

    protected Usuario(String cedula, String nombres, String apellidos, String usuario) {
        super(cedula, nombres, apellidos);
        setUsuario(usuario);
    }

    // ---------- Abstractos: cada rol los define ----------

    /** Rol del usuario; también es lo que se guarda en la columna rol. */
    public abstract Rol getRol();

    /** Registrar estudiantes con su huella. */
    public abstract boolean puedeRegistrarEstudiantes();

    /** Activar o desactivar estudiantes. */
    public abstract boolean puedeCambiarEstadoEstudiantes();

    /** Registrar y desactivar al personal (administradores y bibliotecarios). */
    public abstract boolean puedeGestionarPersonal();

    /** Libros, ejemplares, autores y categorías. */
    public abstract boolean puedeGestionarCatalogo();

    /** Préstamos, devoluciones y atrasos. */
    public abstract boolean puedeGestionarPrestamos();

    /** Parámetros del sistema. */
    public abstract boolean puedeConfigurarParametros();

    /** Bitácora de autenticación. */
    public abstract boolean puedeVerBitacora();

    /** Reportes con JasperReports (ambos roles; una hija podría redefinirlo). */
    public boolean puedeGenerarReportes() {
        return true;
    }

    // ---------- Implementación de los abstractos de Persona ----------

    @Override
    public String getTipo() {
        return getRol().getDescripcion();
    }

    /** El personal inicia sesión solo si su cuenta está activa (borrado lógico). */
    @Override
    public boolean puedeIniciarSesion() {
        return activo;
    }

    // ---------- Getters y setters ----------

    public String getUsuario() {
        return usuario;
    }

    public final void setUsuario(String usuario) {
        String u = Validaciones.obligatorio(usuario, "usuario");
        if (!u.matches("[A-Za-z0-9._-]{3,50}")) {
            throw new excepciones.ValidacionException(
                    "El usuario debe tener de 3 a 50 caracteres, sin espacios (letras, números, punto, guion).");
        }
        this.usuario = u;
    }

    /** Hash SHA-256 de la contraseña. La contraseña en texto plano nunca se guarda. */
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = Validaciones.obligatorio(passwordHash, "contraseña");
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = Validaciones.maximo(Validaciones.opcional(direccion), 150, "dirección");
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /** La pone la BD al insertar; el DAO la copia al leer. */
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
