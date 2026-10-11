package biblioteca;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        aplicarEstilo();

        
        javax.swing.SwingUtilities.invokeLater(() -> {
            new vista.Login().setVisible(true);
        });
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