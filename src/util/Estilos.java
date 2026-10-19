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

    /** Verde más oscuro para el botón principal al pasar el mouse. */
    public static final Color VERDE_OSCURO = new Color(0, 98, 98);

    /**
     * Botón blanco; verde suave al pasar el mouse y verde completo al hacer clic.
     * Si el botón tiene la marca "primario" (BotonSistema.setPrimario) va verde desde el inicio.
     */
    public static void boton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFont(FUENTE);
        pintarBoton(b);
        b.getModel().addChangeListener(e -> pintarBoton(b));
    }

    /** Colores del botón según su estado (normal, mouse encima, presionado). */
    public static void pintarBoton(JButton b) {
        ButtonModel m = b.getModel();
        boolean primario = Boolean.TRUE.equals(b.getClientProperty("primario"));
        Color fondo, texto, borde;
        if (!m.isEnabled()) {                 // deshabilitado: gris, para que se note que no se puede usar
            fondo = new Color(241, 244, 246);
            texto = new Color(150, 160, 168);
            borde = new Color(214, 221, 225);
        } else if (m.isPressed()) {
            fondo = primario ? VERDE_OSCURO : VERDE;
            texto = Color.WHITE;
            borde = fondo;
        } else if (m.isRollover()) {
            fondo = primario ? VERDE_OSCURO : HOVER;
            texto = primario ? Color.WHITE : TEXTO;
            borde = primario ? VERDE_OSCURO : VERDE;
        } else {
            fondo = primario ? VERDE_TITULO : Color.WHITE;
            texto = primario ? Color.WHITE : TEXTO;
            borde = primario ? VERDE_TITULO : BORDE;
        }
        b.setBackground(fondo);
        b.setForeground(texto);
        b.setBorder(BorderFactory.createLineBorder(borde, 1, true));
    }

    /** Barra de progreso plana y verde (Nimbus ignora el color si no se cambia el UI). */
    public static void barra(JProgressBar barra) {
        barra.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        barra.setForeground(VERDE);
        barra.setBackground(new Color(227, 232, 235));
        barra.setBorderPainted(false);
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
}
