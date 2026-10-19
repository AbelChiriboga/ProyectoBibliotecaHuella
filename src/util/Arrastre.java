package util;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Las ventanas no tienen la barra de título de Windows (setUndecorated). Para poder moverlas,
 * se arrastran desde una zona (el encabezado verde o el fondo del login).
 */
public final class Arrastre {

    private Arrastre() {
    }

    /** Al arrastrar con el mouse sobre la zona, se mueve la ventana. */
    public static void permitir(Window ventana, Component zona) {
        MouseAdapter mover = new MouseAdapter() {
            private Point distancia;                     // del mouse a la esquina de la ventana

            @Override
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getLocationOnScreen();
                distancia = new Point(mouse.x - ventana.getX(), mouse.y - ventana.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point mouse = e.getLocationOnScreen();
                ventana.setLocation(mouse.x - distancia.x, mouse.y - distancia.y);
            }
        };
        zona.addMouseListener(mover);
        zona.addMouseMotionListener(mover);
    }
}
