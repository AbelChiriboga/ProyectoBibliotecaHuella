package componentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Caja de búsqueda que se estira + botón de filtro + botón Buscar.
 * Se repetía igual en Libros, Estudiantes, Bibliotecarios, Atrasos, Préstamos, etc.
 */
public class BarraBusqueda extends JPanel {

    private final JTextField campo = new JTextField();
    private final BotonSistema botonFiltro = new BotonSistema("");
    private final BotonSistema botonBuscar = new BotonSistema("Buscar");

    public BarraBusqueda() {
        super(new BorderLayout(8, 0));
        setOpaque(false);

        campo.setFont(util.Estilos.FUENTE);
        campo.setPreferredSize(new Dimension(200, 32));

        botonFiltro.setToolTipText("Filtrar");
        botonFiltro.setPreferredSize(new Dimension(36, 32));
        botonFiltro.setRutaIcono("filtro.png");
        botonFiltro.setTamanoIcono(18);
        botonBuscar.setPreferredSize(new Dimension(90, 32));

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        derecha.setOpaque(false);
        derecha.add(botonFiltro);
        derecha.add(botonBuscar);

        add(campo, BorderLayout.CENTER);
        add(derecha, BorderLayout.EAST);
    }

    public JTextField getCampo() { return campo; }
    public BotonSistema getBotonFiltro() { return botonFiltro; }
    public BotonSistema getBotonBuscar() { return botonBuscar; }

    /** Texto de ayuda al pasar el mouse sobre la caja. */
    public String getAyuda() { return campo.getToolTipText(); }
    public void setAyuda(String ayuda) { campo.setToolTipText(ayuda); }

    public boolean isFiltroVisible() { return botonFiltro.isVisible(); }
    public void setFiltroVisible(boolean visible) { botonFiltro.setVisible(visible); }

    public boolean isBuscarVisible() { return botonBuscar.isVisible(); }
    public void setBuscarVisible(boolean visible) { botonBuscar.setVisible(visible); }
}
