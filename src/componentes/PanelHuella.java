package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Panel del lector de huella. Un solo componente para las tres ventanas que lo usan:
 *  - modoRegistro = false: Préstamos y Devoluciones (identifica al estudiante y muestra sus datos).
 *  - modoRegistro = true : CRUDEstudiantes (elige dedo y captura 3 muestras).
 * Antes estaba copiado completo en Préstamos y Devoluciones, y otra variante en CRUDEstudiantes.
 */
public class PanelHuella extends PanelGrupo implements biometria.AvisoCaptura {

    private static final Color GRIS = new Color(60, 75, 85);

    // Comunes
    private final JLabel lblHuella = new JLabel("Coloque el dedo");
    private final JLabel lblEstado = new JLabel(" ");
    private final BotonSistema botonCapturar = new BotonSistema("Capturar huella");

    // Modo verificación (préstamo / devolución)
    private final JPanel pnlEstudiante = new JPanel(new GridLayout(4, 1, 0, 4));
    private final JLabel lblNombre = new JLabel("—");
    private final JLabel lblDatos = new JLabel("<html>Cédula: —<br>Carrera: —</html>");
    private final JLabel lblActivos = new JLabel("Préstamos activos: — de 3");
    private final JLabel lblEstadoEstudiante = new JLabel("SIN IDENTIFICAR");

    // Modo registro (estudiantes)
    private final JLabel lblDedo = new JLabel("Dedo");
    private final JComboBox<modelo.Dedo> cbxDedo = new JComboBox<>(modelo.Dedo.values());
    private final JProgressBar barMuestras = new JProgressBar(0, 3);
    private final JTextArea txtInfo = new JTextArea(3, 20);
    private final JScrollPane scrInfo = new JScrollPane(txtInfo);

    private boolean modoRegistro = false;

    public PanelHuella() {
        super("Huella");
        setLayout(new GridBagLayout());
        armar();
        setModoRegistro(false);
    }

    private void armar() {
        lblHuella.setOpaque(true);
        lblHuella.setBackground(new Color(242, 249, 248));
        lblHuella.setHorizontalAlignment(SwingConstants.CENTER);
        lblHuella.setBorder(BorderFactory.createDashedBorder(new Color(127, 191, 186), 2, 6, 4, true));
        lblHuella.setFont(util.Estilos.FUENTE);
        lblHuella.setForeground(new Color(90, 110, 115));
        lblHuella.setPreferredSize(new Dimension(190, 190));
        lblHuella.setMinimumSize(new Dimension(190, 190));          // el recuadro nunca se achica
        lblHuella.setHorizontalTextPosition(SwingConstants.CENTER);   // con imagen: texto debajo
        lblHuella.setVerticalTextPosition(SwingConstants.BOTTOM);

        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEstado.setForeground(GRIS);
        botonCapturar.setPreferredSize(new Dimension(200, 36));

        // Tarjeta del estudiante
        pnlEstudiante.setBackground(new Color(247, 249, 250));
        pnlEstudiante.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(util.Estilos.BORDE, 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(util.Estilos.TEXTO);
        for (JLabel l : new JLabel[]{lblDatos, lblActivos}) {
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            l.setForeground(GRIS);
        }
        lblEstadoEstudiante.setOpaque(true);
        lblEstadoEstudiante.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstadoEstudiante.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEstadoEstudiante.setBackground(new Color(225, 230, 233));
        lblEstadoEstudiante.setForeground(GRIS);
        // Ancho preferido pequeño: un nombre o carrera largos se cortan con "..." en vez de
        // ensanchar el panel (si el panel no entra, GridBagLayout aplasta todo lo demás).
        lblNombre.setPreferredSize(new Dimension(10, 24));
        lblDatos.setPreferredSize(new Dimension(10, 40));
        lblActivos.setPreferredSize(new Dimension(10, 20));
        lblEstadoEstudiante.setPreferredSize(new Dimension(10, 40));
        pnlEstudiante.add(lblNombre);
        pnlEstudiante.add(lblDatos);
        pnlEstudiante.add(lblActivos);
        pnlEstudiante.add(lblEstadoEstudiante);

        // Controles de registro
        lblDedo.setFont(util.Estilos.FUENTE);
        cbxDedo.setFont(util.Estilos.FUENTE);
        cbxDedo.setPreferredSize(new Dimension(100, 32));
        cbxDedo.setSelectedItem(modelo.Dedo.INDICE_DERECHO);   // valores fijos: el enum Dedo
        barMuestras.setStringPainted(true);
        barMuestras.setString("0 de 3 muestras");
        barMuestras.setForeground(util.Estilos.VERDE);
        barMuestras.setPreferredSize(new Dimension(100, 20));
        util.Estilos.barra(barMuestras);
        barMuestras.setStringPainted(true);
        txtInfo.setText("Elija el dedo y presione Capturar huella.");
        txtInfo.setEditable(false);
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtInfo.setBackground(new Color(247, 249, 250));
        txtInfo.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        scrInfo.setBorder(BorderFactory.createLineBorder(util.Estilos.BORDE));
        // Ancho pequeño: así el panel no se aplasta cuando es angosto (GridBag usaría los mínimos)
        scrInfo.setPreferredSize(new Dimension(100, 70));

        int fila = 0;
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;
        g.fill = GridBagConstraints.NONE;
        g.gridy = fila++; g.insets = new Insets(6, 6, 10, 6);  add(lblHuella, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridy = fila++; g.insets = new Insets(0, 6, 4, 6);   add(lblDedo, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 8, 6);   add(cbxDedo, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 8, 6);   add(barMuestras, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 8, 6);   add(lblEstado, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 8, 6);   add(scrInfo, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 14, 6);  add(botonCapturar, g);
        g.gridy = fila++; g.insets = new Insets(0, 6, 0, 6);   add(pnlEstudiante, g);

        g.gridy = fila;
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        JPanel relleno = new JPanel();
        relleno.setOpaque(false);
        relleno.setPreferredSize(new Dimension(0, 0));
        add(relleno, g);
    }

    /** false = préstamo/devolución, true = registro de huella. */
    public boolean isModoRegistro() { return modoRegistro; }

    public void setModoRegistro(boolean modoRegistro) {
        this.modoRegistro = modoRegistro;
        lblDedo.setVisible(modoRegistro);
        cbxDedo.setVisible(modoRegistro);
        barMuestras.setVisible(modoRegistro);
        scrInfo.setVisible(modoRegistro);
        pnlEstudiante.setVisible(!modoRegistro);
        lblEstado.setVisible(!modoRegistro);
        botonCapturar.setText("Capturar huella");
        botonCapturar.setVisible(modoRegistro);     // en préstamo/devolución la huella se lee sola
        botonCapturar.setPrimario(true);
        revalidate();
        repaint();
    }

    // ---- Estados de la captura (los usan Préstamos, Devoluciones y CRUDEstudiantes) ----

    private static final Color FONDO_NORMAL = new Color(242, 249, 248);
    private static final Color FONDO_EXITO = new Color(212, 238, 235);
    private static final Color FONDO_FALLO = new Color(253, 241, 232);

    // ---- Avisos del lector durante el registro (AvisoCaptura) ----
    // El lector avisa desde otro hilo: se pasa al hilo de Swing con invokeLater.

    @Override
    public void avisar(int muestrasListas, String mensaje) {
        javax.swing.SwingUtilities.invokeLater(() -> mostrarAvance(muestrasListas, mensaje));
    }

    @Override
    public void imagen(java.awt.image.BufferedImage huella) {
        javax.swing.SwingUtilities.invokeLater(() -> mostrarImagen(huella));
    }

    /** Dibuja la huella leída dentro del recuadro (escalada sin deformarla). */
    public void mostrarImagen(java.awt.image.BufferedImage huella) {
        int lado = 150;
        double escala = Math.min((double) lado / huella.getWidth(), (double) lado / huella.getHeight());
        java.awt.Image chica = huella.getScaledInstance((int) (huella.getWidth() * escala),
                (int) (huella.getHeight() * escala), java.awt.Image.SCALE_SMOOTH);
        lblHuella.setIcon(new javax.swing.ImageIcon(chica));
        lblHuella.setText("");
        lblHuella.setBackground(Color.WHITE);
    }

    /** Vuelve al estado inicial: "Coloque el dedo", sin muestras. */
    public void reiniciar() {
        lblHuella.setIcon(null);
        pintarHuella("Coloque el dedo", FONDO_NORMAL, new Color(90, 110, 115));
        mostrarAvance(0, modoRegistro
                ? "Elija el dedo y presione Capturar huella."
                : "Coloque el dedo en el lector.");
    }

    /** Durante la captura: bloquea el botón y avisa que se está leyendo. */
    public void iniciarCaptura() {
        lblHuella.setIcon(null);
        botonCapturar.setEnabled(false);
        cbxDedo.setEnabled(false);
        pintarHuella("Leyendo...", FONDO_NORMAL, util.Estilos.VERDE_TITULO);
    }

    /** Al terminar la captura (bien o mal) se vuelve a habilitar el botón. */
    public void terminarCaptura() {
        botonCapturar.setEnabled(true);
        cbxDedo.setEnabled(true);
    }

    /** Avance del registro: cuántas muestras van y la instrucción para el usuario. */
    public void mostrarAvance(int muestras, String mensaje) {
        barMuestras.setValue(muestras);
        barMuestras.setString(muestras + " de 3 muestras");
        txtInfo.setForeground(util.Estilos.TEXTO);
        txtInfo.setText(mensaje);
        lblEstado.setText(envolver(mensaje));
    }

    /** Rojo para los avisos importantes del registro (por ejemplo, huella de otro estudiante). */
    private static final Color ROJO_AVISO = new Color(198, 40, 40);

    /**
     * Problema al capturar: el recuadro vuelve a "Coloque el dedo" y el mensaje se muestra
     * solo en el área de texto de abajo. Con alerta = true el texto va en rojo.
     */
    public void mostrarAviso(String mensaje, boolean alerta) {
        lblHuella.setIcon(null);
        pintarHuella("Coloque el dedo", FONDO_NORMAL, new Color(90, 110, 115));
        barMuestras.setValue(0);
        barMuestras.setString("0 de 3 muestras");
        txtInfo.setForeground(alerta ? ROJO_AVISO : util.Estilos.TEXTO);
        txtInfo.setText(mensaje);
        lblEstado.setText(envolver(mensaje));
    }

    /** Resultado final: verde si salió bien, naranja si no. */
    public void mostrarResultado(String texto, boolean exito) {
        pintarHuella(texto, exito ? FONDO_EXITO : FONDO_FALLO,
                exito ? util.Estilos.VERDE_TITULO : new Color(138, 58, 18));
    }

    /** Muestra en el recuadro si el lector está listo o no (no borra el texto informativo). */
    public void mostrarLector(biometria.EstadoLector estado) {
        lblHuella.setIcon(null);
        if (estado.permiteCapturar()) {
            pintarHuella("Coloque el dedo", FONDO_NORMAL, new Color(90, 110, 115));
        } else {
            pintarHuella("Lector " + estado.getDescripcion().toLowerCase(), FONDO_FALLO, new Color(138, 58, 18));
        }
    }

    // ---- Tarjeta del estudiante identificado (préstamo / devolución) ----

    private static final Color FONDO_OK = new Color(212, 238, 235);
    private static final Color FONDO_NO = new Color(253, 241, 232);

    /**
     * Muestra al estudiante que se identificó con la huella.
     * @param estado texto de la franja inferior ("PUEDE LLEVAR 2 LIBROS", "INACTIVO"...)
     * @param ok     verde si puede continuar, naranja si hay algún impedimento
     */
    public void mostrarEstudiante(String nombre, String datos, String activos, String estado, boolean ok) {
        lblNombre.setText(nombre);
        lblNombre.setToolTipText(nombre);
        lblDatos.setText("<html>" + datos.replace("\n", "<br>") + "</html>");   // cada dato en su línea
        lblDatos.setToolTipText(datos);
        lblActivos.setText(activos);
        lblEstadoEstudiante.setText("<html><div style='text-align:center'>" + estado + "</div></html>");
        lblEstadoEstudiante.setBackground(ok ? FONDO_OK : FONDO_NO);
        lblEstadoEstudiante.setForeground(ok ? util.Estilos.VERDE_TITULO : new Color(138, 58, 18));
    }

    /** Vuelve la tarjeta a "sin identificar". */
    public void limpiarEstudiante() {
        lblNombre.setText("—");
        lblDatos.setText("<html>Cédula: —<br>Carrera: —</html>");
        lblActivos.setText("Préstamos activos: —");
        lblEstadoEstudiante.setText("SIN IDENTIFICAR");
        lblEstadoEstudiante.setBackground(new Color(225, 230, 233));
        lblEstadoEstudiante.setForeground(GRIS);
    }

    /** Texto informativo debajo del lector (por ejemplo, las huellas ya registradas). */
    public void setInfo(String texto) {
        txtInfo.setForeground(util.Estilos.TEXTO);
        txtInfo.setText(texto);
        lblEstado.setText(envolver(texto));
    }

    /** Texto en varias líneas para que quepa en el ancho del panel. */
    private static String envolver(String texto) {
        return "<html><div style='text-align:center;width:150px'>" + texto + "</div></html>";
    }

    private void pintarHuella(String texto, Color fondo, Color letra) {
        lblHuella.setText("<html><div style='text-align:center'>" + texto + "</div></html>");
        lblHuella.setBackground(fondo);
        lblHuella.setForeground(letra);
    }

    // ---- Acceso para la lógica ----
    public JLabel getLblHuella() { return lblHuella; }
    public JLabel getLblEstado() { return lblEstado; }
    public BotonSistema getBotonCapturar() { return botonCapturar; }
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblDatos() { return lblDatos; }
    public JLabel getLblActivos() { return lblActivos; }
    public JLabel getLblEstadoEstudiante() { return lblEstadoEstudiante; }
    public JComboBox<modelo.Dedo> getCbxDedo() { return cbxDedo; }
    public JProgressBar getBarMuestras() { return barMuestras; }
    public JTextArea getTxtInfo() { return txtInfo; }
}
