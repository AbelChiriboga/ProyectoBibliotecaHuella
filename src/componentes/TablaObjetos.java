package componentes;

import excepciones.ValidacionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Une una JTable con la lista de OBJETOS que muestra (genérica: TablaObjetos<Libro>,
 * TablaObjetos<Autor>...). Así, al seleccionar una fila se obtiene directamente el objeto,
 * sin volver a buscarlo en la BD ni leer celda por celda.
 *
 * Además filtra mientras se escribe (sobre los datos ya cargados) y ordena al hacer clic
 * en los títulos de las columnas.
 *
 * Uso:
 *   Tablas.soloLectura(jtblAutores, jScrollPane1, "Nombres", "Apellidos", "Nacionalidad");
 *   tabla = new TablaObjetos<>(jtblAutores, a -> new Object[]{a.getNombres(), a.getApellidos(), a.getNacionalidad()});
 */
public class TablaObjetos<T> {

    private final JTable tabla;
    private final Function<T, Object[]> fila;
    private final List<T> datos = new ArrayList<>();
    private final TableRowSorter<TableModel> ordenador;

    /** @param fila cómo se convierte un objeto en las celdas de una fila (en el orden de las columnas) */
    public TablaObjetos(JTable tabla, Function<T, Object[]> fila) {
        this.tabla = tabla;
        this.fila = fila;
        this.ordenador = new TableRowSorter<>(tabla.getModel());
        tabla.setRowSorter(ordenador);
    }

    /** Reemplaza el contenido de la tabla por la lista. */
    public void mostrar(List<T> lista) {
        // Antes de cambiar las filas se borra la selección (también su "ancla"),
        // si no, Swing avisa que la fila seleccionada ya no existe.
        tabla.clearSelection();
        tabla.getSelectionModel().setAnchorSelectionIndex(-1);
        tabla.getSelectionModel().setLeadSelectionIndex(-1);
        datos.clear();
        datos.addAll(lista);
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (T objeto : lista) {
            modelo.addRow(fila.apply(objeto));
        }
    }

    /** Objetos cargados (solo lectura). */
    public List<T> getDatos() {
        return Collections.unmodifiableList(datos);
    }

    /** Objeto de la fila seleccionada, o vacío si no hay selección. */
    public Optional<T> getSeleccionado() {
        int fila = tabla.getSelectedRow();
        return fila < 0 ? Optional.empty() : Optional.of(datos.get(tabla.convertRowIndexToModel(fila)));
    }

    /** Objeto seleccionado; si no hay, avisa al usuario. Ejemplo: exigirSeleccionado("un libro"). */
    public T exigirSeleccionado(String que) {
        return getSeleccionado().orElseThrow(() -> new ValidacionException("Seleccione " + que + " en la tabla."));
    }

    /** Ejecuta la acción cada vez que el usuario selecciona una fila. */
    public void alSeleccionar(Consumer<T> accion) {
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                getSeleccionado().ifPresent(accion);
            }
        });
    }

    /** Selecciona la fila del objeto indicado (por ejemplo, el que se acaba de guardar). */
    public void seleccionar(T objeto) {
        int indice = datos.indexOf(objeto);
        if (indice >= 0) {
            int vista = tabla.convertRowIndexToView(indice);
            if (vista >= 0) {
                tabla.setRowSelectionInterval(vista, vista);
                tabla.scrollRectToVisible(tabla.getCellRect(vista, 0, true));
            }
        }
    }

    public void limpiarSeleccion() {
        tabla.clearSelection();
    }

    // ---------- Tablas con casillas (Tablas.conSeleccion): columna 0 = marcado ----------

    /** Objetos cuya casilla está marcada (aunque el filtro los esté ocultando). */
    public List<T> getMarcados() {
        List<T> marcados = new ArrayList<>();
        TableModel modelo = tabla.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (Boolean.TRUE.equals(modelo.getValueAt(i, 0))) {
                marcados.add(datos.get(i));
            }
        }
        return marcados;
    }

    /** Ejecuta la acción cada vez que se marca o desmarca una casilla. */
    public void alMarcar(Runnable accion) {
        tabla.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 0) {
                accion.run();
            }
        });
    }

    /** Deja ver solo las filas que coinciden con el texto en cualquier columna. */
    public void filtrar(String texto) {
        filtrar(texto, new int[0]);
    }

    /**
     * Filtra buscando de IZQUIERDA A DERECHA: el valor debe EMPEZAR con lo escrito
     * ("180" encuentra la cédula 1805346721; "53" no). En textos con varias palabras basta
     * con que empiece alguna palabra ("san" encuentra "Abel Santiago").
     * No distingue mayúsculas ni tildes. columnas vacío = todas.
     */
    public void filtrar(String texto, int... columnas) {
        String buscado = normalizar(texto);
        ordenador.setRowFilter(buscado.isEmpty() ? null : new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> fila) {
                for (int c : columnasABuscar(columnas, fila.getValueCount())) {
                    if (empiezaCon(fila.getStringValue(c), buscado)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private static int[] columnasABuscar(int[] columnas, int total) {
        return columnas.length > 0 ? columnas : java.util.stream.IntStream.range(0, total).toArray();
    }

    /** ¿El valor (o alguna de sus palabras) empieza con el texto buscado? */
    private static boolean empiezaCon(String valor, String buscado) {
        String v = normalizar(valor);
        if (v.startsWith(buscado)) {
            return true;
        }
        for (String palabra : v.split("\\s+")) {
            if (palabra.startsWith(buscado)) {
                return true;
            }
        }
        return false;
    }

    /** Minúsculas y sin tildes, para que "lucia" encuentre "Lucía". */
    private static String normalizar(String texto) {
        String t = texto == null ? "" : texto.trim().toLowerCase();
        return java.text.Normalizer.normalize(t, java.text.Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    /**
     * Conecta la barra de búsqueda con esta tabla: el filtro de la barra ofrece las columnas
     * de la tabla y cada vez que se escribe (o se cambian las columnas) la tabla se filtra.
     * Las columnas sin título (por ejemplo, la de casillas) no se ofrecen.
     */
    public void usarBarra(BarraBusqueda barra) {
        List<Integer> indices = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < tabla.getModel().getColumnCount(); i++) {
            String nombre = tabla.getModel().getColumnName(i);
            if (nombre != null && !nombre.isBlank()) {
                indices.add(i);
                nombres.add(nombre);
            }
        }
        barra.configurarFiltro(nombres);
        barra.alBuscar(texto -> filtrar(texto, barra.getColumnasSeleccionadas().stream()
                .mapToInt(indices::get).toArray()));
    }
}
