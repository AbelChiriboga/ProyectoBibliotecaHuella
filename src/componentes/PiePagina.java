package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Barra verde inferior común (reemplaza el jPanel6 repetido en todos los frames). */
public class PiePagina extends JPanel {

    private final JLabel lblSistema = new JLabel("Sistema de Préstamos biométrico · FISEI");
    private final JLabel lblLector = new JLabel("Lector: —");

    public PiePagina() {
        super(new BorderLayout());
        setBackground(util.Estilos.VERDE);
        setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        for (JLabel l : new JLabel[]{lblSistema, lblLector}) {
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
        add(lblSistema, BorderLayout.WEST);
        add(lblLector, BorderLayout.EAST);
    }

    /** Texto del estado del lector (por ahora solo visual). */
    public void setTextoLector(String texto) {
        lblLector.setText("Lector: " + texto);
    }
}
