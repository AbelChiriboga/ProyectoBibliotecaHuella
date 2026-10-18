package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Clase padre de todas las ventanas internas del sistema (todas menos el Login).
 * Pone el encabezado, el pie, el tamaño estándar y el comportamiento del botón Regresar.
 *
 * Cada frame solo diseña su contenido en NetBeans y en el constructor llama:
 *     initComponents();
 *     montar("REGISTRO DE LIBROS", jpnlContenido);
 */
public class VentanaBase extends JFrame {

    public static final Dimension TAMANO = new Dimension(1130, 700);

    protected final Encabezado encabezado = new Encabezado();
    protected final PiePagina pie = new PiePagina();

    public VentanaBase() {
        // Comportamiento por defecto: Regresar cierra esta ventana
        // (util.Navegacion se encarga de mostrar la anterior).
        encabezado.getBotonRegresar().addActionListener(e -> dispose());
    }

    /** Ventana normal: encabezado con Regresar, contenido y pie. */
    protected void montar(String subtitulo, JComponent contenido) {
        encabezado.setSubtitulo(subtitulo);

        // El contenido va dentro de un panel blanco con margen,
        // así nunca se le pisa el borde (por ejemplo, el borde verde de un PanelGrupo).
        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        cuerpo.add(contenido, BorderLayout.CENTER);

        Container cp = getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.add(encabezado, BorderLayout.NORTH);
        cp.add(cuerpo, BorderLayout.CENTER);
        cp.add(pie, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cp.setPreferredSize(TAMANO);
        pack();
        setLocationRelativeTo(null);
    }

    /** Ventana de menú: encabezado con Cerrar sesión y Salir; cerrar la ventana termina el programa. */
    protected void montarMenu(String subtitulo, JComponent contenido) {
        encabezado.setModoMenu(true);
        encabezado.getBotonSalir().addActionListener(e -> confirmarSalida());
        montar(subtitulo, contenido);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void confirmarSalida() {
        int r = JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
