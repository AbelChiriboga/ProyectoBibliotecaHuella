package pruebas;

import dao.UsuarioDAO;
import excepciones.BibliotecaException;
import modelo.Usuario;
import servicio.AutenticacionService;
import servicio.Sesion;

/**
 * Prueba de la Fase 3 (XAMPP encendido). En NetBeans: clic derecho → Run File (Shift+F6).
 * Para probar las ventanas, ejecuta el proyecto normal (F6) e ingresa con admin/admin123 o biblio/biblio123.
 */
public class PruebaSesion {

    public static void main(String[] args) {
        AutenticacionService auth = new AutenticacionService();

        titulo("Login correcto: el DAO devuelve la subclase y la Sesion la guarda");
        for (String[] cred : new String[][]{{"admin", "admin123"}, {"biblio", "biblio123"}}) {
            Usuario u = auth.iniciarSesion(cred[0], cred[1]);
            System.out.println("  " + cred[0] + " → " + u.getClass().getSimpleName() + " | sesión: "
                    + Sesion.getInstancia().getUsuario().getNombreCompleto() + " (" + Sesion.getInstancia().getUsuario().getTipo() + ")");
            probar("    exigir permiso de catálogo", () -> Sesion.getInstancia().exigir(Usuario::puedeGestionarCatalogo, "gestionar el catálogo"));
            probar("    exigir permiso de parámetros", () -> Sesion.getInstancia().exigir(Usuario::puedeConfigurarParametros, "configurar los parámetros"));
            auth.cerrarSesion();
        }

        titulo("Login incorrecto");
        probar("Contraseña equivocada", () -> auth.iniciarSesion("admin", "otra"));
        probar("Usuario que no existe", () -> auth.iniciarSesion("nadie", "123456"));
        probar("Campos vacíos", () -> auth.iniciarSesion("", ""));

        UsuarioDAO dao = new UsuarioDAO();
        String cedulaBiblio = dao.buscarPorUsuario("biblio").orElseThrow().getCedula();
        dao.eliminar(cedulaBiblio);                         // borrado lógico
        probar("Cuenta desactivada", () -> auth.iniciarSesion("biblio", "biblio123"));
        dao.cambiarActivo(cedulaBiblio, true);              // se deja como estaba
        System.out.println("  Sesión después de cerrar: " + Sesion.getInstancia().getUsuarioActual().isPresent());

        titulo("Consulta del estudiante por cédula");
        probar("1800000003", () -> System.out.print(auth.consultarEstudiante("1800000003").getNombreCompleto() + " → "));
        probar("Cédula inexistente", () -> auth.consultarEstudiante("1799999999"));
        probar("Cédula con letras", () -> auth.consultarEstudiante("18000ABC"));
    }

    private static void probar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println("  " + caso + ": permitido");
        } catch (BibliotecaException ex) {
            System.out.println("  " + caso + ": [" + ex.getTitulo() + "] " + ex.getMessage());
        }
    }

    private static void titulo(String t) {
        System.out.println("\n=== " + t + " ===");
    }
}
