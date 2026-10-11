/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class CRUDBibliotecarios extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CRUDBibliotecarios.class.getName());

    /**
     * Creates new form CRUDBibliotecarios
     */
    public CRUDBibliotecarios() {
        initComponents();
        util.Estilos.reloj(jlblFecha, jlblHora);
        util.Estilos.icono(jbtnRegresar, "Regresar.png", 50, true);
        ajustarDiseno();
    }
    private void ajustarDiseno() {
        jLabel13.setText("GESTIÓN DE USUARIOS");

        // ---- Contenido: formulario a la izquierda, tabla a la derecha ----
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout(16, 0));
        jPanel1.add(jpnlDatosUsario, java.awt.BorderLayout.WEST);
        jPanel1.add(jpnlUsuarioReg, java.awt.BorderLayout.CENTER);

        util.Estilos.grupo(jpnlDatosUsario, "Datos del usuario");
        util.Estilos.grupo(jpnlUsuarioReg, "Usuarios registrados");
        jpnlDatosUsario.setPreferredSize(new java.awt.Dimension(450, 0));

        armarFormulario();
        armarTabla();

        // ---- Encabezado + contenido + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, jPanel1, jPanel6);
    }

    /** Formulario en dos columnas: datos personales, datos de la cuenta y botones. */
    private void armarFormulario() {
        jpnlDatosUsario.removeAll();
        jpnlDatosUsario.setLayout(new java.awt.GridBagLayout());

        // ---- Datos personales ----
        celda(jLabel1, "Cédula", jtxtCedula, 0, 0);
        celda(jLabel2, "Nombres", jtxtNombres, 1, 0);
        celda(jLabel11, "Apellidos", jtxtApellidos, 0, 2);
        celda(jLabel15, "Correo electrónico", jtxtCorreo, 1, 2);
        celda(jLabel16, "Dirección", jtxtDireccion, 0, 4);
        celda(jLabel17, "Teléfono", jtxtTelefono, 1, 4);

        // ---- Separador "Datos de la cuenta" ----
        jLabel7.setText("Datos de la cuenta");
        jLabel7.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jLabel7.setForeground(util.Estilos.VERDE_TITULO);
        jLabel7.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(210, 225, 228)),
                javax.swing.BorderFactory.createEmptyBorder(0, 0, 4, 0)));
        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 2;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.insets = new java.awt.Insets(6, 6, 8, 6);
        jpnlDatosUsario.add(jLabel7, g);

        // ---- Datos de la cuenta ----
        celda(jLabel9, "Usuario", jtxtUsuario, 0, 7);
        celda(jLabel19, "Rol", jcbxRol, 1, 7);
        celda(jLabel10, "Contraseña", jpswClave, 0, 9);
        celda(jLabel18, "Repetir contraseña", jpswRepClave, 1, 9);

        jpswClave.setText("");
        jpswRepClave.setText("");

        jcbxRol.removeAllItems();
        jcbxRol.addItem("Bibliotecario");   // valores fijos (ENUM de la BD)
        jcbxRol.addItem("Administrador");

        // ---- Barra de seguridad de la contraseña ----
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setValue(0);
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setString("Seguridad de la contraseña");
        jProgressBar1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        jProgressBar1.setForeground(util.Estilos.VERDE);
        jProgressBar1.setPreferredSize(new java.awt.Dimension(100, 18));
        g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.gridy = 11;
        g.gridwidth = 2;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.insets = new java.awt.Insets(0, 6, 12, 6);
        jpnlDatosUsario.add(jProgressBar1, g);

        // ---- Botones en una fila ----
        javax.swing.JPanel botones = new javax.swing.JPanel(new java.awt.GridLayout(1, 5, 6, 0));
        botones.setOpaque(false);
        for (javax.swing.JButton b : new javax.swing.JButton[]{jbtnNuevo, jbtnGuardar, jbtnEditar, jbtnEliminar, jbtnCancelar}) {
            util.Estilos.boton(b);
            b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            b.setPreferredSize(new java.awt.Dimension(80, 36));
            botones.add(b);
        }
        g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.gridy = 12;
        g.gridwidth = 2;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.insets = new java.awt.Insets(0, 6, 0, 6);
        jpnlDatosUsario.add(botones, g);

        // Relleno: empuja todo hacia arriba
        g = new java.awt.GridBagConstraints();
        g.gridx = 0;
        g.gridy = 13;
        g.gridwidth = 2;
        g.weighty = 1;
        g.fill = java.awt.GridBagConstraints.BOTH;
        javax.swing.JPanel relleno = new javax.swing.JPanel();
        relleno.setOpaque(false);
        jpnlDatosUsario.add(relleno, g);
    }

    /** Pone un label arriba y su campo abajo, en la columna (0 o 1) y fila indicadas. */
    private void celda(javax.swing.JLabel etiqueta, String texto, javax.swing.JComponent campo, int col, int fila) {
        etiqueta.setText(texto);
        etiqueta.setFont(util.Estilos.FUENTE);
        campo.setFont(util.Estilos.FUENTE);
        campo.setPreferredSize(new java.awt.Dimension(150, 32));

        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.gridx = col;
        g.weightx = 1;
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.anchor = java.awt.GridBagConstraints.WEST;

        g.gridy = fila;
        g.insets = new java.awt.Insets(2, 6, 3, 6);
        jpnlDatosUsario.add(etiqueta, g);

        g.gridy = fila + 1;
        g.insets = new java.awt.Insets(0, 6, 8, 6);
        jpnlDatosUsario.add(campo, g);
    }

    /** Derecha: búsqueda y tabla de usuarios. */
    private void armarTabla() {
        jpnlUsuarioReg.removeAll();
        jpnlUsuarioReg.setLayout(new java.awt.BorderLayout(0, 12));

        javax.swing.JPanel barra = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        barra.setOpaque(false);

        jtxtBuscar.setFont(util.Estilos.FUENTE);
        jtxtBuscar.setPreferredSize(new java.awt.Dimension(200, 32));
        jtxtBuscar.setToolTipText("Buscar por cédula, nombre o usuario");
        barra.add(jtxtBuscar, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel botonesBusqueda = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        botonesBusqueda.setOpaque(false);
        util.Estilos.boton(jbtnFiltro);
        jbtnFiltro.setText("");
        jbtnFiltro.setToolTipText("Filtrar");
        jbtnFiltro.setPreferredSize(new java.awt.Dimension(36, 32));
        util.Estilos.icono(jbtnFiltro, "filtro.png", 18, false);
        util.Estilos.boton(jbtnBuscar);
        jbtnBuscar.setPreferredSize(new java.awt.Dimension(90, 32));
        botonesBusqueda.add(jbtnFiltro);
        botonesBusqueda.add(jbtnBuscar);
        barra.add(botonesBusqueda, java.awt.BorderLayout.EAST);

        util.Estilos.tabla(jtblUsuarios, jScrollPane1,
                "Cédula", "Nombres", "Apellidos", "Usuario", "Rol", "Estado");
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));

        jpnlUsuarioReg.add(barra, java.awt.BorderLayout.NORTH);
        jpnlUsuarioReg.add(jScrollPane1, java.awt.BorderLayout.CENTER);
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
        jPanel1 = new javax.swing.JPanel();
        jpnlDatosUsario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombres = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtApellidos = new javax.swing.JTextField();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jtxtCorreo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtxtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtxtUsuario = new javax.swing.JTextField();
        jpswClave = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        jpswRepClave = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jcbxRol = new javax.swing.JComboBox<>();
        jProgressBar1 = new javax.swing.JProgressBar();
        jpnlUsuarioReg = new javax.swing.JPanel();
        jtxtBuscar = new javax.swing.JTextField();
        jbtnFiltro = new javax.swing.JButton();
        jbtnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblUsuarios = new javax.swing.JTable();

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
        jLabel13.setText("GESTIÓN DE USUARIOS");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jpnlDatosUsario.setBackground(new java.awt.Color(255, 255, 255));
        jpnlDatosUsario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Usuario"));

        jLabel1.setText("Cédula");

        jLabel2.setText("Nombres");

        jLabel11.setText("Apellidos");

        jbtnNuevo.setText("Nuevo");

        jbtnGuardar.setText("Guardar");

        jbtnEditar.setText("Editar");

        jbtnEliminar.setText("Eliminar");

        jbtnCancelar.setText("Cancelar");

        jLabel15.setText("Correo Electrónico");

        jLabel16.setText("Dirección");

        jLabel17.setText("Teléfono");

        jLabel7.setText("DATOS DE LA CUENTA");

        jLabel9.setText("Usuario");

        jpswClave.setText("jPasswordField1");

        jLabel10.setText("Contraseña");

        jpswRepClave.setText("jPasswordField2");

        jLabel18.setText("Repetir Contraseña");

        jLabel19.setText("Rol");

        javax.swing.GroupLayout jpnlDatosUsarioLayout = new javax.swing.GroupLayout(jpnlDatosUsario);
        jpnlDatosUsario.setLayout(jpnlDatosUsarioLayout);
        jpnlDatosUsarioLayout.setHorizontalGroup(
            jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                            .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jcbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addComponent(jtxtNombres)
                                .addContainerGap())))
                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                        .addComponent(jpswClave, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addComponent(jpswRepClave)
                                .addContainerGap())))
                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtUsuario)
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jtxtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtCorreo)
                                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel15))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtTelefono))
                            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                        .addGap(136, 136, 136)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jpnlDatosUsarioLayout.setVerticalGroup(
            jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosUsarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jcbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpswClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpswRepClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnNuevo)
                    .addComponent(jbtnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosUsarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnEditar)
                    .addComponent(jbtnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnCancelar)
                .addContainerGap())
        );

        jpnlUsuarioReg.setBackground(new java.awt.Color(255, 255, 255));
        jpnlUsuarioReg.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuarios Registrados"));

        jbtnFiltro.addActionListener(this::jbtnFiltroActionPerformed);

        jbtnBuscar.setText("Buscar");

        jtblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblUsuarios);

        javax.swing.GroupLayout jpnlUsuarioRegLayout = new javax.swing.GroupLayout(jpnlUsuarioReg);
        jpnlUsuarioReg.setLayout(jpnlUsuarioRegLayout);
        jpnlUsuarioRegLayout.setHorizontalGroup(
            jpnlUsuarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlUsuarioRegLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlUsuarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                    .addGroup(jpnlUsuarioRegLayout.createSequentialGroup()
                        .addComponent(jtxtBuscar)
                        .addGap(12, 12, 12)
                        .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpnlUsuarioRegLayout.setVerticalGroup(
            jpnlUsuarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlUsuarioRegLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlUsuarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jpnlDatosUsario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlUsuarioReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlUsuarioReg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlDatosUsario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
            .addComponent(jpnlInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegresarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jbtnRegresarActionPerformed

    private void jbtnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnFiltroActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CRUDBibliotecarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnFiltro;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JComboBox<String> jcbxRol;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpnlDatosUsario;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlUsuarioReg;
    private javax.swing.JPasswordField jpswClave;
    private javax.swing.JPasswordField jpswRepClave;
    private javax.swing.JTable jtblUsuarios;
    private javax.swing.JTextField jtxtApellidos;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtCorreo;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombres;
    private javax.swing.JTextField jtxtTelefono;
    private javax.swing.JTextField jtxtUsuario;
    // End of variables declaration//GEN-END:variables
}
