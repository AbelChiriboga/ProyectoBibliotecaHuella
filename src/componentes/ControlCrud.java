package componentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Habilita y deshabilita los campos y botones de un formulario CRUD según el modo.
 * Lo usan todos los CRUD, así ninguno repite la lógica de "qué botón va activo".
 *
 * Cada constante del enum Modo define sus propias reglas (polimorfismo en el enum):
 * no hay un switch que pregunte en qué modo estamos.
 */
public class ControlCrud {

    /** Estados del formulario. */
    public enum Modo {
        /** Solo se ve la información; campos bloqueados. */
        CONSULTA {
            @Override boolean camposEditables() { return false; }
            @Override boolean puedeEmpezar(boolean haySeleccion) { return true; }
            @Override boolean puedeActuarSobreSeleccion(boolean haySeleccion) { return haySeleccion; }
        },
        /** Llenando un registro nuevo. */
        NUEVO {
            @Override boolean camposEditables() { return true; }
            @Override boolean puedeEmpezar(boolean haySeleccion) { return false; }
            @Override boolean puedeActuarSobreSeleccion(boolean haySeleccion) { return false; }
        },
        /** Modificando el registro seleccionado. */
        EDICION {
            @Override boolean camposEditables() { return true; }
            @Override boolean puedeEmpezar(boolean haySeleccion) { return false; }
            @Override boolean puedeActuarSobreSeleccion(boolean haySeleccion) { return false; }
        };

        /** ¿Se puede escribir en los campos? (también habilita Guardar y Cancelar). */
        abstract boolean camposEditables();

        /** ¿Se puede presionar Nuevo? */
        abstract boolean puedeEmpezar(boolean haySeleccion);

        /** ¿Se puede Editar / Eliminar / usar los botones que necesitan una fila seleccionada? */
        abstract boolean puedeActuarSobreSeleccion(boolean haySeleccion);
    }

    private final List<JComponent> campos;
    private final JButton nuevo;
    private final JButton guardar;
    private final JButton editar;
    private final JButton eliminar;
    private final JButton cancelar;
    private final List<JComponent> conSeleccion = new ArrayList<>();
    private final List<JComponent> bloqueadosAlEditar = new ArrayList<>();
    private Modo modo = Modo.CONSULTA;

    public ControlCrud(JComponent[] campos, JButton nuevo, JButton guardar, JButton editar,
                       JButton eliminar, JButton cancelar) {
        this.campos = Arrays.asList(campos);
        this.nuevo = nuevo;
        this.guardar = guardar;
        this.editar = editar;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
    }

    /** Componentes extra que solo funcionan con una fila seleccionada (por ejemplo el botón "Ejemplares"). */
    public void agregarBotonDeSeleccion(JComponent componente) {
        conSeleccion.add(componente);
    }

    /**
     * Componentes que se bloquean mientras se llena un registro nuevo o se edita uno
     * (normalmente la tabla): así el usuario no puede seleccionar otra fila y perder lo que escribió.
     */
    public void bloquearAlEditar(JComponent... componentes) {
        bloqueadosAlEditar.addAll(Arrays.asList(componentes));
    }

    public void consulta(boolean haySeleccion) {
        aplicar(Modo.CONSULTA, haySeleccion);
    }

    public void nuevo() {
        aplicar(Modo.NUEVO, false);
    }

    public void edicion() {
        aplicar(Modo.EDICION, true);
    }

    public Modo getModo() {
        return modo;
    }

    /**
     * Bloquea (en consulta) o desbloquea (Nuevo / Editar) un campo. Bloqueado no se puede
     * escribir ni hacer clic, pero el texto se sigue viendo OSCURO, no gris
     * (ver Estilos.textoDeshabilitadoLegible).
     */
    public static void permitirEscribir(JComponent c, boolean editable) {
        c.setEnabled(editable);
        if (c instanceof javax.swing.text.JTextComponent) {
            ((javax.swing.text.JTextComponent) c).setDisabledTextColor(util.Estilos.TEXTO);
        }
    }

    private void aplicar(Modo modo, boolean haySeleccion) {
        this.modo = modo;
        boolean editable = modo.camposEditables();
        campos.forEach(c -> permitirEscribir(c, editable));
        guardar.setEnabled(editable);
        cancelar.setEnabled(editable);
        nuevo.setEnabled(modo.puedeEmpezar(haySeleccion));
        boolean sobreSeleccion = modo.puedeActuarSobreSeleccion(haySeleccion);
        editar.setEnabled(sobreSeleccion);
        eliminar.setEnabled(sobreSeleccion);
        conSeleccion.forEach(b -> b.setEnabled(sobreSeleccion));
        bloqueadosAlEditar.forEach(c -> c.setEnabled(!editable));
    }
}
