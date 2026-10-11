/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class Devoluciones extends javax.swing.JFrame {
    
    private final javax.swing.JPanel pnlContenido = new javax.swing.JPanel();
    private final javax.swing.JLabel lblNombreEst = new javax.swing.JLabel("—");
    private final javax.swing.JLabel lblDatosEst = new javax.swing.JLabel("Cédula: —   ·   Carrera: —");
    private final javax.swing.JLabel lblActivos = new javax.swing.JLabel("Préstamos activos: — de 3");
    private final javax.swing.JLabel lblEstadoEst = new javax.swing.JLabel("SIN IDENTIFICAR");
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Devoluciones.class.getName());

    /**
     * Creates new form Devoluciones
     */
    public Devoluciones() {
        initComponents();
        util.Estilos.reloj(jlblFecha, jlblHora);
        util.Estilos.icono(jbtnRegresar, "Regresar.png", 50, true);
        ajustarDiseno();
    }
    
    private void ajustarDiseno() {
        jLabel13.setText("DEVOLUCIÓN DE LIBROS");

        // ---- Contenido: huella a la izquierda, préstamos a la derecha ----
        pnlContenido.setLayout(new java.awt.BorderLayout(16, 0));
        pnlContenido.add(jpnlHuella, java.awt.BorderLayout.WEST);
        pnlContenido.add(jpnlPrestamos, java.awt.BorderLayout.CENTER);

        util.Estilos.grupo(jpnlHuella, "Huella del estudiante");
        util.Estilos.grupo(jpnlPrestamos, "Préstamos activos del estudiante");
        jpnlHuella.setPreferredSize(new java.awt.Dimension(300, 0));

        armarPanelHuella();
        armarPanelPrestamos();

        // ---- Encabezado + contenido + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, pnlContenido, jPanel6);
    }

    /** Lado izquierdo: lectura de huella y datos del estudiante identificado. */
    private void armarPanelHuella() {
        jpnlHuella.removeAll();
        jpnlHuella.setLayout(new java.awt.GridBagLayout());

        jlblHuella.setOpaque(true);
        jlblHuella.setBackground(new java.awt.Color(242, 249, 248));
        jlblHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblHuella.setBorder(javax.swing.BorderFactory.createDashedBorder(
                new java.awt.Color(127, 191, 186), 2, 6, 4, true));
        jlblHuella.setFont(util.Estilos.FUENTE);
        jlblHuella.setForeground(new java.awt.Color(90, 110, 115));
        jlblHuella.setText("Coloque el dedo");
        jlblHuella.setPreferredSize(new java.awt.Dimension(190, 210));

        jlblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jlblInfo.setForeground(new java.awt.Color(60, 75, 85));
        jlblInfo.setText("Esperando lectura del lector...");

        util.Estilos.boton(jbtnCapNuevo);
        jbtnCapNuevo.setText("Capturar de nuevo");
        jbtnCapNuevo.setPreferredSize(new java.awt.Dimension(200, 36));

        javax.swing.JPanel pnlEst = new javax.swing.JPanel(new java.awt.GridLayout(4, 1, 0, 4));
        pnlEst.setBackground(new java.awt.Color(247, 249, 250));
        pnlEst.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(util.Estilos.BORDE, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        lblNombreEst.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblNombreEst.setForeground(util.Estilos.TEXTO);
        lblDatosEst.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblDatosEst.setForeground(new java.awt.Color(60, 75, 85));
        lblActivos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblActivos.setForeground(new java.awt.Color(60, 75, 85));

        lblEstadoEst.setOpaque(true);
        lblEstadoEst.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstadoEst.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblEstadoEst.setBackground(new java.awt.Color(225, 230, 233));
        lblEstadoEst.setForeground(new java.awt.Color(60, 75, 85));

        pnlEst.add(lblNombreEst);
        pnlEst.add(lblDatosEst);
        pnlEst.add(lblActivos);
        pnlEst.add(lblEstadoEst);

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;

        g.gridy = 0;
        g.fill = java.awt.GridBagConstraints.NONE;
        g.insets = new java.awt.Insets(6, 6, 10, 6);
        jpnlHuella.add(jlblHuella, g);

        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.gridy = 1;
        g.insets = new java.awt.Insets(0, 6, 8, 6);
        jpnlHuella.add(jlblInfo, g);

        g.gridy = 2;
        g.insets = new java.awt.Insets(0, 6, 14, 6);
        jpnlHuella.add(jbtnCapNuevo, g);

        g.gridy = 3;
        g.insets = new java.awt.Insets(0, 6, 0, 6);
        jpnlHuella.add(pnlEst, g);

        g.gridy = 4;
        g.weighty = 1;
        g.fill = java.awt.GridBagConstraints.BOTH;
        javax.swing.JPanel relleno = new javax.swing.JPanel();
        relleno.setOpaque(false);
        jpnlHuella.add(relleno, g);
    }

    /** Lado derecho: tabla de préstamos activos, tarjetas de multa y botones. */
    private void armarPanelPrestamos() {
        jpnlPrestamos.removeAll();
        jpnlPrestamos.setLayout(new java.awt.BorderLayout(0, 14));

        // ---- Tabla con casillas: marcar los libros que se devuelven ----
        util.Estilos.tabla(jtblPrestamos, jScrollPane1, "", "Código", "Título", "Prestado", "Fecha límite", "Estado");
        jtblPrestamos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{"", "Código", "Título", "Prestado", "Fecha límite", "Estado"}) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int fila, int col) {
                return col == 0;
            }
        });
        jtblPrestamos.getColumnModel().getColumn(0).setMaxWidth(40);
        jtblPrestamos.getColumnModel().getColumn(1).setPreferredWidth(90);
        jtblPrestamos.getColumnModel().getColumn(2).setPreferredWidth(240);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));

        // ---- Tres tarjetas: días de retraso, multa por día, multa por cobrar ----
        javax.swing.JPanel tarjetas = new javax.swing.JPanel(new java.awt.GridLayout(1, 3, 12, 0));
        tarjetas.setOpaque(false);

        java.awt.Color normalBorde = util.Estilos.BORDE;
        java.awt.Color naranjaBorde = new java.awt.Color(224, 179, 147);
        java.awt.Color naranjaFondo = new java.awt.Color(255, 255, 255);
        java.awt.Color naranjaTexto = new java.awt.Color(138, 58, 18);

        tarjeta(jpblDiasRetraso, jLabel1, "Días de retraso", jLabel2, "0",
                java.awt.Color.WHITE, normalBorde, util.Estilos.TEXTO);
        tarjeta(jpnlMulDia, jLabel7, "Multa por día", jLabel9, "$0.00",
                java.awt.Color.WHITE, normalBorde, util.Estilos.TEXTO);
        tarjeta(jpnlTotalPagar, jLabel10, "Multa a generar", jLabel11, "$0.00",
                naranjaFondo, naranjaBorde, naranjaTexto);

        tarjetas.add(jpblDiasRetraso);
        tarjetas.add(jpnlMulDia);
        tarjetas.add(jpnlTotalPagar);

        // ---- Botones ----
        javax.swing.JPanel botones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        util.Estilos.boton(jbtnCancelar);
        util.Estilos.boton(jbtnConfirmar);
        jbtnCancelar.setText("Cancelar");
        jbtnConfirmar.setText("Confirmar devolución");
        jbtnCancelar.setPreferredSize(new java.awt.Dimension(110, 36));
        jbtnConfirmar.setPreferredSize(new java.awt.Dimension(180, 36));
        botones.add(jbtnCancelar);
        botones.add(jbtnConfirmar);

        // Parte de abajo: tarjetas y, debajo, los botones
        javax.swing.JPanel abajo = new javax.swing.JPanel(new java.awt.BorderLayout(0, 14));
        abajo.setOpaque(false);
        abajo.add(tarjetas, java.awt.BorderLayout.CENTER);
        abajo.add(botones, java.awt.BorderLayout.SOUTH);
        botones.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(227, 232, 235)),
                javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0)));

        jpnlPrestamos.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jpnlPrestamos.add(abajo, java.awt.BorderLayout.SOUTH);
    }

    /** Tarjeta con un título pequeño arriba y un número grande abajo. */
    private void tarjeta(javax.swing.JPanel panel, javax.swing.JLabel titulo, String textoTitulo,
                         javax.swing.JLabel valor, String textoValor,
                         java.awt.Color fondo, java.awt.Color borde, java.awt.Color colorValor) {
        panel.removeAll();
        panel.setLayout(new java.awt.BorderLayout(0, 2));
        panel.setBackground(fondo);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(borde, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        panel.setPreferredSize(new java.awt.Dimension(150, 80));

        titulo.setText(textoTitulo);
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        titulo.setForeground(new java.awt.Color(80, 90, 100));

        valor.setText(textoValor);
        valor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        valor.setForeground(colorValor);

        panel.add(titulo, java.awt.BorderLayout.NORTH);
        panel.add(valor, java.awt.BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jpnlHuella = new javax.swing.JPanel();
        jlblHuella = new javax.swing.JLabel();
        jlblInfo = new javax.swing.JLabel();
        jbtnCapNuevo = new javax.swing.JButton();
        jpnlPrestamos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPrestamos = new javax.swing.JTable();
        jpblDiasRetraso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jpnlMulDia = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jpnlTotalPagar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jbtnCancelar = new javax.swing.JButton();
        jbtnConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jLabel13.setText("DEVOLUCIÓN DE LIBROS");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jpnlHuella.setBackground(new java.awt.Color(255, 255, 255));
        jpnlHuella.setBorder(javax.swing.BorderFactory.createTitledBorder("Huella"));

        jbtnCapNuevo.setText("Capturar de Nuevo");

        javax.swing.GroupLayout jpnlHuellaLayout = new javax.swing.GroupLayout(jpnlHuella);
        jpnlHuella.setLayout(jpnlHuellaLayout);
        jpnlHuellaLayout.setHorizontalGroup(
            jpnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlHuellaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlHuellaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlblHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnCapNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlHuellaLayout.setVerticalGroup(
            jpnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlHuellaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnCapNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpnlPrestamos.setBackground(new java.awt.Color(255, 255, 255));
        jpnlPrestamos.setBorder(javax.swing.BorderFactory.createTitledBorder("Prestamos"));

        jtblPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblPrestamos);

        jpblDiasRetraso.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Días de retraso");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 153));
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jpblDiasRetrasoLayout = new javax.swing.GroupLayout(jpblDiasRetraso);
        jpblDiasRetraso.setLayout(jpblDiasRetrasoLayout);
        jpblDiasRetrasoLayout.setHorizontalGroup(
            jpblDiasRetrasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpblDiasRetrasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpblDiasRetrasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jpblDiasRetrasoLayout.setVerticalGroup(
            jpblDiasRetrasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpblDiasRetrasoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jpnlMulDia.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Multa por día");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 153));
        jLabel9.setText("jLabel2");

        javax.swing.GroupLayout jpnlMulDiaLayout = new javax.swing.GroupLayout(jpnlMulDia);
        jpnlMulDia.setLayout(jpnlMulDiaLayout);
        jpnlMulDiaLayout.setHorizontalGroup(
            jpnlMulDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMulDiaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
            .addGroup(jpnlMulDiaLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpnlMulDiaLayout.setVerticalGroup(
            jpnlMulDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMulDiaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        jpnlTotalPagar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Multa por cobrar");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 51));
        jLabel11.setText("jLabel2");

        javax.swing.GroupLayout jpnlTotalPagarLayout = new javax.swing.GroupLayout(jpnlTotalPagar);
        jpnlTotalPagar.setLayout(jpnlTotalPagarLayout);
        jpnlTotalPagarLayout.setHorizontalGroup(
            jpnlTotalPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTotalPagarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlTotalPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlTotalPagarLayout.setVerticalGroup(
            jpnlTotalPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTotalPagarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel11))
        );

        jbtnCancelar.setText("Cancelar");

        jbtnConfirmar.setText("Confirmar");

        javax.swing.GroupLayout jpnlPrestamosLayout = new javax.swing.GroupLayout(jpnlPrestamos);
        jpnlPrestamos.setLayout(jpnlPrestamosLayout);
        jpnlPrestamosLayout.setHorizontalGroup(
            jpnlPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlPrestamosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpnlPrestamosLayout.createSequentialGroup()
                        .addComponent(jpblDiasRetraso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpnlMulDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpnlTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlPrestamosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnConfirmar)
                .addGap(89, 89, 89))
        );
        jpnlPrestamosLayout.setVerticalGroup(
            jpnlPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlPrestamosLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpblDiasRetraso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlMulDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jpnlPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCancelar)
                    .addComponent(jbtnConfirmar))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
            .addComponent(jpnlInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlHuella, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        java.awt.EventQueue.invokeLater(() -> new Devoluciones().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnCapNuevo;
    private javax.swing.JButton jbtnConfirmar;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblHuella;
    private javax.swing.JLabel jlblInfo;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpblDiasRetraso;
    private javax.swing.JPanel jpnlHuella;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlMulDia;
    private javax.swing.JPanel jpnlPrestamos;
    private javax.swing.JPanel jpnlTotalPagar;
    private javax.swing.JTable jtblPrestamos;
    // End of variables declaration//GEN-END:variables
}
