/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class CRUDEstudiantes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CRUDEstudiantes.class.getName());

    /**
     * Creates new form BEstudiantes
     */
    public CRUDEstudiantes() {
        initComponents();
         util.Estilos.reloj(jlblFecha, jlblHora);
        util.Estilos.icono(jbtnRegresar, "Regresar.png", 50, true);
        ajustarDiseno();
    }
    private void ajustarDiseno() {
        // ---- Distribución: datos arriba, tabla abajo, huella a la derecha ----
        jPanel4.removeAll();
        jPanel4.setLayout(new java.awt.BorderLayout(16, 0));

        javax.swing.JPanel izquierda = new javax.swing.JPanel(new java.awt.BorderLayout(0, 16));
        izquierda.setOpaque(false);
        izquierda.add(jpnlDatosEst, java.awt.BorderLayout.NORTH);
        izquierda.add(jpnlEstReg, java.awt.BorderLayout.CENTER);

        jPanel4.add(izquierda, java.awt.BorderLayout.CENTER);
        jPanel4.add(jPanel5, java.awt.BorderLayout.EAST);
        jPanel5.setPreferredSize(new java.awt.Dimension(290, jPanel5.getPreferredSize().height));

        // ---- Grupos ----
        util.Estilos.grupo(jpnlDatosEst, "Datos del estudiante");
        util.Estilos.grupo(jpnlEstReg, "Estudiantes registrados");
        util.Estilos.grupo(jPanel5, "Huella");

        // ---- Formulario en 4 columnas ----
        armarFormulario();

        // ---- Barra de búsqueda y tabla ----
        jpnlEstReg.removeAll();
        jpnlEstReg.setLayout(new java.awt.BorderLayout(0, 10));
        javax.swing.JPanel barra = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        barra.setOpaque(false);
        barra.add(jtxtBuscar, java.awt.BorderLayout.CENTER);
        javax.swing.JPanel botonesBusqueda = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        botonesBusqueda.setOpaque(false);
        botonesBusqueda.add(jbtnFiltro);
        botonesBusqueda.add(jbtnBuscar);
        barra.add(botonesBusqueda, java.awt.BorderLayout.EAST);
        jpnlEstReg.add(barra, java.awt.BorderLayout.NORTH);
        jpnlEstReg.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));

        jtxtBuscar.setFont(util.Estilos.FUENTE);
        jtxtBuscar.setPreferredSize(new java.awt.Dimension(200, 32));
        util.Estilos.boton(jbtnBuscar);
        jbtnBuscar.setPreferredSize(new java.awt.Dimension(90, 32));
        util.Estilos.boton(jbtnFiltro);
        jbtnFiltro.setText("");
        jbtnFiltro.setToolTipText("Filtrar");
        jbtnFiltro.setPreferredSize(new java.awt.Dimension(36, 32));
        util.Estilos.icono(jbtnFiltro, "filtro.png", 18, false);

        util.Estilos.tabla(jtblEst, jScrollPane1,
                "Cédula", "Nombres", "Apellidos", "Carrera", "Semestre", "Huellas", "Estado");

        // ---- Panel de huella ----
        jlblHuella.setOpaque(true);
        jlblHuella.setBackground(new java.awt.Color(242, 249, 248));
        jlblHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblHuella.setBorder(javax.swing.BorderFactory.createDashedBorder(
                new java.awt.Color(127, 191, 186), 2, 6, 4, true));
        jlblHuella.setText("Coloque el dedo");
        jlblHuella.setForeground(new java.awt.Color(90, 110, 115));
        jlblHuella.setFont(util.Estilos.FUENTE);

        jLabel38.setFont(util.Estilos.FUENTE);
        jcbxDedo.setFont(util.Estilos.FUENTE);
        jcbxDedo.removeAllItems();   // se llena más adelante

        jpgrHuella.setMinimum(0);
        jpgrHuella.setMaximum(3);
        jpgrHuella.setValue(0);
        jpgrHuella.setStringPainted(true);
        jpgrHuella.setString("0 de 3 muestras");
        jpgrHuella.setForeground(util.Estilos.VERDE);
        jpgrHuella.setPreferredSize(new java.awt.Dimension(100, 20));

        jtxtAInfo.setEditable(false);
        jtxtAInfo.setLineWrap(true);
        jtxtAInfo.setWrapStyleWord(true);
        jtxtAInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jtxtAInfo.setBackground(new java.awt.Color(247, 249, 250));
        jtxtAInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8));
        jtxtAInfo.setText("Seleccione el dedo y presione Capturar huella. Se necesitan 3 muestras del mismo dedo.");
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(util.Estilos.BORDE));

        util.Estilos.boton(jbtnCapturar);
        jbtnCapturar.setText("Capturar huella");
        jbtnCapturar.setPreferredSize(new java.awt.Dimension(200, 36));

        // ---- Encabezado + contenido + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, jPanel4, jPanel6);
    }

    /** Formulario: 4 columnas con label arriba y campo abajo, botones al final. */
    private void armarFormulario() {
        jpnlDatosEst.removeAll();
        jpnlDatosEst.setLayout(new java.awt.GridBagLayout());

        // Fila 1: Cédula | Nombres | Apellidos | Teléfono
        celda(jLabel33, "Cédula", jtxtCedula, 0, 0, 1);
        celda(jLabel34, "Nombres", jtxtNombre, 1, 0, 1);
        celda(jLabel35, "Apellidos", jtxtApellidos, 2, 0, 1);
        celda(jLabel37, "Teléfono", jtxtTelefono, 3, 0, 1);

        // Fila 2: Correo | Carrera (2 columnas) | Semestre
        celda(jLabel36, "Correo electrónico", jtxtCorreo, 0, 2, 1);
        celda(jLabel39, "Carrera", jcbxCarrera, 1, 2, 2);
        celda(jLabel40, "Semestre", jcbxSemestre, 3, 2, 1);

        // Se llenan más adelante
        jcbxCarrera.removeAllItems();
        jcbxSemestre.removeAllItems();

        // Fila 3: botones
        javax.swing.JPanel botones = new javax.swing.JPanel(new java.awt.GridLayout(1, 5, 10, 0));
        botones.setOpaque(false);
        for (javax.swing.JButton b : new javax.swing.JButton[]{jbtnnuevo, jbtnguardar, jbtneditar, jbtneliminar, jbtncancelar}) {
            util.Estilos.boton(b);
            b.setPreferredSize(new java.awt.Dimension(110, 36));
            botones.add(b);
        }
        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 4;
        g.anchor = java.awt.GridBagConstraints.WEST;
        g.insets = new java.awt.Insets(10, 6, 4, 6);
        jpnlDatosEst.add(botones, g);
    }

    private void celda(javax.swing.JLabel etiqueta, String texto, javax.swing.JComponent campo,
                       int col, int fila, int ancho) {
        etiqueta.setText(texto);
        etiqueta.setFont(util.Estilos.FUENTE);
        campo.setFont(util.Estilos.FUENTE);
        campo.setPreferredSize(new java.awt.Dimension(120, 32));

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = col;
        g.gridwidth = ancho;
        g.weightx = 1;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.anchor = java.awt.GridBagConstraints.WEST;

        g.gridy = fila;
        g.insets = new java.awt.Insets(4, 6, 4, 6);
        jpnlDatosEst.add(etiqueta, g);

        g.gridy = fila + 1;
        g.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlDatosEst.add(campo, g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
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
        jPanel4 = new javax.swing.JPanel();
        jpnlDatosEst = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jcbxCarrera = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jcbxSemestre = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jtxtTelefono = new javax.swing.JTextField();
        jbtnnuevo = new javax.swing.JButton();
        jbtnguardar = new javax.swing.JButton();
        jbtneditar = new javax.swing.JButton();
        jbtneliminar = new javax.swing.JButton();
        jbtncancelar = new javax.swing.JButton();
        jtxtApellidos = new javax.swing.JTextField();
        jtxtCorreo = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jpnlEstReg = new javax.swing.JPanel();
        jtxtBuscar = new javax.swing.JTextField();
        jbtnFiltro = new javax.swing.JButton();
        jbtnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEst = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jlblHuella = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jcbxDedo = new javax.swing.JComboBox<>();
        jpgrHuella = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxtAInfo = new javax.swing.JTextArea();
        jbtnCapturar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

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
        jLabel13.setText("GESTION DE ESTUDIANTES");

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
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel12))
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpnlInicioLayout.createSequentialGroup()
                                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblNombre, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(6, 6, 6))
                            .addGroup(jpnlInicioLayout.createSequentialGroup()
                                .addComponent(jlblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(jpnlInicioLayout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInicioLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(207, 207, 207)))
                .addContainerGap())
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jpnlDatosEst.setBackground(new java.awt.Color(255, 255, 255));
        jpnlDatosEst.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Del Libro"));

        jLabel33.setText("Cédula");

        jLabel34.setText("Nombres");

        jLabel35.setText("Apellidos");

        jLabel36.setText("Correo Electrónico");

        jcbxSemestre.addActionListener(this::jcbxSemestreActionPerformed);

        jLabel37.setText("Teléfono");

        jbtnnuevo.setText("Nuevo");

        jbtnguardar.setText("Guardar");

        jbtneditar.setText("Editar");

        jbtneliminar.setText("Eliminar");

        jbtncancelar.setText("Cancelar");

        jtxtApellidos.addActionListener(this::jtxtApellidosActionPerformed);

        jLabel39.setText("Carrera");

        jLabel40.setText("Semestre");

        javax.swing.GroupLayout jpnlDatosEstLayout = new javax.swing.GroupLayout(jpnlDatosEst);
        jpnlDatosEst.setLayout(jpnlDatosEstLayout);
        jpnlDatosEstLayout.setHorizontalGroup(
            jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                        .addComponent(jbtnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                        .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtxtCedula)
                                        .addComponent(jtxtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                    .addComponent(jLabel33))
                                .addGap(28, 28, 28)
                                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                                        .addComponent(jcbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcbxSemestre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(191, 191, 191)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                            .addComponent(jtxtTelefono))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlDatosEstLayout.setVerticalGroup(
            jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosEstLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbxSemestre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnnuevo)
                    .addComponent(jbtnguardar)
                    .addComponent(jbtneditar)
                    .addComponent(jbtneliminar)
                    .addComponent(jbtncancelar))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jpnlEstReg.setBackground(new java.awt.Color(255, 255, 255));
        jpnlEstReg.setBorder(javax.swing.BorderFactory.createTitledBorder("Estudiantes Registrados"));

        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(this::jbtnBuscarActionPerformed);

        jtblEst.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblEst);

        javax.swing.GroupLayout jpnlEstRegLayout = new javax.swing.GroupLayout(jpnlEstReg);
        jpnlEstReg.setLayout(jpnlEstRegLayout);
        jpnlEstRegLayout.setHorizontalGroup(
            jpnlEstRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlEstRegLayout.createSequentialGroup()
                .addGroup(jpnlEstRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlEstRegLayout.createSequentialGroup()
                        .addGroup(jpnlEstRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlEstRegLayout.createSequentialGroup()
                                .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlEstRegLayout.createSequentialGroup()
                                .addComponent(jbtnBuscar)
                                .addGap(27, 27, 27)))
                        .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlEstRegLayout.setVerticalGroup(
            jpnlEstRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlEstRegLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnlEstRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlEstRegLayout.createSequentialGroup()
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Huella"));

        jLabel38.setText("Dedo");

        jtxtAInfo.setColumns(20);
        jtxtAInfo.setRows(5);
        jScrollPane2.setViewportView(jtxtAInfo);

        jbtnCapturar.setText("Capturar Huella");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbxDedo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpgrHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jbtnCapturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbxDedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpgrHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnCapturar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jpnlEstReg, javax.swing.GroupLayout.PREFERRED_SIZE, 906, Short.MAX_VALUE)
                    .addComponent(jpnlDatosEst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jpnlDatosEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jpnlEstReg, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegresarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jbtnRegresarActionPerformed

    private void jcbxSemestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxSemestreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxSemestreActionPerformed

    private void jtxtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidosActionPerformed

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnBuscarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CRUDEstudiantes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCapturar;
    private javax.swing.JButton jbtnFiltro;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JButton jbtncancelar;
    private javax.swing.JButton jbtneditar;
    private javax.swing.JButton jbtneliminar;
    private javax.swing.JButton jbtnguardar;
    private javax.swing.JButton jbtnnuevo;
    private javax.swing.JComboBox<String> jcbxCarrera;
    private javax.swing.JComboBox<String> jcbxDedo;
    private javax.swing.JComboBox<String> jcbxSemestre;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblHuella;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JProgressBar jpgrHuella;
    private javax.swing.JPanel jpnlDatosEst;
    private javax.swing.JPanel jpnlEstReg;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JTable jtblEst;
    private javax.swing.JTextArea jtxtAInfo;
    private javax.swing.JTextField jtxtApellidos;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtCorreo;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
