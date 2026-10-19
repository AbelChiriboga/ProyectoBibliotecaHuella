package biblioteca;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        aplicarEstilo();
        iniciarMonitorLector();

        
        javax.swing.SwingUtilities.invokeLater(() -> {
            new vista.Login().setVisible(true);
        });
    }

    /**
     * Vigila el lector de huella mientras el sistema esté abierto: el pie de cada ventana
     * muestra Conectado / Desconectado y, si cambia en pleno uso, aparece un aviso.
     */
    private static void iniciarMonitorLector() {
        biometria.MonitorLector monitor = biometria.MonitorLector.getInstancia();
        monitor.alCambiar(Main::avisarCambioLector);
        monitor.iniciar();
    }

    private static void avisarCambioLector(biometria.EstadoLector estado) {
        componentes.Aviso.mostrar(
                estado.permiteCapturar() ? "Lector de huella conectado" : "Lector de huella desconectado",
                estado.permiteCapturar() ? "Ya puede registrar y leer huellas."
                        : "El sistema sigue funcionando; las operaciones con huella quedan en espera.",
                estado.permiteCapturar());
    }

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
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
}