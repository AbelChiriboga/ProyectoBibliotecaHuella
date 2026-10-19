package servicio;

import excepciones.NegocioException;
import java.util.Optional;
import java.util.function.Predicate;
import modelo.Usuario;

/**
 * Sesión del usuario que ingresó al sistema. SINGLETON: existe una sola instancia en toda
 * la aplicación (constructor privado + getInstancia()), así cualquier ventana o servicio
 * consulta al mismo usuario sin pasarlo de ventana en ventana.
 */
public final class Sesion {

    private static final Sesion INSTANCIA = new Sesion();

    private Usuario usuario;

    /** Privado: nadie puede hacer "new Sesion()". */
    private Sesion() {
    }

    public static Sesion getInstancia() {
        return INSTANCIA;
    }

    /** Lo llama AutenticacionService cuando la contraseña es correcta. */
    void iniciar(Usuario usuario) {
        this.usuario = usuario;
    }

    void cerrar() {
        this.usuario = null;
    }

    /** Usuario actual, o vacío si nadie ha iniciado sesión (por ejemplo, al abrir un frame suelto). */
    public Optional<Usuario> getUsuarioActual() {
        return Optional.ofNullable(usuario);
    }

    /** Usuario actual; si no hay sesión, lanza NegocioException. */
    public Usuario getUsuario() {
        return getUsuarioActual().orElseThrow(() -> new NegocioException("No hay una sesión iniciada."));
    }

    /**
     * Verifica un permiso de forma polimórfica, por ejemplo:
     *   Sesion.getInstancia().exigir(Usuario::puedeGestionarCatalogo, "gestionar el catálogo");
     * Cada subclase de Usuario responde su propio permiso; aquí no se pregunta el tipo.
     */
    public void exigir(Predicate<Usuario> permiso, String accion) {
        if (!permiso.test(getUsuario())) {
            throw new NegocioException("Su rol (" + usuario.getTipo() + ") no tiene permiso para " + accion + ".");
        }
    }
}
