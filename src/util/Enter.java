package util;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/** Asigna una acción a la tecla Enter de un componente. */
public final class Enter {

    private static final KeyStroke ENTER = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

    private Enter() {
    }

    public static void alPresionar(JComponent c, Runnable accion) {
        c.getInputMap(JComponent.WHEN_FOCUSED).put(ENTER, "accionEnter");
        c.getActionMap().put("accionEnter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.run();
            }
        });
    }
}
