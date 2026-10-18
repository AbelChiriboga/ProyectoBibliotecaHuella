package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/** Panel que se ajusta al ancho del JScrollPane: solo hay scroll vertical. */
public class PanelScroll extends JPanel implements Scrollable {

    public PanelScroll() {
        super(new BorderLayout());
        setBackground(Color.WHITE);
    }

    @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
    @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 16; }
    @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 120; }
    @Override public boolean getScrollableTracksViewportWidth() { return true; }
    @Override public boolean getScrollableTracksViewportHeight() { return false; }
}
