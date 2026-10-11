/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class Bitacora extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Bitacora.class.getName());
    private final javax.swing.JLabel lblIntentosHoy = new javax.swing.JLabel("0");
    private final javax.swing.JLabel lblTasaExito = new javax.swing.JLabel("0%");
    private final javax.swing.JLabel lblFallidosHoy = new javax.swing.JLabel("0");
    private final javax.swing.JLabel lblPuntaje = new javax.swing.JLabel("0");
    private final javax.swing.JLabel lblSubIntentos = new javax.swing.JLabel("0 en el periodo");
    private final javax.swing.JLabel lblSubTasa = new javax.swing.JLabel("0 de 0 en el periodo");
    private final javax.swing.JLabel lblSubFallidos = new javax.swing.JLabel("0 en el periodo");
    private final javax.swing.JLabel lblSubPuntaje = new javax.swing.JLabel("Rechazados: 0 · Umbral: 50");

    private final GraficoBarras grafico = new GraficoBarras();
    private final javax.swing.JProgressBar barPrestamos = new javax.swing.JProgressBar();
    private final javax.swing.JProgressBar barDevoluciones = new javax.swing.JProgressBar();
    private final javax.swing.JProgressBar barRegistros = new javax.swing.JProgressBar();
    private final javax.swing.JLabel lblNotaUmbral = new javax.swing.JLabel();

    private final javax.swing.JTable tblDetalle = new javax.swing.JTable();
    private final javax.swing.JScrollPane scrDetalle = new javax.swing.JScrollPane(tblDetalle);
    public Bitacora() {
        initComponents();
        util.Estilos.reloj(jlblFecha, jlblHora);
        util.Estilos.icono(jbtnRegresar, "Regresar.png", 50, true);
        ajustarDiseno();
    }

    private void ajustarDiseno() {
        jLabel13.setText("BITÁCORA DE AUTENTICACIÓN");

        armarFiltros();
        armarTarjetas();
        armarGraficos();
        armarDetalle();

        // ---- Distribución vertical: filtros, tarjetas, gráficos, detalle ----
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.GridBagLayout());
        jPanel1.setBackground(java.awt.Color.WHITE);

        javax.swing.JPanel tarjetas = new javax.swing.JPanel(new java.awt.GridLayout(1, 4, 16, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(jpnlIntenetosHoy);
        tarjetas.add(jpnlTazaExito);
        tarjetas.add(jpnlFallidoshoy);
        tarjetas.add(jpnlPunPromedio);

        javax.swing.JPanel graficos = new javax.swing.JPanel(new java.awt.BorderLayout(16, 0));
        graficos.setOpaque(false);
        graficos.add(jpnlIntentos, java.awt.BorderLayout.CENTER);
        graficos.add(jpnlOperación, java.awt.BorderLayout.EAST);
        jpnlOperación.setPreferredSize(new java.awt.Dimension(320, 280));

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.gridy = 0; g.insets = new java.awt.Insets(0, 0, 16, 0); jPanel1.add(jpnlFiltros, g);
        g.gridy = 1; g.insets = new java.awt.Insets(0, 0, 16, 0); jPanel1.add(tarjetas, g);
        g.gridy = 2; g.insets = new java.awt.Insets(0, 0, 16, 0); jPanel1.add(graficos, g);
        g.gridy = 3; g.insets = new java.awt.Insets(0, 0, 0, 0);  jPanel1.add(jpnlDetalles, g);

        // ---- Scroll solo vertical: el contenido se ajusta al ancho de la ventana ----
        PanelScroll lienzo = new PanelScroll();
        lienzo.add(jPanel1, java.awt.BorderLayout.CENTER);
        jScrollPane1.setViewportView(lienzo);
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        javax.swing.JPanel contenedor = new javax.swing.JPanel(new java.awt.BorderLayout());
        contenedor.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        // ---- Encabezado + contenido + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, contenedor, jPanel6);

        // Datos de ejemplo solo para ver el diseño (luego vienen de la BD)
        datosDeEjemplo();
    }

    /** Fila de filtros: desde, hasta, operación, resultado y botón. */
    private void armarFiltros() {
        util.Estilos.grupo(jpnlFiltros, "Filtros");
        jpnlFiltros.removeAll();
        jpnlFiltros.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Desde");
        jLabel2.setText("Hasta");
        jLabel9.setText("Operación");
        jLabel7.setText("Resultado");

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.setDateFormatString("dd/MM/yyyy");

        jComboBox1.removeAllItems();   // valores fijos
        for (String s : new String[]{"Todas", "Registro de huella", "Préstamo", "Devolución"}) {
            jComboBox1.addItem(s);
        }
        jComboBox2.removeAllItems();
        for (String s : new String[]{"Todos", "Éxito", "Fallo"}) {
            jComboBox2.addItem(s);
        }

        util.Estilos.boton(jbtnFiltrar);
        jbtnFiltrar.setText("Aplicar");

        javax.swing.JLabel[] etiquetas = {jLabel1, jLabel2, jLabel9, jLabel7};
        javax.swing.JComponent[] campos = {jDateChooser1, jDateChooser2, jComboBox1, jComboBox2};

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        for (int i = 0; i < 4; i++) {
            etiquetas[i].setFont(util.Estilos.FUENTE);
            campos[i].setFont(util.Estilos.FUENTE);
            campos[i].setPreferredSize(new java.awt.Dimension(150, 32));

            g.gridx = i;
            g.gridy = 0;
            g.insets = new java.awt.Insets(0, 6, 4, 6);
            jpnlFiltros.add(etiquetas[i], g);
            g.gridy = 1;
            g.insets = new java.awt.Insets(0, 6, 4, 6);
            jpnlFiltros.add(campos[i], g);
        }
        jbtnFiltrar.setPreferredSize(new java.awt.Dimension(110, 32));
        g.gridx = 4;
        g.gridy = 1;
        g.weightx = 0;
        jpnlFiltros.add(jbtnFiltrar, g);
    }

    /** Cuatro tarjetas: título, número grande y una línea de detalle. */
    private void armarTarjetas() {
        java.awt.Color verdeTitulo = util.Estilos.VERDE_TITULO;
        java.awt.Color naranja = new java.awt.Color(138, 58, 18);

        tarjeta(jpnlIntenetosHoy, "Intentos hoy", lblIntentosHoy, lblSubIntentos,
                util.Estilos.TEXTO, java.awt.Color.WHITE, util.Estilos.BORDE);
        tarjeta(jpnlTazaExito, "Tasa de éxito", lblTasaExito, lblSubTasa,
                verdeTitulo, java.awt.Color.WHITE, util.Estilos.VERDE);
        tarjeta(jpnlFallidoshoy, "Fallidos hoy", lblFallidosHoy, lblSubFallidos,
                naranja, new java.awt.Color(253, 241, 232), new java.awt.Color(224, 179, 147));
        tarjeta(jpnlPunPromedio, "Puntaje promedio", lblPuntaje, lblSubPuntaje,
                verdeTitulo, java.awt.Color.WHITE, util.Estilos.VERDE);
    }

    private void tarjeta(javax.swing.JPanel panel, String titulo, javax.swing.JLabel valor,
                         javax.swing.JLabel sub, java.awt.Color colorValor,
                         java.awt.Color fondo, java.awt.Color borde) {
        panel.removeAll();
        panel.setLayout(new java.awt.BorderLayout(0, 2));
        panel.setBackground(fondo);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(borde, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel(titulo);
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblTitulo.setForeground(new java.awt.Color(80, 90, 100));

        valor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        valor.setForeground(colorValor);

        sub.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        sub.setForeground(new java.awt.Color(80, 90, 100));

        panel.add(lblTitulo, java.awt.BorderLayout.NORTH);
        panel.add(valor, java.awt.BorderLayout.CENTER);
        panel.add(sub, java.awt.BorderLayout.SOUTH);
    }

    /** Gráfico de intentos por día y barras por operación. */
    private void armarGraficos() {
        // ---- Intentos por día ----
        util.Estilos.grupo(jpnlIntentos, "Intentos por día");
        jpnlIntentos.removeAll();
        jpnlIntentos.setLayout(new java.awt.BorderLayout());
        jpnlIntentos.setPreferredSize(new java.awt.Dimension(400, 280));
        grafico.setBackground(java.awt.Color.WHITE);
        jpnlIntentos.add(grafico, java.awt.BorderLayout.CENTER);

        // ---- Por operación ----
        util.Estilos.grupo(jpnlOperación, "Por operación");
        jpnlOperación.removeAll();
        jpnlOperación.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        int fila = 0;
        for (Object[] o : new Object[][]{{"Préstamos", barPrestamos},
                                         {"Devoluciones", barDevoluciones},
                                         {"Registro de huella", barRegistros}}) {
            javax.swing.JLabel lbl = new javax.swing.JLabel((String) o[0]);
            lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            javax.swing.JProgressBar bar = (javax.swing.JProgressBar) o[1];
            bar.setStringPainted(true);
            bar.setForeground(util.Estilos.VERDE);
            bar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
            bar.setPreferredSize(new java.awt.Dimension(100, 16));

            g.gridy = fila++; g.insets = new java.awt.Insets(4, 6, 2, 6);  jpnlOperación.add(lbl, g);
            g.gridy = fila++; g.insets = new java.awt.Insets(0, 6, 10, 6); jpnlOperación.add(bar, g);
        }

        lblNotaUmbral.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblNotaUmbral.setForeground(new java.awt.Color(80, 95, 105));
        lblNotaUmbral.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(227, 232, 235)),
                javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0)));
        g.gridy = fila++;
        g.insets = new java.awt.Insets(4, 6, 0, 6);
        jpnlOperación.add(lblNotaUmbral, g);

        g.gridy = fila;
        g.weighty = 1;
        g.fill = java.awt.GridBagConstraints.BOTH;
        javax.swing.JPanel relleno = new javax.swing.JPanel();
        relleno.setOpaque(false);
        jpnlOperación.add(relleno, g);
    }

    /** Tabla de detalle de intentos. */
    private void armarDetalle() {
        util.Estilos.grupo(jpnlDetalles, "Detalle de intentos");
        jpnlDetalles.removeAll();
        jpnlDetalles.setLayout(new java.awt.BorderLayout());
        util.Estilos.tabla(tblDetalle, scrDetalle,
                "Fecha y hora", "Cédula", "Estudiante", "Operación", "Resultado", "Puntaje", "Detalle");
        scrDetalle.setPreferredSize(new java.awt.Dimension(400, 230));
        jpnlDetalles.add(scrDetalle, java.awt.BorderLayout.CENTER);
    }

    /** Solo para ver el diseño. Se reemplaza con los datos reales de la BD. */
    private void datosDeEjemplo() {
        lblIntentosHoy.setText("12");
        lblSubIntentos.setText("97 en el periodo");
        lblTasaExito.setText("92%");
        lblSubTasa.setText("85 de 97 en el periodo");
        lblFallidosHoy.setText("1");
        lblSubFallidos.setText("12 en el periodo");
        lblPuntaje.setText("78");
        lblSubPuntaje.setText("Rechazados: 31 · Umbral: 50");

        grafico.setDatos(new String[]{"04/10", "05/10", "06/10", "07/10", "08/10", "09/10", "10/10"},
                         new int[]{12, 15, 9, 14, 18, 6, 11},
                         new int[]{2, 1, 3, 1, 4, 0, 1});

        ponerBarra(barPrestamos, 48, 97);
        ponerBarra(barDevoluciones, 37, 97);
        ponerBarra(barRegistros, 12, 97);
        lblNotaUmbral.setText("Rechazados promedian 31 puntos (umbral 50).");
    }

    private void ponerBarra(javax.swing.JProgressBar bar, int valor, int total) {
        bar.setMaximum(Math.max(total, 1));
        bar.setValue(valor);
        bar.setString(String.valueOf(valor));
    }

    // =====================================================================
    //  Clases internas de apoyo
    // =====================================================================

    /** Panel que se ajusta al ancho del scroll (así solo hay scroll vertical). */
    private static class PanelScroll extends javax.swing.JPanel implements javax.swing.Scrollable {
        PanelScroll() {
            super(new java.awt.BorderLayout());
            setBackground(java.awt.Color.WHITE);
        }
        @Override public java.awt.Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(java.awt.Rectangle r, int o, int d) { return 16; }
        @Override public int getScrollableBlockIncrement(java.awt.Rectangle r, int o, int d) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }

    /** Gráfico de barras agrupadas (éxitos y fallos por día), dibujado a mano. */
    private static class GraficoBarras extends javax.swing.JPanel {
        private String[] etiquetas = {};
        private int[] exitos = {};
        private int[] fallos = {};

        void setDatos(String[] etiquetas, int[] exitos, int[] fallos) {
            this.etiquetas = etiquetas;
            this.exitos = exitos;
            this.fallos = fallos;
            repaint();
        }

        @Override
        protected void paintComponent(java.awt.Graphics g0) {
            super.paintComponent(g0);
            java.awt.Graphics2D g = (java.awt.Graphics2D) g0.create();
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            java.awt.FontMetrics fm = g.getFontMetrics();

            java.awt.Color verde = new java.awt.Color(0, 153, 153);
            java.awt.Color naranja = new java.awt.Color(204, 90, 30);
            java.awt.Color gris = new java.awt.Color(90, 106, 116);

            int w = getWidth(), h = getHeight();
            int izq = 36, der = 10, arriba = 30, abajo = 26;
            int ancho = w - izq - der;
            int alto = h - arriba - abajo;

            // Leyenda
            g.setColor(verde);
            g.fillRoundRect(izq, 6, 12, 12, 3, 3);
            g.setColor(gris);
            g.drawString("Éxitos", izq + 18, 17);
            g.setColor(naranja);
            g.fillRoundRect(izq + 80, 6, 12, 12, 3, 3);
            g.setColor(gris);
            g.drawString("Fallos", izq + 98, 17);

            // Escala
            int max = 1;
            for (int i = 0; i < exitos.length; i++) {
                max = Math.max(max, Math.max(exitos[i], fallos[i]));
            }
            max = ((max + 4) / 5) * 5;

            for (int i = 0; i <= 5; i++) {
                int y = arriba + alto - alto * i / 5;
                g.setColor(new java.awt.Color(227, 232, 235));
                g.drawLine(izq, y, w - der, y);
                String v = String.valueOf(max * i / 5);
                g.setColor(gris);
                g.drawString(v, izq - 6 - fm.stringWidth(v), y + 4);
            }

            // Barras
            int n = etiquetas.length;
            if (n == 0) {
                g.dispose();
                return;
            }
            double grupo = (double) ancho / n;
            int barra = (int) Math.max(4, grupo * 0.3);
            for (int i = 0; i < n; i++) {
                int x = (int) (izq + i * grupo + grupo * 0.18);
                int h1 = alto * exitos[i] / max;
                int h2 = alto * fallos[i] / max;

                g.setColor(verde);
                g.fillRoundRect(x, arriba + alto - h1, barra, h1, 4, 4);
                g.setColor(naranja);
                g.fillRoundRect(x + barra + 4, arriba + alto - h2, barra, h2, 4, 4);

                g.setColor(gris);
                String t = etiquetas[i];
                g.drawString(t, x + barra + 2 - fm.stringWidth(t) / 2, h - 8);
            }
            g.dispose();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jpnlInicio = new javax.swing.JPanel();
        jbtnRegresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jlblNombre = new javax.swing.JLabel();
        jlblFecha = new javax.swing.JLabel();
        jlblHora = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jpnlFiltros = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jbtnFiltrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jpnlIntentos = new javax.swing.JPanel();
        jpnlDetalles = new javax.swing.JPanel();
        jpnlOperación = new javax.swing.JPanel();
        jpnlIntenetosHoy = new javax.swing.JPanel();
        jpnlTazaExito = new javax.swing.JPanel();
        jpnlFallidoshoy = new javax.swing.JPanel();
        jpnlPunPromedio = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setPreferredSize(new java.awt.Dimension(1442, 30));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sistema de Prestamos biométrico · FISEI");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Lector:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jpnlInicio.setBackground(new java.awt.Color(0, 153, 153));

        jbtnRegresar.setText("Regresar");
        jbtnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRegresar.addActionListener(this::jbtnRegresarActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("USUARIO");

        jlblNombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNombre.setForeground(new java.awt.Color(255, 255, 255));
        jlblNombre.setText("NOMBRE_APELLIDO");

        jlblFecha.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        jlblFecha.setForeground(new java.awt.Color(255, 255, 255));
        jlblFecha.setText("jLabel4");

        jlblHora.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        jlblHora.setForeground(new java.awt.Color(255, 255, 255));
        jlblHora.setText("jLabel5");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Día:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hora:");

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("BIBLIOTECA DIGITAL FISEI");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BITÁCORA");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("C.I: 0000000000");

        javax.swing.GroupLayout jpnlInicioLayout = new javax.swing.GroupLayout(jpnlInicio);
        jpnlInicio.setLayout(jpnlInicioLayout);
        jpnlInicioLayout.setHorizontalGroup(
            jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addGap(75, 75, 75)
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblNombre, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(14, 14, 14))
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addComponent(jlblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jpnlInicioLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInicioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215))
        );
        jpnlInicioLayout.setVerticalGroup(
            jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlInicioLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addComponent(jbtnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblHora)
                            .addComponent(jLabel5)
                            .addComponent(jlblFecha)
                            .addComponent(jLabel4))
                        .addContainerGap())
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jpnlFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel1.setText("Desde");

        jbtnFiltrar.setText("Filtrar");

        jLabel2.setText("Hasta");

        jLabel7.setText("Resultado");

        jLabel9.setText("Operación");

        javax.swing.GroupLayout jpnlFiltrosLayout = new javax.swing.GroupLayout(jpnlFiltros);
        jpnlFiltros.setLayout(jpnlFiltrosLayout);
        jpnlFiltrosLayout.setHorizontalGroup(
            jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                    .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlFiltrosLayout.setVerticalGroup(
            jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFiltrosLayout.createSequentialGroup()
                .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnFiltrar))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jpnlIntentos.setBorder(javax.swing.BorderFactory.createTitledBorder("Intentos"));

        javax.swing.GroupLayout jpnlIntentosLayout = new javax.swing.GroupLayout(jpnlIntentos);
        jpnlIntentos.setLayout(jpnlIntentosLayout);
        jpnlIntentosLayout.setHorizontalGroup(
            jpnlIntentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );
        jpnlIntentosLayout.setVerticalGroup(
            jpnlIntentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        jpnlDetalles.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));

        javax.swing.GroupLayout jpnlDetallesLayout = new javax.swing.GroupLayout(jpnlDetalles);
        jpnlDetalles.setLayout(jpnlDetallesLayout);
        jpnlDetallesLayout.setHorizontalGroup(
            jpnlDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpnlDetallesLayout.setVerticalGroup(
            jpnlDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jpnlOperación.setBorder(javax.swing.BorderFactory.createTitledBorder("Operación"));

        javax.swing.GroupLayout jpnlOperaciónLayout = new javax.swing.GroupLayout(jpnlOperación);
        jpnlOperación.setLayout(jpnlOperaciónLayout);
        jpnlOperaciónLayout.setHorizontalGroup(
            jpnlOperaciónLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpnlOperaciónLayout.setVerticalGroup(
            jpnlOperaciónLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnlIntenetosHoyLayout = new javax.swing.GroupLayout(jpnlIntenetosHoy);
        jpnlIntenetosHoy.setLayout(jpnlIntenetosHoyLayout);
        jpnlIntenetosHoyLayout.setHorizontalGroup(
            jpnlIntenetosHoyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jpnlIntenetosHoyLayout.setVerticalGroup(
            jpnlIntenetosHoyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnlTazaExitoLayout = new javax.swing.GroupLayout(jpnlTazaExito);
        jpnlTazaExito.setLayout(jpnlTazaExitoLayout);
        jpnlTazaExitoLayout.setHorizontalGroup(
            jpnlTazaExitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jpnlTazaExitoLayout.setVerticalGroup(
            jpnlTazaExitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnlFallidoshoyLayout = new javax.swing.GroupLayout(jpnlFallidoshoy);
        jpnlFallidoshoy.setLayout(jpnlFallidoshoyLayout);
        jpnlFallidoshoyLayout.setHorizontalGroup(
            jpnlFallidoshoyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jpnlFallidoshoyLayout.setVerticalGroup(
            jpnlFallidoshoyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnlPunPromedioLayout = new javax.swing.GroupLayout(jpnlPunPromedio);
        jpnlPunPromedio.setLayout(jpnlPunPromedioLayout);
        jpnlPunPromedioLayout.setHorizontalGroup(
            jpnlPunPromedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jpnlPunPromedioLayout.setVerticalGroup(
            jpnlPunPromedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpnlDetalles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlFiltros, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jpnlIntentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlOperación, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(380, 380, 380))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlIntenetosHoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jpnlTazaExito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jpnlFallidoshoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jpnlPunPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpnlIntenetosHoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlTazaExito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlFallidoshoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlPunPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlIntentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlOperación, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jpnlInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegresarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jbtnRegresarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Bitacora().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnFiltrar;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpnlDetalles;
    private javax.swing.JPanel jpnlFallidoshoy;
    private javax.swing.JPanel jpnlFiltros;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlIntenetosHoy;
    private javax.swing.JPanel jpnlIntentos;
    private javax.swing.JPanel jpnlOperación;
    private javax.swing.JPanel jpnlPunPromedio;
    private javax.swing.JPanel jpnlTazaExito;
    // End of variables declaration//GEN-END:variables
}
