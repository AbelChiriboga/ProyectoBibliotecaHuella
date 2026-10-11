/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author User
 */
public class MenuAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAdmin.class.getName());

    /**
     * Creates new form MenuAdmin
     */
    public MenuAdmin() {
        initComponents();
        initComponents();
        util.Estilos.reloj(jlblFecha, jlblHora);
        ajustarDiseno();
    }
    
    private void ajustarDiseno() {
        armarEncabezado();

        // ---- Grupos de botones ----
        estiloGrupoMenu(jpnlCatalogo, "Usuarios");
        estiloGrupoMenu(jpnlConfig, "Configuración");
        estiloGrupoMenu(jpnlReportes, "Reportes");

        jbtnEstudiante.setText("Estudiantes");
        jbtnPersonal.setText("Bibliotecarios");
        jbtnNuevo.setText("Parámetros");
        jbtnDevolver.setText("Bitácora");

        botonMenu(jbtnEstudiante, "Estudiantes.png");
        botonMenu(jbtnPersonal, "Bibliotecarios.png");
        botonMenu(jbtnNuevo, "Parametros.png");
        botonMenu(jbtnDevolver, "Bitacora.png");
        botonMenu(jbtnInventario, "Invetario.png");
        botonMenu(jbtnHistorial, "Historial.png");

        // ---- Tarjetas de resumen ----
        armarResumen();

        // ---- Encabezado + contenido + pie, tamaño estándar ----
        util.Estilos.estructura(this, jpnlInicio, jpnlMenu, jPanel6);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);   // es ventana principal
    }

    /** Encabezado: botones a la izquierda, título al centro, usuario a la derecha. */
    private void armarEncabezado() {
        jpnlInicio.removeAll();
        jpnlInicio.setLayout(new java.awt.BorderLayout(20, 0));
        jpnlInicio.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 16));

        // Izquierda: Cerrar sesión y Salir
        javax.swing.JPanel izquierda = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        izquierda.setOpaque(false);
        for (javax.swing.JButton b : new javax.swing.JButton[]{jbtnCerraSesion, jbtnSalir}) {
            util.Estilos.boton(b);
            b.setPreferredSize(new java.awt.Dimension(110, 100));
            b.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            b.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            izquierda.add(b);
        }
        jbtnCerraSesion.setText("Cerrar sesión");
        util.Estilos.icono(jbtnCerraSesion, "Cerrar.png", 44, true);
        util.Estilos.icono(jbtnSalir, "Exit.png", 55, true);
        jbtnSalir.setForeground(new java.awt.Color(140, 30, 30));
        jbtnSalir.addActionListener(e -> {
            int r = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?",
                    "Salir", javax.swing.JOptionPane.YES_NO_OPTION);
            if (r == javax.swing.JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Centro: título y subtítulo
        javax.swing.JPanel centro = new javax.swing.JPanel(new java.awt.GridLayout(2, 1));
        centro.setOpaque(false);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("MENÚ PRINCIPAL ADMINISTRADOR");
        centro.add(jLabel12);
        centro.add(jLabel13);

        // Derecha: bienvenida, nombre, cédula, fecha y hora
        javax.swing.JPanel derecha = new javax.swing.JPanel(new java.awt.GridLayout(4, 1));
        derecha.setOpaque(false);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        javax.swing.JPanel fechaHora = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));
        fechaHora.setOpaque(false);
        fechaHora.add(jLabel4);
        fechaHora.add(jlblFecha);
        fechaHora.add(jLabel5);
        fechaHora.add(jlblHora);
        derecha.add(jLabel3);
        derecha.add(jlblNombre);
        derecha.add(jLabel14);
        derecha.add(fechaHora);

        jpnlInicio.add(izquierda, java.awt.BorderLayout.WEST);
        jpnlInicio.add(centro, java.awt.BorderLayout.CENTER);
        jpnlInicio.add(derecha, java.awt.BorderLayout.EAST);
    }

    /** Grupo del menú: fondo blanco, borde verde y título centrado. */
    private void estiloGrupoMenu(javax.swing.JPanel panel, String titulo) {
        panel.setBackground(java.awt.Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(util.Estilos.VERDE, 2, true),
                titulo,
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15),
                util.Estilos.VERDE_TITULO));
    }

    /** Botón grande del menú: ícono arriba y texto abajo. */
    private void botonMenu(javax.swing.JButton b, String imagen) {
        util.Estilos.boton(b);
        b.setPreferredSize(new java.awt.Dimension(110, 110));
        util.Estilos.icono(b, imagen, 50, true);
    }

    /** Cuatro tarjetas del mismo ancho que los grupos de arriba. */
    private void armarResumen() {
        int ancho = jpnlCatalogo.getPreferredSize().width * 3 + 25 * 2;
        jpnlResumen.setPreferredSize(new java.awt.Dimension(ancho, 110));
        jpnlResumen.setBackground(java.awt.Color.WHITE);
        jpnlResumen.setLayout(new java.awt.GridLayout(1, 4, 25, 0));

        java.awt.Color naranja = new java.awt.Color(204, 85, 0);
        tarjeta(jpnlDevolucion, jLabel9, jlblDevoHoy, "Estudiantes registrados", util.Estilos.VERDE);
        tarjeta(jpnlDisponible, jLabel7, jlblDisponibles, "Bibliotecarios activos", util.Estilos.VERDE);
        tarjeta(jpnlActivo, jLabel1, jlblActivos, "Préstamos activos", util.Estilos.VERDE);
        tarjeta(jpnlAtrasados, jLabel2, jlblAtrasados, "Huellas fallidas hoy", naranja);

        // Orden de izquierda a derecha
        jpnlResumen.removeAll();
        jpnlResumen.add(jpnlDevolucion);
        jpnlResumen.add(jpnlDisponible);
        jpnlResumen.add(jpnlActivo);
        jpnlResumen.add(jpnlAtrasados);
    }

    private void tarjeta(javax.swing.JPanel tarjeta, javax.swing.JLabel titulo,
                         javax.swing.JLabel numero, String texto, java.awt.Color color) {
        tarjeta.removeAll();
        tarjeta.setLayout(new java.awt.BorderLayout(0, 4));
        tarjeta.setBackground(java.awt.Color.WHITE);
        tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(util.Estilos.VERDE, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        titulo.setText(texto);
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        titulo.setForeground(new java.awt.Color(80, 90, 100));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        numero.setText("0");
        numero.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        numero.setForeground(color);
        numero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tarjeta.add(titulo, java.awt.BorderLayout.NORTH);
        tarjeta.add(numero, java.awt.BorderLayout.CENTER);
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
        jbtnCerraSesion = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jlblNombre = new javax.swing.JLabel();
        jlblFecha = new javax.swing.JLabel();
        jlblHora = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jpnlMenu = new javax.swing.JPanel();
        jpnlCatalogo = new javax.swing.JPanel();
        jbtnEstudiante = new javax.swing.JButton();
        jbtnPersonal = new javax.swing.JButton();
        jpnlConfig = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnDevolver = new javax.swing.JButton();
        jpnlReportes = new javax.swing.JPanel();
        jbtnInventario = new javax.swing.JButton();
        jbtnHistorial = new javax.swing.JButton();
        jpnlResumen = new javax.swing.JPanel();
        jpnlDevolucion = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jlblDevoHoy = new javax.swing.JLabel();
        jpnlDisponible = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jlblDisponibles = new javax.swing.JLabel();
        jpnlAtrasados = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jlblAtrasados = new javax.swing.JLabel();
        jpnlActivo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlblActivos = new javax.swing.JLabel();
        jbtnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnlInicio.setBackground(new java.awt.Color(0, 153, 153));

        jbtnCerraSesion.setText("Cerrar Sesión");
        jbtnCerraSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCerraSesion.addActionListener(this::jbtnCerraSesionActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("BIENVENIDO");

        jlblNombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNombre.setForeground(new java.awt.Color(255, 255, 255));
        jlblNombre.setText("Usuario Name_LastName");

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
        jLabel13.setText("MENÚ PRINCIPAL ADMIN");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("C.I: 0000000000");

        javax.swing.GroupLayout jpnlInicioLayout = new javax.swing.GroupLayout(jpnlInicio);
        jpnlInicio.setLayout(jpnlInicioLayout);
        jpnlInicioLayout.setHorizontalGroup(
            jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnCerraSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2058, Short.MAX_VALUE)
                .addGroup(jpnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInicioLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInicioLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInicioLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(259, 259, 259)))
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
                            .addComponent(jbtnCerraSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jpnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        jpnlMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 80));

        jpnlCatalogo.setBorder(javax.swing.BorderFactory.createTitledBorder("Uusarios"));
        jpnlCatalogo.setPreferredSize(new java.awt.Dimension(340, 200));
        jpnlCatalogo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 12, 20));

        jbtnEstudiante.setText("Estudiante");
        jbtnEstudiante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnEstudiante.setPreferredSize(new java.awt.Dimension(95, 110));
        jbtnEstudiante.addActionListener(this::jbtnEstudianteActionPerformed);
        jpnlCatalogo.add(jbtnEstudiante);

        jbtnPersonal.setText("Personal");
        jbtnPersonal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnPersonal.setPreferredSize(new java.awt.Dimension(95, 110));
        jbtnPersonal.addActionListener(this::jbtnPersonalActionPerformed);
        jpnlCatalogo.add(jbtnPersonal);

        jpnlMenu.add(jpnlCatalogo);

        jpnlConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));
        jpnlConfig.setPreferredSize(new java.awt.Dimension(340, 200));
        jpnlConfig.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 12, 20));

        jbtnNuevo.setText("Parámetro");
        jbtnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnNuevo.setPreferredSize(new java.awt.Dimension(95, 110));
        jbtnNuevo.addActionListener(this::jbtnNuevoActionPerformed);
        jpnlConfig.add(jbtnNuevo);

        jbtnDevolver.setText("Bitácora");
        jbtnDevolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDevolver.setPreferredSize(new java.awt.Dimension(95, 110));
        jbtnDevolver.addActionListener(this::jbtnDevolverActionPerformed);
        jpnlConfig.add(jbtnDevolver);

        jpnlMenu.add(jpnlConfig);

        jpnlReportes.setBorder(javax.swing.BorderFactory.createTitledBorder("Reportes"));
        jpnlReportes.setPreferredSize(new java.awt.Dimension(340, 200));
        jpnlReportes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 12, 20));

        jbtnInventario.setText("Inventario");
        jbtnInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnInventario.setPreferredSize(new java.awt.Dimension(95, 110));
        jbtnInventario.addActionListener(this::jbtnInventarioActionPerformed);
        jpnlReportes.add(jbtnInventario);

        jbtnHistorial.setText("Historial");
        jbtnHistorial.setPreferredSize(new java.awt.Dimension(95, 110));
        jpnlReportes.add(jbtnHistorial);

        jpnlMenu.add(jpnlReportes);

        jpnlResumen.setPreferredSize(new java.awt.Dimension(1330, 120));
        jpnlResumen.setLayout(new java.awt.GridLayout(1, 4, 25, 0));

        jpnlDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        jpnlDevolucion.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("jLabel9");
        jpnlDevolucion.add(jLabel9, java.awt.BorderLayout.NORTH);

        jlblDevoHoy.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jlblDevoHoy.setForeground(new java.awt.Color(0, 204, 153));
        jlblDevoHoy.setText("jLabel10");
        jpnlDevolucion.add(jlblDevoHoy, java.awt.BorderLayout.CENTER);

        jpnlResumen.add(jpnlDevolucion);

        jpnlDisponible.setBackground(new java.awt.Color(255, 255, 255));
        jpnlDisponible.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("jLabel7");
        jpnlDisponible.add(jLabel7, java.awt.BorderLayout.NORTH);

        jlblDisponibles.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jlblDisponibles.setForeground(new java.awt.Color(0, 204, 153));
        jlblDisponibles.setText("jLabel8");
        jpnlDisponible.add(jlblDisponibles, java.awt.BorderLayout.CENTER);

        jpnlResumen.add(jpnlDisponible);

        jpnlAtrasados.setBackground(new java.awt.Color(255, 255, 255));
        jpnlAtrasados.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("jLabel2");
        jpnlAtrasados.add(jLabel2, java.awt.BorderLayout.NORTH);

        jlblAtrasados.setBackground(new java.awt.Color(255, 255, 255));
        jlblAtrasados.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jlblAtrasados.setForeground(new java.awt.Color(255, 51, 0));
        jlblAtrasados.setText("jLabel6");
        jpnlAtrasados.add(jlblAtrasados, java.awt.BorderLayout.CENTER);

        jpnlResumen.add(jpnlAtrasados);

        jpnlActivo.setBackground(new java.awt.Color(255, 255, 255));
        jpnlActivo.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("jLabel1");
        jpnlActivo.add(jLabel1, java.awt.BorderLayout.NORTH);

        jlblActivos.setBackground(new java.awt.Color(255, 255, 255));
        jlblActivos.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jlblActivos.setForeground(new java.awt.Color(0, 204, 153));
        jlblActivos.setText("jLabel2");
        jpnlActivo.add(jlblActivos, java.awt.BorderLayout.CENTER);

        jpnlResumen.add(jpnlActivo);

        jpnlMenu.add(jpnlResumen);

        jbtnSalir.setBackground(new java.awt.Color(0, 153, 153));
        jbtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jbtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnSalir.setIconTextGap(8);
        jbtnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnSalir.addActionListener(this::jbtnSalirActionPerformed);
        jpnlMenu.add(jbtnSalir);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2610, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2962, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jpnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 2950, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 2950, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 6, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jpnlInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jpnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCerraSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCerraSesionActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new Login());
    }//GEN-LAST:event_jbtnCerraSesionActionPerformed

    private void jbtnEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEstudianteActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new CRUDLibros());

    }//GEN-LAST:event_jbtnEstudianteActionPerformed

    private void jbtnPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPersonalActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new CRUDBibliotecarios());
    }//GEN-LAST:event_jbtnPersonalActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new Parametros());
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDevolverActionPerformed
        // TODO add your handling code here:
        util.Navegacion.abrir(this, new Bitacora());
    }//GEN-LAST:event_jbtnDevolverActionPerformed

    private void jbtnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInventarioActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jbtnInventarioActionPerformed

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnSalirActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MenuAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton jbtnCerraSesion;
    private javax.swing.JButton jbtnDevolver;
    private javax.swing.JButton jbtnEstudiante;
    private javax.swing.JButton jbtnHistorial;
    private javax.swing.JButton jbtnInventario;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnPersonal;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JLabel jlblActivos;
    private javax.swing.JLabel jlblAtrasados;
    private javax.swing.JLabel jlblDevoHoy;
    private javax.swing.JLabel jlblDisponibles;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblHora;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpnlActivo;
    private javax.swing.JPanel jpnlAtrasados;
    private javax.swing.JPanel jpnlCatalogo;
    private javax.swing.JPanel jpnlConfig;
    private javax.swing.JPanel jpnlDevolucion;
    private javax.swing.JPanel jpnlDisponible;
    private javax.swing.JPanel jpnlInicio;
    private javax.swing.JPanel jpnlMenu;
    private javax.swing.JPanel jpnlReportes;
    private javax.swing.JPanel jpnlResumen;
    // End of variables declaration//GEN-END:variables
}
