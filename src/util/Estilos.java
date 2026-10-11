package util;

/**
 * Estilos compartidos por todas las ventanas del sistema.
 * Así cada frame se ve igual sin repetir código.
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Estilos {

    public static final Color VERDE = new Color(0, 153, 153);
    public static final Color VERDE_TITULO = new Color(0, 122, 122);
    public static final Color BORDE = new Color(185, 198, 204);
    public static final Color TEXTO = new Color(31, 43, 51);
    public static final Color HOVER = new Color(230, 245, 243);
    public static final Font FUENTE = new Font("Segoe UI", Font.PLAIN, 14);

    /** Panel con borde verde redondeado y título. */
    public static void grupo(JPanel panel, String titulo) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(VERDE, 2, true),
                        titulo,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 15),
                        VERDE_TITULO),
                BorderFactory.createEmptyBorder(4, 8, 8, 8)));
    }

    /** Botón blanco; verde suave al pasar el mouse y verde completo al hacer clic. */
    public static void boton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFont(FUENTE);
        b.setBackground(Color.WHITE);
        b.setForeground(TEXTO);
        b.setBorder(BorderFactory.createLineBorder(BORDE, 1, true));

        b.getModel().addChangeListener(e -> {
            ButtonModel m = b.getModel();
            if (m.isPressed()) {
                b.setBackground(VERDE);
                b.setForeground(Color.WHITE);
                b.setBorder(BorderFactory.createLineBorder(VERDE, 1, true));
            } else if (m.isRollover()) {
                b.setBackground(HOVER);
                b.setForeground(TEXTO);
                b.setBorder(BorderFactory.createLineBorder(VERDE, 1, true));
            } else {
                b.setBackground(Color.WHITE);
                b.setForeground(TEXTO);
                b.setBorder(BorderFactory.createLineBorder(BORDE, 1, true));
            }
        });
    }

    /** Imagen escalada en un botón. Texto debajo si abajo = true, a la derecha si false. */
    public static void icono(JButton b, String archivo, int tamano, boolean abajo) {
        java.net.URL ruta = Estilos.class.getResource("/imagenes/" + archivo);
        if (ruta == null) {
            System.err.println("No se encontró la imagen: /imagenes/" + archivo);
            return;
        }
        Image img = new ImageIcon(ruta).getImage()
                .getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(img));
        if (abajo) {
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
        } else {
            b.setHorizontalTextPosition(SwingConstants.RIGHT);
            b.setVerticalTextPosition(SwingConstants.CENTER);
            b.setIconTextGap(8);
        }
    }

    /** Tabla de solo lectura con las columnas dadas y el estilo del sistema. */
    public static void tabla(JTable t, JScrollPane scroll, String... columnas) {
        t.setModel(new DefaultTableModel(new Object[][]{}, columnas) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        });
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setRowHeight(28);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setSelectionBackground(new Color(212, 238, 235));
        t.setSelectionForeground(Color.BLACK);
        t.setGridColor(new Color(227, 232, 235));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.getTableHeader().setReorderingAllowed(false);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(BORDE));
    }

    /** Reloj en vivo para los labels de fecha y hora del encabezado. */
    public static void reloj(JLabel fecha, JLabel hora) {
        Timer t = new Timer(1000, e -> {
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
            fecha.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            hora.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        t.setInitialDelay(0);
        t.start();
    }

    /** Encabezado, contenido y pie pegados (sin franjas grises) y tamaño estándar 1130x700. */
    public static void estructura(JFrame frame, JPanel encabezado,
                                  JPanel contenido, JPanel pie) {
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.add(encabezado, BorderLayout.NORTH);
        cp.add(contenido, BorderLayout.CENTER);
        cp.add(pie, BorderLayout.SOUTH);
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cp.setPreferredSize(new Dimension(1130, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}