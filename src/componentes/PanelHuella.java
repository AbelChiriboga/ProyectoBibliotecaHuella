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
public class PanelHuella extends PanelGrupo {

    private static final Color GRIS = new Color(60, 75, 85);

    // Comunes
    private final JLabel lblHuella = new JLabel("Coloque el dedo");
    private final JLabel lblEstado = new JLabel("Esperando lectura del lector...");
    private final BotonSistema botonCapturar = new BotonSistema("Capturar de nuevo");

    // Modo verificación (préstamo / devolución)
    private final JPanel pnlEstudiante = new JPanel(new GridLayout(4, 1, 0, 4));
    private final JLabel lblNombre = new JLabel("—");
    private final JLabel lblDatos = new JLabel("Cédula: —   ·   Carrera: —");
    private final JLabel lblActivos = new JLabel("Préstamos activos: — de 3");
    private final JLabel lblEstadoEstudiante = new JLabel("SIN IDENTIFICAR");

    // Modo registro (estudiantes)
    private final JLabel lblDedo = new JLabel("Dedo");
    private final JComboBox<String> cbxDedo = new JComboBox<>();
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
        pnlEstudiante.add(lblNombre);
        pnlEstudiante.add(lblDatos);
        pnlEstudiante.add(lblActivos);
        pnlEstudiante.add(lblEstadoEstudiante);

        // Controles de registro
        lblDedo.setFont(util.Estilos.FUENTE);
        cbxDedo.setFont(util.Estilos.FUENTE);
        cbxDedo.setPreferredSize(new Dimension(100, 32));
        // Valores fijos (no vienen de una tabla)
        for (String dedo : new String[]{"Índice derecho", "Medio derecho", "Pulgar derecho", "Anular derecho",
                "Meñique derecho", "Índice izquierdo", "Medio izquierdo", "Pulgar izquierdo",
                "Anular izquierdo", "Meñique izquierdo"}) {
            cbxDedo.addItem(dedo);
        }
        barMuestras.setStringPainted(true);
        barMuestras.setString("0 de 3 muestras");
        barMuestras.setForeground(util.Estilos.VERDE);
        barMuestras.setPreferredSize(new Dimension(100, 20));
        util.Estilos.barra(barMuestras);
        barMuestras.setStringPainted(true);
        txtInfo.setText("Seleccione el dedo y presione Capturar huella. Se necesitan 3 muestras del mismo dedo.");
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
        botonCapturar.setText(modoRegistro ? "Capturar huella" : "Capturar de nuevo");
        botonCapturar.setPrimario(modoRegistro);
        revalidate();
        repaint();
    }

    // ---- Acceso para la lógica (se usará después) ----
    public JLabel getLblHuella() { return lblHuella; }
    public JLabel getLblEstado() { return lblEstado; }
    public BotonSistema getBotonCapturar() { return botonCapturar; }
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblDatos() { return lblDatos; }
    public JLabel getLblActivos() { return lblActivos; }
    public JLabel getLblEstadoEstudiante() { return lblEstadoEstudiante; }
    public JComboBox<String> getCbxDedo() { return cbxDedo; }
    public JProgressBar getBarMuestras() { return barMuestras; }
    public JTextArea getTxtInfo() { return txtInfo; }
}
