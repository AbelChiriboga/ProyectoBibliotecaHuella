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
    private final JLabel lblLector = new JLabel("Lector: verificando...");
    /** Se guarda la referencia para poder quitarla del monitor cuando la ventana se cierra. */
    private final java.util.function.Consumer<biometria.EstadoLector> oyente = this::mostrarEstado;

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

    /** Texto libre del lector (normalmente lo pone el monitor). */
    public void setTextoLector(String texto) {
        lblLector.setText("Lector: " + texto);
    }

    /** Estado en vivo: el punto es verde claro si se puede capturar y naranja si no. */
    private void mostrarEstado(biometria.EstadoLector estado) {
        String color = estado.permiteCapturar() ? "#B9F6CA" : "#FFCC80";
        lblLector.setText("<html><span style='color:" + color + "'>&#9679;</span> Lector: <b>"
                + estado.getDescripcion() + "</b></html>");
    }

    // El pie escucha al monitor solo mientras está en pantalla
    @Override
    public void addNotify() {
        super.addNotify();
        biometria.MonitorLector.getInstancia().agregarOyente(oyente);
    }

    @Override
    public void removeNotify() {
        biometria.MonitorLector.getInstancia().quitarOyente(oyente);
        super.removeNotify();
    }
}
