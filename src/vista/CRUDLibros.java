/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class CRUDLibros extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CRUDLibros.class.getName());

    /**
     * Creates new form BLibros
     */
    public CRUDLibros() {
        initComponents();
        IniciarHora();
        ponerIcono(jbtnRegresar, "Regresar.png", 50);
        ajustarDiseno();
    }
    private void ajustarDiseno() {
    java.awt.Color verde = new java.awt.Color(0, 153, 153);
    java.awt.Color borde = new java.awt.Color(185, 198, 204);
    java.awt.Font fuente = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);

    // ---- Quitar las franjas grises: encabezado, contenido y pie pegados ----
    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(jpnlInicio, java.awt.BorderLayout.NORTH);
    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    getContentPane().add(jPanel6, java.awt.BorderLayout.SOUTH);
    jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));

    // ---- Grupos con el mismo borde verde del menú ----
    estiloGrupo(jpnlDatosLibro, "Datos del libro", verde);
    estiloGrupo(jpnlLibroReg, "Libros registrados", verde);

    // ---- Labels y campos ----
    for (javax.swing.JLabel l : new javax.swing.JLabel[]{jLabel1, jLabel2, jLabel7, jLabel9, jLabel10, jLabel11}) {
        l.setFont(fuente);
    }
    for (javax.swing.JComponent c : new javax.swing.JComponent[]{jtxtISBN, jtxtTitulo, jtxtEditorial, jtxtAnio, jtxtBuscar, jcbxAutor, jcbxCategoria}) {
        c.setFont(fuente);
        c.setPreferredSize(new java.awt.Dimension(c.getPreferredSize().width, 32));
    }

    // ---- Botones del formulario: todos iguales ----
    javax.swing.JButton[] acciones = {jbtnNuevo, jbtnGuardar, jbtnEditar, jbtnEliminar, jbtnCancelar, jbtnEjemplares};
    for (javax.swing.JButton b : acciones) {
        estiloBoton(b, verde, borde);
        b.setPreferredSize(new java.awt.Dimension(120, 36));
    }
    estiloBoton(jbtnBuscar, verde, borde);

    // ---- Botones + junto a los combos ----
    for (javax.swing.JButton b : new javax.swing.JButton[]{jbtnAutor, jbtnCategoria}) {
        estiloBoton(b, verde, borde);
        b.setPreferredSize(new java.awt.Dimension(36, 32));
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
    }
    jbtnAutor.setToolTipText("Agregar autor");
    jbtnCategoria.setToolTipText("Agregar categoría");

    // ---- Botón de filtro con imagen ----
    estiloBoton(jbtnFiltro, verde, borde);
    jbtnFiltro.setText("");
    jbtnFiltro.setPreferredSize(new java.awt.Dimension(36, 32));
    jbtnFiltro.setToolTipText("Filtrar");
    java.net.URL ruta = getClass().getResource("/imagenes/filtro.png");
    if (ruta != null) {
        java.awt.Image img = new javax.swing.ImageIcon(ruta).getImage()
                .getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH);
        jbtnFiltro.setIcon(new javax.swing.ImageIcon(img));
    } else {
        System.err.println("No se encontró la imagen: /imagenes/filtro.png");
    }

    // ---- Tabla ----
    jtblLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ISBN", "Título", "Autor", "Categoría", "Año", "Disponibles"}) {
        @Override
        public boolean isCellEditable(int fila, int col) {
            return false;   // la tabla es solo para ver y seleccionar
        }
    });
    jtblLibros.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    jtblLibros.setRowHeight(28);
    jtblLibros.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jtblLibros.setSelectionBackground(new java.awt.Color(212, 238, 235));
    jtblLibros.setSelectionForeground(java.awt.Color.BLACK);
    jtblLibros.setGridColor(new java.awt.Color(227, 232, 235));
    jtblLibros.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    jtblLibros.getTableHeader().setReorderingAllowed(false);
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(borde));

    // ---- Mismo tamaño que el login y el menú ----
    getContentPane().setPreferredSize(new java.awt.Dimension(1130, 700));
    pack();
    setLocationRelativeTo(null);
}

private void estiloGrupo(javax.swing.JPanel panel, String titulo, java.awt.Color verde) {
    panel.setBackground(java.awt.Color.WHITE);
    panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createTitledBorder(
                    javax.swing.BorderFactory.createLineBorder(verde, 2, true),
                    titulo,
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15),
                    new java.awt.Color(0, 122, 122)),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 8, 8)));
}

private void estiloBoton(javax.swing.JButton b, java.awt.Color verde, java.awt.Color borde) {
    java.awt.Color texto = new java.awt.Color(31, 43, 51);
    java.awt.Color hover = new java.awt.Color(230, 245, 243);

    b.setFocusPainted(false);
    b.setContentAreaFilled(false);
    b.setOpaque(true);
    b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    b.setBackground(java.awt.Color.WHITE);
    b.setForeground(texto);
    b.setBorder(javax.swing.BorderFactory.createLineBorder(borde, 1, true));

    b.getModel().addChangeListener(e -> {
        javax.swing.ButtonModel m = b.getModel();
        if (m.isPressed()) {
            b.setBackground(verde);
            b.setForeground(java.awt.Color.WHITE);
            b.setBorder(javax.swing.BorderFactory.createLineBorder(verde, 1, true));
        } else if (m.isRollover()) {
            b.setBackground(hover);
            b.setForeground(texto);
            b.setBorder(javax.swing.BorderFactory.createLineBorder(verde, 1, true));
        } else {
            b.setBackground(java.awt.Color.WHITE);
            b.setForeground(texto);
            b.setBorder(javax.swing.BorderFactory.createLineBorder(borde, 1, true));
        }
    });
}

    
    private void ponerIcono(javax.swing.JButton boton, String archivo, int tamano) {
        java.net.URL ruta = getClass().getResource("/imagenes/" + archivo);
        if (ruta == null) {
            System.err.println("No se encontró la imagen: /imagenes/" + archivo);
            return;
        }
        javax.swing.ImageIcon original = new javax.swing.ImageIcon(ruta);
        java.awt.Image escalada = original.getImage().getScaledInstance(tamano, tamano, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new javax.swing.ImageIcon(escalada));
        boton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }
    public void IniciarHora(){
        javax.swing.Timer reloj = new javax.swing.Timer(1000, e -> {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        jlblFecha.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        jlblHora.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
            });
            reloj.setInitialDelay(0);
            reloj.start();
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
        jpnlDatosLibro = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtISBN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtTitulo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcbxAutor = new javax.swing.JComboBox<>();
        jbtnAutor = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jcbxCategoria = new javax.swing.JComboBox<>();
        jbtnCategoria = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jtxtEditorial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtAnio = new javax.swing.JTextField();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jbtnEjemplares = new javax.swing.JButton();
        jpnlLibroReg = new javax.swing.JPanel();
        jtxtBuscar = new javax.swing.JTextField();
        jbtnFiltro = new javax.swing.JButton();
        jbtnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblLibros = new javax.swing.JTable();

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jpnlDatosLibro.setBackground(new java.awt.Color(255, 255, 255));
        jpnlDatosLibro.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Del Libro"));

        jLabel1.setText("ISBN");

        jLabel2.setText("Título");

        jLabel7.setText("Autor");

        jbtnAutor.setText("+");
        jbtnAutor.addActionListener(this::jbtnAutorActionPerformed);

        jLabel9.setText("Categoría");

        jbtnCategoria.setText("+");
        jbtnCategoria.addActionListener(this::jbtnCategoriaActionPerformed);

        jLabel10.setText("Editorial");

        jLabel11.setText("Año");

        jbtnNuevo.setText("Nuevo");

        jbtnGuardar.setText("Guardar");

        jbtnEditar.setText("Editar");

        jbtnEliminar.setText("Eliminar");

        jbtnCancelar.setText("Cancelar");

        jbtnEjemplares.setText("Ejemplares");

        javax.swing.GroupLayout jpnlDatosLibroLayout = new javax.swing.GroupLayout(jpnlDatosLibro);
        jpnlDatosLibro.setLayout(jpnlDatosLibroLayout);
        jpnlDatosLibroLayout.setHorizontalGroup(
            jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtTitulo)
                    .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                        .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbxCategoria, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbxAutor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnAutor)
                            .addComponent(jbtnCategoria, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlDatosLibroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnEjemplares, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46))
                    .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                        .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                                .addComponent(jtxtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jtxtISBN))
                .addContainerGap())
        );
        jpnlDatosLibroLayout.setVerticalGroup(
            jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosLibroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnCategoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnNuevo)
                    .addComponent(jbtnGuardar))
                .addGap(18, 18, 18)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnEliminar)
                    .addComponent(jbtnEditar))
                .addGap(18, 18, 18)
                .addGroup(jpnlDatosLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnEjemplares)
                    .addComponent(jbtnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlLibroReg.setBackground(new java.awt.Color(255, 255, 255));
        jpnlLibroReg.setBorder(javax.swing.BorderFactory.createTitledBorder("Libros Registrados"));

        jbtnBuscar.setText("Buscar");

        jtblLibros.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblLibros);

        javax.swing.GroupLayout jpnlLibroRegLayout = new javax.swing.GroupLayout(jpnlLibroReg);
        jpnlLibroReg.setLayout(jpnlLibroRegLayout);
        jpnlLibroRegLayout.setHorizontalGroup(
            jpnlLibroRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlLibroRegLayout.createSequentialGroup()
                .addGroup(jpnlLibroRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlLibroRegLayout.createSequentialGroup()
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jpnlLibroRegLayout.setVerticalGroup(
            jpnlLibroRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlLibroRegLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlLibroRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlLibroRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jpnlDatosLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlLibroReg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlDatosLibro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlLibroReg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
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

    private void jbtnAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAutorActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new CRUDAutores());
    }//GEN-LAST:event_jbtnAutorActionPerformed

    private void jbtnCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCategoriaActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new CRUDCategoria());
    }//GEN-LAST:event_jbtnCategoriaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CRUDLibros().setVisible(true));
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnAutor;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnCategoria;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEjemplares;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnFiltro;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnRegresar;
    private javax.swing.JComboBox<String> jcbxAutor;
    private javax.swing.JComboBox<String> jcbxCategoria;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpnlDatosLibro;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlLibroReg;
    private javax.swing.JTable jtblLibros;
    private javax.swing.JTextField jtxtAnio;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JTextField jtxtEditorial;
    private javax.swing.JTextField jtxtISBN;
    private javax.swing.JTextField jtxtTitulo;
    // End of variables declaration//GEN-END:variables
}
