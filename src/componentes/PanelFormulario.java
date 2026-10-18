package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Formulario en cuadrícula: label arriba y campo abajo.
 * Reemplaza los métodos celda(), campo() y apilar() que estaban copiados en 6 frames.
 *
 * Uso:
 *   PanelFormulario f = new PanelFormulario("Datos del libro");
 *   f.campo("ISBN", jtxtISBN, 0, 0, 2);
 *   f.campo("Editorial", jtxtEditorial, 0, 2, 1);
 *   f.campo("Año", jtxtAnio, 1, 2, 1);
 *   f.fila(panelBotones, 4, 2);
 *   f.empujarArriba(5);
 */
public class PanelFormulario extends PanelGrupo {

    public PanelFormulario() {
        this("Formulario");
    }

    public PanelFormulario(String titulo) {
        super(titulo);
        setLayout(new GridBagLayout());
    }

    /** Label arriba y campo abajo. Ocupa las filas 'fila' y 'fila + 1'. Devuelve la siguiente fila libre. */
    public int campo(String etiqueta, JComponent campo, int col, int fila, int ancho) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(util.Estilos.FUENTE);
        lbl.setLabelFor(campo);
        campo.setFont(util.Estilos.FUENTE);
        if (campo.getPreferredSize().height < 32) {
            campo.setPreferredSize(new Dimension(120, 32));
        }
        add(lbl, restriccion(col, fila, ancho, new Insets(2, 6, 3, 6)));
        add(campo, restriccion(col, fila + 1, ancho, new Insets(0, 6, 10, 6)));
        return fila + 2;
    }

    /** Un componente que ocupa una fila completa (botones, avisos, etc.). */
    public int fila(JComponent comp, int fila, int ancho) {
        add(comp, restriccion(0, fila, ancho, new Insets(4, 6, 8, 6)));
        return fila + 1;
    }

    /** Título de sección con una línea debajo (por ejemplo "Datos de la cuenta"). */
    public int separador(String texto, int fila, int ancho) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(util.Estilos.VERDE_TITULO);
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 225, 228)),
                BorderFactory.createEmptyBorder(0, 0, 4, 0)));
        add(lbl, restriccion(0, fila, ancho, new Insets(6, 6, 8, 6)));
        return fila + 1;
    }

    /** Relleno invisible al final: hace que todo quede pegado arriba. */
    public void empujarArriba(int fila) {
        GridBagConstraints g = restriccion(0, fila, 4, new Insets(0, 0, 0, 0));
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        JPanel relleno = new JPanel();
        relleno.setOpaque(false);
        add(relleno, g);
    }

    private GridBagConstraints restriccion(int col, int fila, int ancho, Insets insets) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = col;
        g.gridy = fila;
        g.gridwidth = ancho;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.insets = insets;
        return g;
    }
}
