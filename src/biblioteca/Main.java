package biblioteca;

public class Main {

    public static void main(String[] args) {
        aplicarEstilo();

        // Abrir el login en el hilo de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new vista.Login().setVisible(true);
        });
    }

    /**
     * Usa FlatLaf si está agregada al proyecto (se ve plano y moderno).
     * Si no está, usa Nimbus, que viene incluido en Java.
     */
    private static void aplicarEstilo() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // Si falla todo, se queda el estilo por defecto de Java
            }
        }
    }
}