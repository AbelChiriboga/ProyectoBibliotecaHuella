package servicio;

import dao.EstudianteDAO;
import dao.UsuarioDAO;
import excepciones.NegocioException;
import modelo.Estudiante;
import modelo.Usuario;
import util.Seguridad;
import util.Validaciones;

/**
 * Reglas del ingreso al sistema:
 *  - Personal: usuario + contraseña (SHA-256). Solo cuentas activas.
 *  - Estudiante: no inicia sesión, solo consulta su información con la cédula.
 * La ventana Login solo llama a estos métodos; no sabe nada de SQL ni de hashes.
 */
public class AutenticacionService {

    private static final String CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos.";

    private final UsuarioDAO usuarioDAO;
    private final EstudianteDAO estudianteDAO;

    public AutenticacionService() {
        this(new UsuarioDAO(), new EstudianteDAO());
    }

    /** Permite pasar otros DAO (por ejemplo, para pruebas). */
    public AutenticacionService(UsuarioDAO usuarioDAO, EstudianteDAO estudianteDAO) {
        this.usuarioDAO = usuarioDAO;
        this.estudianteDAO = estudianteDAO;
    }

    /**
     * Inicia la sesión del personal y devuelve el usuario como Usuario
     * (en realidad un Administrador o un Bibliotecario, creado por la fábrica del DAO).
     * Por seguridad, el mismo mensaje sirve para "no existe" y "contraseña incorrecta".
     */
    public Usuario iniciarSesion(String nombreUsuario, String clave) {
        String usuarioEscrito = Validaciones.obligatorio(nombreUsuario, "usuario");
        Validaciones.obligatorio(clave, "contraseña");

        Usuario u = usuarioDAO.buscarPorUsuario(usuarioEscrito)
                .filter(x -> Seguridad.coincide(clave, x.getPasswordHash()))
                .orElseThrow(() -> new NegocioException(CREDENCIALES_INVALIDAS));

        if (!u.puedeIniciarSesion()) {          // polimórfico: Usuario lo resuelve con "activo"
            throw new NegocioException("Su cuenta está desactivada. Comuníquese con el administrador.");
        }
        Sesion.getInstancia().iniciar(u);
        return u;
    }

    public void cerrarSesion() {
        Sesion.getInstancia().cerrar();
    }

    /** Consulta del estudiante por cédula (botón Consultar del Login). */
    public Estudiante consultarEstudiante(String cedula) {
        String c = Validaciones.formatoCedula(cedula);
        return estudianteDAO.buscarPorCedula(c)
                .orElseThrow(() -> new NegocioException("No existe un estudiante registrado con la cédula " + c + "."));
    }
}
