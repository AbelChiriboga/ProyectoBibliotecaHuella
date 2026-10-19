package componentes;

import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Mensaje del estado del lector (conectado / desconectado) con un JOptionPane simple.
 * Si el lector cambia otra vez mientras el mensaje anterior sigue abierto, el anterior
 * se cierra y se muestra el nuevo (no se acumulan ventanas).
 */
public final class Aviso {

    private static JDialog actual;

    private Aviso() {
    }

    /** @param exito mensaje de información si es buena noticia; de advertencia si no */
    public static void mostrar(String titulo, String detalle, boolean exito) {
        if (actual != null) {
            actual.dispose();
        }
        JOptionPane mensaje = new JOptionPane(titulo + "\n" + detalle,
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        JDialog dialogo = mensaje.createDialog(ventanaActiva(), "Lector de huella");
        actual = dialogo;
        dialogo.setVisible(true);
        dialogo.dispose();
    }

    /** La ventana que el usuario está usando (o cualquier ventana visible). */
    private static Window ventanaActiva() {
        Window activa = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (activa != null) {
            return activa;
        }
        for (Frame f : Frame.getFrames()) {
            if (f.isShowing()) {
                return f;
            }
        }
        return null;
    }
}
