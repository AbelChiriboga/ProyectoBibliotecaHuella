package servicio;

import dao.UsuarioDAO;
import excepciones.NegocioException;
import excepciones.ValidacionException;
import java.util.List;
import modelo.Rol;
import modelo.Usuario;
import util.Seguridad;
import util.Validaciones;

/**
 * Personal del sistema (administradores y bibliotecarios). Solo el administrador lo gestiona.
 * Borrado LÓGICO: un usuario nunca se elimina, se desactiva (y se puede volver a activar).
 */
public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public List<Usuario> listar() {
        exigirPermiso();
        return dao.listar();
    }

    /**
     * Crea un usuario vacío de la subclase que corresponde al rol elegido en el combo.
     * Usa la fábrica del enum Rol: no hay if para decidir si es Administrador o Bibliotecario.
     */
    public Usuario crear(Rol rol) {
        return Validaciones.requerido(rol, "el rol").crearUsuario();
    }

    /** Registra un usuario nuevo con su contraseña (se guarda solo el hash SHA-256). */
    public void registrar(Usuario u, String clave, String repetida) {
        exigirPermiso();
        Validaciones.cedulaEcuatoriana(u.getCedula());
        if (dao.buscarPorId(u.getCedula()).isPresent()) {
            throw new ValidacionException("Ya existe un usuario con la cédula " + u.getCedula() + ".");
        }
        u.setPasswordHash(Seguridad.sha256(Seguridad.validarNueva(clave, repetida)));
        u.setActivo(true);
        dao.insertar(u);
    }

    /** Actualiza los datos. La contraseña solo cambia si se escribió una nueva. */
    public void actualizar(Usuario u, String clave, String repetida) {
        exigirPermiso();
        exigirQueNoCambieSuPropioRol(u);
        boolean cambiaClave = clave != null && !clave.isEmpty();
        String hash = cambiaClave ? Seguridad.sha256(Seguridad.validarNueva(clave, repetida)) : null;
        dao.actualizar(u);
        if (cambiaClave) {
            dao.actualizarPassword(u.getCedula(), hash);
        }
    }

    /** Activa o desactiva (borrado lógico). Nadie puede desactivarse a sí mismo. */
    public void cambiarEstado(Usuario u) {
        exigirPermiso();
        if (esUsuarioActual(u)) {
            throw new NegocioException("No puede desactivar su propia cuenta.");
        }
        dao.cambiarActivo(u.getCedula(), !u.isActivo());
        u.setActivo(!u.isActivo());
    }

    private void exigirQueNoCambieSuPropioRol(Usuario u) {
        if (esUsuarioActual(u) && Sesion.getInstancia().getUsuario().getRol() != u.getRol()) {
            throw new NegocioException("No puede cambiar su propio rol.");
        }
    }

    private boolean esUsuarioActual(Usuario u) {
        return Sesion.getInstancia().getUsuario().getCedula().equals(u.getCedula());
    }

    private void exigirPermiso() {
        Sesion.getInstancia().exigir(Usuario::puedeGestionarPersonal, "gestionar al personal");
    }
}
