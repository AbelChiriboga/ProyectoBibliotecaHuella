package util;

import excepciones.BibliotecaException;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Mensajes al usuario en un solo lugar. Las ventanas no repiten try/catch ni JOptionPane:
 * llaman a Mensajes.ejecutar(this, () -> ...) y si algo falla se muestra el mensaje
 * de la excepción propia con su título (polimorfismo: cada excepción sabe su título).
 */
public final class Mensajes {

    private Mensajes() {
    }

    /**
     * Ejecuta una acción de la ventana. Si lanza una excepción del sistema, la muestra.
     * Devuelve true si terminó bien (útil para limpiar el formulario solo cuando se guardó).
     */
    public static boolean ejecutar(Component padre, Runnable accion) {
        try {
            accion.run();
            return true;
        } catch (BibliotecaException e) {
            error(padre, e);
            return false;
        }
    }

    public static void error(Component padre, BibliotecaException e) {
        int tipo = e.esErrorTecnico() ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
        JOptionPane.showMessageDialog(padre, e.getMessage(), e.getTitulo(), tipo);
    }

    public static void info(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Biblioteca FISEI", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(Component padre, String pregunta) {
        return JOptionPane.showConfirmDialog(padre, pregunta, "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }
}
