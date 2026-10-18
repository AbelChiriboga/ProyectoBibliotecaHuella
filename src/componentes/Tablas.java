package componentes;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Configuración de tablas en un solo lugar.
 * Préstamos y Devoluciones tenían copiado el mismo modelo con columna de casillas.
 */
public final class Tablas {

    private Tablas() {
    }

    /** Tabla de solo lectura con el estilo del sistema. */
    public static void soloLectura(JTable tabla, JScrollPane scroll, String... columnas) {
        util.Estilos.tabla(tabla, scroll, columnas);
    }

    /**
     * Tabla con una primera columna de casillas (para marcar ejemplares o préstamos).
     * Solo la casilla es editable.
     */
    public static void conSeleccion(JTable tabla, JScrollPane scroll, String... columnas) {
        util.Estilos.tabla(tabla, scroll, columnas);
        String[] todas = new String[columnas.length + 1];
        todas[0] = "";
        System.arraycopy(columnas, 0, todas, 1, columnas.length);

        tabla.setModel(new DefaultTableModel(new Object[][]{}, todas) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int fila, int col) {
                return col == 0;
            }
        });
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
    }

    /** Anchos preferidos de las columnas, en orden. Usa 0 para dejar una columna con su ancho normal. */
    public static void anchos(JTable tabla, int... anchos) {
        for (int i = 0; i < anchos.length && i < tabla.getColumnCount(); i++) {
            if (anchos[i] > 0) {
                tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        }
    }
}
