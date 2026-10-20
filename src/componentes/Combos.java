package componentes;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * Llenado de combos con OBJETOS (Autor, Categoria, Carrera, enums...). El combo muestra
 * su toString() y al leerlo se obtiene el objeto completo, no un texto.
 */
public final class Combos {

    private Combos() {
    }

    /** Reemplaza los elementos del combo; intenta conservar el que estaba seleccionado. */
    public static <T> void llenar(JComboBox<T> combo, List<T> elementos) {
        Object anterior = combo.getSelectedItem();
        combo.setModel(new DefaultComboBoxModel<>(new java.util.Vector<>(elementos)));
        combo.setSelectedItem(anterior);
        if (combo.getSelectedIndex() < 0 && !elementos.isEmpty()) {
            combo.setSelectedIndex(0);
        }
    }

    /** Llena el combo con las constantes de un enum. */
    public static <E extends Enum<E>> void llenar(JComboBox<E> combo, Class<E> tipoEnum) {
        llenar(combo, List.of(tipoEnum.getEnumConstants()));
    }

    /**
     * Deja el combo del ancho JUSTO para mostrar completo su texto más largo
     * (por ejemplo, "Tecnologías de la Información"). Conserva el alto que tenía.
     */
    public static <T> void ajustarAncho(JComboBox<T> combo) {
        java.awt.FontMetrics fm = combo.getFontMetrics(combo.getFont());
        T masLargo = null;
        for (int i = 0; i < combo.getItemCount(); i++) {
            T item = combo.getItemAt(i);
            if (masLargo == null || fm.stringWidth(String.valueOf(item)) > fm.stringWidth(String.valueOf(masLargo))) {
                masLargo = item;
            }
        }
        if (masLargo == null) {
            return;
        }
        int alto = combo.getPreferredSize().height;
        combo.setPrototypeDisplayValue(masLargo);
        combo.setPreferredSize(null);                       // que el combo calcule su ancho con el texto más largo
        java.awt.Dimension justo = new java.awt.Dimension(combo.getPreferredSize().width + 8, alto);
        combo.setPreferredSize(justo);
        combo.setMinimumSize(justo);
        combo.revalidate();
    }

    /** Objeto seleccionado con su tipo (null si el combo está vacío). */
    public static <T> T seleccionado(JComboBox<T> combo) {
        return combo.getItemAt(combo.getSelectedIndex());
    }

    /** Selecciona el elemento igual al indicado (usa equals: mismo id). */
    public static <T> void seleccionar(JComboBox<T> combo, T elemento) {
        combo.setSelectedItem(elemento);
    }
}
