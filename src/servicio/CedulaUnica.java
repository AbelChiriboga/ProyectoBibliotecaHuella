package servicio;

import dao.EstudianteDAO;
import dao.UsuarioDAO;
import excepciones.ValidacionException;

/**
 * Una cédula pertenece a UNA sola persona del sistema: o es estudiante o es usuario
 * (administrador / bibliotecario), nunca las dos. Estudiantes y usuarios están en tablas
 * distintas, por eso la BD sola no lo puede impedir y se revisa aquí, antes de guardar.
 */
public class CedulaUnica {

    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /** Para registrar un administrador o bibliotecario: la cédula no puede ser de un estudiante. */
    public void exigirQueNoSeaDeEstudiante(String cedula) {
        estudianteDAO.buscarPorCedula(cedula).ifPresent(e -> {
            throw new ValidacionException("Esa cédula ya está registrada como estudiante (" + e.getNombreCompleto() + ").");
        });
    }

    /** Para registrar o editar un estudiante: la cédula no puede ser de un usuario del sistema. */
    public void exigirQueNoSeaDeUsuario(String cedula) {
        usuarioDAO.buscarPorId(cedula).ifPresent(u -> {
            throw new ValidacionException("Esa cédula ya está registrada como " + u.getTipo().toLowerCase()
                    + " (" + u.getNombreCompleto() + ").");
        });
    }
}
