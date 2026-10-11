package util;

/**
 * Estilos compartidos por todas las ventanas del sistema.
 * Así cada frame se ve igual sin repetir código.
 */
public class Estilos {

    public static final java.awt.Color VERDE = new java.awt.Color(0, 153, 153);
    public static final java.awt.Color VERDE_TITULO = new java.awt.Color(0, 122, 122);
    public static final java.awt.Color BORDE = new java.awt.Color(185, 198, 204);
    public static final java.awt.Color TEXTO = new java.awt.Color(31, 43, 51);
    public static final java.awt.Color HOVER = new java.awt.Color(230, 245, 243);
    public static final java.awt.Font FUENTE = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);

    /** Panel con borde verde redondeado y título. */
    public static void grupo(javax.swing.JPanel panel, String titulo) {
        panel.setBackground(java.awt.Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createTitledBorder(
                        javax.swing.BorderFactory.createLineBorder(VERDE, 2, true),
                        titulo,
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15),
                        VERDE_TITULO),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 8, 8)));
    }

    /** Botón blanco; verde suave al pasar el mouse y verde completo al hacer clic. */
    public static void boton(javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b.setFont(FUENTE);
        b.setBackground(java.awt.Color.WHITE);
        b.setForeground(TEXTO);
        b.setBorder(javax.swing.BorderFactory.createLineBorder(BORDE, 1, true));

        b.getModel().addChangeListener(e -> {
            javax.swing.ButtonModel m = b.getModel();
            if (m.isPressed()) {
                b.setBackground(VERDE);
                b.setForeground(java.awt.Color.WHITE);
                b.setBorder(javax.swing.BorderFactory.createLineBorder(VERDE, 1, true));
            } else if (m.isRollover()) {
                b.setBackground(HOVER);
                b.setForeground(TEXTO);
                b.setBorder(javax.swing.BorderFactory.createLineBorder(VERDE, 1, true));
            } else {
                b.setBackground(java.awt.Color.WHITE);
                b.setForeground(TEXTO);
                b.setBorder(javax.swing.BorderFactory.createLineBorder(BORDE, 1, true));
            }
        });
    }

    /** Imagen escalada en un botón. Texto debajo si abajo = true, a la derecha si false. */
    public static void icono(javax.swing.JButton b, String archivo, int tamano, boolean abajo) {
        java.net.URL ruta = Estilos.class.getResource("/imagenes/" + archivo);
        if (ruta == null) {
            System.err.println("No se encontró la imagen: /imagenes/" + archivo);
            return;
        }
        java.awt.Image img = new javax.swing.ImageIcon(ruta).getImage()
                .getScaledInstance(tamano, tamano, java.awt.Image.SCALE_SMOOTH);
        b.setIcon(new javax.swing.ImageIcon(img));
        if (abajo) {
            b.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            b.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        } else {
            b.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            b.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
            b.setIconTextGap(8);
        }
    }

    /** Tabla de solo lectura con las columnas dadas y el estilo del sistema. */
    public static void tabla(javax.swing.JTable t, javax.swing.JScrollPane scroll, String... columnas) {
        t.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, columnas) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        });
        t.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        t.setRowHeight(28);
        t.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        t.setSelectionBackground(new java.awt.Color(212, 238, 235));
        t.setSelectionForeground(java.awt.Color.BLACK);
        t.setGridColor(new java.awt.Color(227, 232, 235));
        t.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        t.getTableHeader().setReorderingAllowed(false);
        scroll.getViewport().setBackground(java.awt.Color.WHITE);
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(BORDE));
    }

    /** Reloj en vivo para los labels de fecha y hora del encabezado. */
    public static void reloj(javax.swing.JLabel fecha, javax.swing.JLabel hora) {
        javax.swing.Timer t = new javax.swing.Timer(1000, e -> {
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
            fecha.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            hora.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        t.setInitialDelay(0);
        t.start();
    }

    /** Encabezado, contenido y pie pegados (sin franjas grises) y tamaño estándar 1130x700. */
    public static void estructura(javax.swing.JFrame frame, javax.swing.JPanel encabezado,
                                  javax.swing.JPanel contenido, javax.swing.JPanel pie) {
        java.awt.Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new java.awt.BorderLayout());
        cp.add(encabezado, java.awt.BorderLayout.NORTH);
        cp.add(contenido, java.awt.BorderLayout.CENTER);
        cp.add(pie, java.awt.BorderLayout.SOUTH);
        contenido.setBackground(java.awt.Color.WHITE);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        cp.setPreferredSize(new java.awt.Dimension(1130, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}