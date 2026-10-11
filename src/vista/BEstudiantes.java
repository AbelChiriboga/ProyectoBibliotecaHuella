/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class BEstudiantes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BEstudiantes.class.getName());

    /**
     * Creates new form BEstudiantes
     */
    public BEstudiantes() {
        initComponents();
        util.Estilos.reloj(jlblFecha, jlblHora);
        util.Estilos.icono(jbtnRegresar, "Regresar.png", 50, true);
        ajustarDiseno();
    }
    private void ajustarDiseno() {
        jLabel13.setText("CONSULTA DE ESTUDIANTES");

        // ---- Contenedor: lista a la izquierda, estudiante seleccionado a la derecha ----
        javax.swing.JPanel contenedor = new javax.swing.JPanel(new java.awt.BorderLayout(16, 0));
        getContentPane().remove(jpnlEstudiantes);
        getContentPane().remove(jPanel1);
        contenedor.add(jpnlEstudiantes, java.awt.BorderLayout.CENTER);
        contenedor.add(jPanel1, java.awt.BorderLayout.EAST);

        util.Estilos.grupo(jpnlEstudiantes, "Estudiantes");
        util.Estilos.grupo(jPanel1, "Estudiante seleccionado");
        jPanel1.setPreferredSize(new java.awt.Dimension(320, 0));

        armarLista();
        armarSeleccionado();

        // ---- Encabezado + contenedor + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, contenedor, jPanel6);
    }

    /** Izquierda: búsqueda, filtro por estado y tabla. */
    private void armarLista() {
        jpnlEstudiantes.removeAll();
        jpnlEstudiantes.setLayout(new java.awt.BorderLayout(0, 12));

        // Barra: buscador estirable, combo de estado y botón
        javax.swing.JPanel barra = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        barra.setOpaque(false);

        jtxtBuscar.setFont(util.Estilos.FUENTE);
        jtxtBuscar.setPreferredSize(new java.awt.Dimension(200, 32));
        jtxtBuscar.setToolTipText("Buscar por cédula o nombre");
        barra.add(jtxtBuscar, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel derecha = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        derecha.setOpaque(false);

        jcbxEstado.setFont(util.Estilos.FUENTE);
        jcbxEstado.setPreferredSize(new java.awt.Dimension(140, 32));
        jcbxEstado.removeAllItems();
        jcbxEstado.addItem("Todos");        // valores fijos, no vienen de la BD
        jcbxEstado.addItem("Activos");
        jcbxEstado.addItem("Inactivos");

        util.Estilos.boton(jbtnBuscar);
        jbtnBuscar.setPreferredSize(new java.awt.Dimension(90, 32));

        derecha.add(jcbxEstado);
        derecha.add(jbtnBuscar);
        barra.add(derecha, java.awt.BorderLayout.EAST);

        // Tabla
        util.Estilos.tabla(jtblEstudiantes, jScrollPane1,
                "Cédula", "Estudiante", "Carrera", "Semestre", "Préstamos activos", "Estado");
        jtblEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(170);
        jtblEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(170);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));

        // Nota abajo
        jlblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jlblInfo.setForeground(new java.awt.Color(80, 95, 105));
        jlblInfo.setText("El registro y la edición de estudiantes los realiza el administrador.");

        jpnlEstudiantes.add(barra, java.awt.BorderLayout.NORTH);
        jpnlEstudiantes.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jpnlEstudiantes.add(jlblInfo, java.awt.BorderLayout.SOUTH);
    }

    /** Derecha: datos del estudiante, indicadores, estado y botones. */
    private void armarSeleccionado() {
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.GridBagLayout());

        // Datos
        jlblNombres.setText("Seleccione un estudiante");
        jlblNombres.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        jlblNombres.setForeground(util.Estilos.TEXTO);

        jlblInfoEst.setText("C.I: —   ·   Carrera: —   ·   Semestre: —");
        jlblInfoEst.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jlblInfoEst.setForeground(new java.awt.Color(80, 95, 105));

        jlblCorreo.setText("Correo: —");
        jlblCorreo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jlblCorreo.setForeground(new java.awt.Color(80, 95, 105));

        // Dos indicadores lado a lado
        javax.swing.JPanel indicadores = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        indicadores.setOpaque(false);
        tarjeta(jpnlPrestamosA, jLabel10, "Préstamos activos", jLabel11, "— / 3",
                java.awt.Color.WHITE, util.Estilos.BORDE, util.Estilos.TEXTO);
        tarjeta(jpnlAtrasos, jLabel15, "Atrasados", jLabel16, "—",
                new java.awt.Color(253, 241, 232), new java.awt.Color(224, 179, 147), new java.awt.Color(138, 58, 18));
        indicadores.add(jpnlPrestamosA);
        indicadores.add(jpnlAtrasos);

        // Estado (etiqueta grande)
        jlblEstado.setOpaque(true);
        jlblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblEstado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        jlblEstado.setBackground(new java.awt.Color(225, 230, 233));
        jlblEstado.setForeground(new java.awt.Color(60, 75, 85));
        jlblEstado.setText("SIN SELECCIONAR");
        jlblEstado.setPreferredSize(new java.awt.Dimension(100, 40));

        // Botones
        util.Estilos.boton(jbtnDesactivar);
        util.Estilos.boton(jbtnActivar);
        jbtnDesactivar.setText("Desactivar estudiante");
        jbtnActivar.setText("Activar estudiante");
        jbtnDesactivar.setForeground(new java.awt.Color(140, 30, 30));   // rojo: acción de bloqueo
        jbtnDesactivar.setPreferredSize(new java.awt.Dimension(200, 38));
        jbtnActivar.setPreferredSize(new java.awt.Dimension(200, 38));
        jbtnDesactivar.setEnabled(false);   // se habilitan al seleccionar un estudiante
        jbtnActivar.setEnabled(false);

        // Acomodar de arriba hacia abajo
        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;

        g.gridy = 0; g.insets = new java.awt.Insets(4, 6, 2, 6);   jPanel1.add(jlblNombres, g);
        g.gridy = 1; g.insets = new java.awt.Insets(2, 6, 2, 6);   jPanel1.add(jlblInfoEst, g);
        g.gridy = 2; g.insets = new java.awt.Insets(2, 6, 14, 6);  jPanel1.add(jlblCorreo, g);
        g.gridy = 3; g.insets = new java.awt.Insets(0, 6, 14, 6);  jPanel1.add(indicadores, g);
        g.gridy = 4; g.insets = new java.awt.Insets(0, 6, 14, 6);  jPanel1.add(jlblEstado, g);
        g.gridy = 5; g.insets = new java.awt.Insets(0, 6, 8, 6);   jPanel1.add(jbtnDesactivar, g);
        g.gridy = 6; g.insets = new java.awt.Insets(0, 6, 0, 6);   jPanel1.add(jbtnActivar, g);

        // Relleno: empuja todo hacia arriba
        g.gridy = 7;
        g.weighty = 1;
        g.fill = java.awt.GridBagConstraints.BOTH;
        javax.swing.JPanel relleno = new javax.swing.JPanel();
        relleno.setOpaque(false);
        jPanel1.add(relleno, g);
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
                javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        titulo.setText(textoTitulo);
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        titulo.setForeground(new java.awt.Color(80, 90, 100));

        valor.setText(textoValor);
        valor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
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
        jpnlEstudiantes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();
        jtxtBuscar = new javax.swing.JTextField();
        jbtnBuscar = new javax.swing.JButton();
        jlblInfo = new javax.swing.JLabel();
        jcbxEstado = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jlblNombres = new javax.swing.JLabel();
        jlblInfoEst = new javax.swing.JLabel();
        jlblCorreo = new javax.swing.JLabel();
        jpnlPrestamosA = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jpnlAtrasos = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jlblEstado = new javax.swing.JLabel();
        jbtnActivar = new javax.swing.JButton();
        jbtnDesactivar = new javax.swing.JButton();

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
        jLabel13.setText("REGISTRO DE LIBROS");

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

        jpnlEstudiantes.setBackground(new java.awt.Color(255, 255, 255));
        jpnlEstudiantes.setBorder(javax.swing.BorderFactory.createTitledBorder("Estudiantes"));

        jtblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblEstudiantes);

        jbtnBuscar.setText("Buscar");

        javax.swing.GroupLayout jpnlEstudiantesLayout = new javax.swing.GroupLayout(jpnlEstudiantes);
        jpnlEstudiantes.setLayout(jpnlEstudiantesLayout);
        jpnlEstudiantesLayout.setHorizontalGroup(
            jpnlEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlEstudiantesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlEstudiantesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlEstudiantesLayout.createSequentialGroup()
                        .addGroup(jpnlEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnlEstudiantesLayout.createSequentialGroup()
                                .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbtnBuscar)))
                        .addGap(0, 5, Short.MAX_VALUE))))
        );
        jpnlEstudiantesLayout.setVerticalGroup(
            jpnlEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlEstudiantesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBuscar))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Estudiante seleccionado"));

        jlblNombres.setText("Nombre_Apellido_E");

        jlblInfoEst.setText("C.I: 000000000 Carrera Semestre");

        jlblCorreo.setText("correo");

        jLabel10.setText("Prestamos Activos");

        jLabel11.setText("N/M");

        javax.swing.GroupLayout jpnlPrestamosALayout = new javax.swing.GroupLayout(jpnlPrestamosA);
        jpnlPrestamosA.setLayout(jpnlPrestamosALayout);
        jpnlPrestamosALayout.setHorizontalGroup(
            jpnlPrestamosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlPrestamosALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlPrestamosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jpnlPrestamosALayout.setVerticalGroup(
            jpnlPrestamosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlPrestamosALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(29, 29, 29))
        );

        jLabel15.setText("Atrasados");

        jLabel16.setText("A");

        javax.swing.GroupLayout jpnlAtrasosLayout = new javax.swing.GroupLayout(jpnlAtrasos);
        jpnlAtrasos.setLayout(jpnlAtrasosLayout);
        jpnlAtrasosLayout.setHorizontalGroup(
            jpnlAtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlAtrasosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlAtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jpnlAtrasosLayout.setVerticalGroup(
            jpnlAtrasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlAtrasosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jlblEstado.setText("ESTADO");

        jbtnActivar.setText("Activar");

        jbtnDesactivar.setText("Desactivar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpnlPrestamosA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jpnlAtrasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnActivar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblNombres)
                            .addComponent(jlblInfoEst, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jbtnDesactivar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblNombres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblInfoEst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnlPrestamosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlAtrasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
            .addComponent(jpnlInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        java.awt.EventQueue.invokeLater(() -> new BEstudiantes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnActivar;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnDesactivar;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JComboBox<String> jcbxEstado;
    private javax.swing.JLabel jlblCorreo;
    private javax.swing.JLabel jlblEstado;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblInfo;
    private javax.swing.JLabel jlblInfoEst;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JLabel jlblNombres;
    private javax.swing.JPanel jpnlAtrasos;
    private javax.swing.JPanel jpnlEstudiantes;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlPrestamosA;
    private javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField jtxtBuscar;
    // End of variables declaration//GEN-END:variables
}
