package util;

import javax.swing.*;

public class Navegacion {

    /**
     * Abre el destino y oculta el origen.
     * Cuando el destino se cierre, el origen vuelve a aparecer.
     */
    public static void abrir(JFrame origen, JFrame destino) {
        destino.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        destino.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                origen.setVisible(true);
            }
        });
        destino.setLocationRelativeTo(origen);
        destino.setVisible(true);
        origen.setVisible(false);
    }

    /**
     * Cambia de ventana sin volver atrás (por ejemplo, login -> menú o cerrar sesión).
     * El origen se destruye.
     */
    public static void cambiar(JFrame origen, JFrame destino) {
        destino.setLocationRelativeTo(origen);
        destino.setVisible(true);
        origen.dispose();
    }
}