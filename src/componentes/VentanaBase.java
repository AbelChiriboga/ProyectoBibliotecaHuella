package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        // Sin la barra de título de Windows; la ventana se mueve arrastrando el encabezado.
        setUndecorated(true);
        util.Arrastre.permitir(this, encabezado);
        // Comportamiento por defecto: Regresar cierra esta ventana
        // (util.Navegacion se encarga de mostrar la anterior).
        encabezado.getBotonRegresar().addActionListener(e -> dispose());
    }

    /** Ventana normal: encabezado con Regresar, contenido y pie. */
    protected void montar(String subtitulo, JComponent contenido) {
        encabezado.setSubtitulo(subtitulo);
        // Datos del usuario que inició sesión (si se abre el frame suelto, se quedan los de ejemplo)
        servicio.Sesion.getInstancia().getUsuarioActual().ifPresent(
                u -> encabezado.setUsuario(u.getNombreCompleto(), u.getCedula(), u.getTipo()));

        // El contenido va dentro de un panel blanco con margen,
        // así nunca se le pisa el borde (por ejemplo, el borde verde de un PanelGrupo).
        // Si la pantalla es más pequeña que la ventana (por ejemplo, laptop con escala 125 %),
        // el contenido NO se aplasta: aparece una barra de desplazamiento.
        JScrollPane cuerpo = new JScrollPane(new Lienzo(contenido));
        cuerpo.setBorder(BorderFactory.createEmptyBorder());
        cuerpo.getViewport().setBackground(Color.WHITE);
        cuerpo.getVerticalScrollBar().setUnitIncrement(16);
        cuerpo.getHorizontalScrollBar().setUnitIncrement(16);

        Container cp = getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.add(encabezado, BorderLayout.NORTH);
        cp.add(cuerpo, BorderLayout.CENTER);
        cp.add(pie, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cp.setPreferredSize(TAMANO);
        pack();
        ajustarAPantalla();
        setLocationRelativeTo(null);
    }

    /** Nunca más grande que el área útil de la pantalla (sin la barra de tareas). */
    private void ajustarAPantalla() {
        java.awt.Rectangle util = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setSize(Math.min(getWidth(), util.width), Math.min(getHeight(), util.height));
    }

    /**
     * Panel blanco con margen que envuelve el contenido. Ocupa todo el espacio disponible
     * y, solo si no alcanza, mantiene su tamaño preferido para que se pueda desplazar.
     */
    private static final class Lienzo extends JPanel implements javax.swing.Scrollable {

        /** Alto desde el que el contenido entra sin aplastarse; con menos, aparece la barra. */
        private static final int ALTO_MINIMO = 500;

        Lienzo(JComponent contenido) {
            super(new BorderLayout());
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            add(contenido, BorderLayout.CENTER);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(java.awt.Rectangle visible, int orientacion, int direccion) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(java.awt.Rectangle visible, int orientacion, int direccion) {
            return orientacion == javax.swing.SwingConstants.VERTICAL ? visible.height : visible.width;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return getParent().getWidth() >= getMinimumSize().width;   // a lo ancho casi nunca hace falta barra
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return getParent().getHeight() >= ALTO_MINIMO;
        }
    }

    /**
     * Ventana de menú: encabezado con Cerrar sesión y Salir; cerrar la ventana termina el programa.
     * pantallaInicio es la ventana a la que vuelve al cerrar sesión (se pasa Login::new),
     * así este paquete no depende del paquete vista.
     */
    protected void montarMenu(String subtitulo, JComponent contenido, Supplier<JFrame> pantallaInicio) {
        encabezado.setModoMenu(true);
        encabezado.getBotonSalir().addActionListener(e -> confirmarSalida());
        encabezado.getBotonCerrarSesion().addActionListener(e -> cerrarSesion(pantallaInicio));
        montar(subtitulo, contenido);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * La ventana recibe el estado del lector ahora y cada vez que cambie.
     * Al cerrar la ventana deja de escucharlo.
     */
    protected void alCambiarLector(java.util.function.Consumer<biometria.EstadoLector> accion) {
        biometria.MonitorLector monitor = biometria.MonitorLector.getInstancia();
        monitor.iniciar();                       // por si la ventana se abre sin pasar por Main
        monitor.agregarOyente(accion);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                monitor.quitarOyente(accion);
            }
        });
    }

    /** Ejecuta una acción y, si falla con una excepción del sistema, muestra el mensaje. */
    protected boolean ejecutar(Runnable accion) {
        return util.Mensajes.ejecutar(this, accion);
    }

    private void cerrarSesion(Supplier<JFrame> pantallaInicio) {
        if (util.Mensajes.confirmar(this, "¿Desea cerrar la sesión?")) {
            new servicio.AutenticacionService().cerrarSesion();
            util.Navegacion.cambiar(this, pantallaInicio.get());
        }
    }

    private void confirmarSalida() {
        int r = JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
