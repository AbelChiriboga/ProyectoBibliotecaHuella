package util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/** Atajos para eventos que Swing hace largos de escribir. */
public final class Eventos {

    private Eventos() {
    }

    /** Ejecuta la acción cada vez que cambia el texto del campo (al escribir o borrar). */
    public static void alCambiar(JTextComponent campo, Runnable accion) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { accion.run(); }
            @Override public void removeUpdate(DocumentEvent e) { accion.run(); }
            @Override public void changedUpdate(DocumentEvent e) { accion.run(); }
        });
    }
}
