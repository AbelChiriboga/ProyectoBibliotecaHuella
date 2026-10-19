package vista;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Lo que comparten Préstamos y Devoluciones en el panel de huella: leer el dedo en segundo plano,
 * mostrar el avance, guardar la ficha del estudiante identificado y reaccionar si el lector
 * se conecta o desconecta.
 *
 * COMPOSICIÓN: cada ventana TIENE una LecturaHuella (no hereda de ella) y le pasa
 * qué servicio identifica (PrestamoService o DevolucionService) y qué hacer con la ficha.
 */
public class LecturaHuella {

    private final java.awt.Component ventana;
    private final componentes.PanelHuella panel;
    private final Supplier<servicio.FichaEstudiante> identificar;
    private final Consumer<servicio.FichaEstudiante> alIdentificar;
    private servicio.FichaEstudiante ficha;
    private boolean leyendo;

    /**
     * @param identificar   el servicio que espera el dedo y arma la ficha (se ejecuta en segundo plano)
     * @param alIdentificar qué hace la ventana cuando el estudiante quedó identificado
     */
    public LecturaHuella(java.awt.Component ventana, componentes.PanelHuella panel,
                         Supplier<servicio.FichaEstudiante> identificar,
                         Consumer<servicio.FichaEstudiante> alIdentificar) {
        this.ventana = ventana;
        this.panel = panel;
        this.identificar = identificar;
        this.alIdentificar = alIdentificar;
        limpiar();
    }

    /** Espera el dedo del estudiante sin congelar la ventana. */
    public void leer() {
        if (leyendo) {
            return;
        }
        leyendo = true;
        ficha = null;
        panel.limpiarEstudiante();
        panel.iniciarCaptura();
        panel.mostrarAvance(0, "Coloque el dedo del estudiante en el lector...");
        util.Tareas.enSegundoPlano(ventana, identificar, this::identificado, this::terminar);
    }

    /** Estudiante identificado (o null si todavía no). */
    public servicio.FichaEstudiante getFicha() {
        return ficha;
    }

    /** Vuelve a "sin identificar". */
    public void limpiar() {
        ficha = null;
        panel.reiniciar();
        panel.limpiarEstudiante();
    }

    /** El recuadro avisa si el lector está desconectado (se pasa a VentanaBase.alCambiarLector). */
    public void lectorCambio(biometria.EstadoLector estado) {
        if (!leyendo && ficha == null) {
            panel.mostrarLector(estado);
        }
    }

    private void identificado(servicio.FichaEstudiante f) {
        ficha = f;
        panel.mostrarResultado("Huella reconocida<br>" + f.getEstudiante().getNombres(), true);
        panel.mostrarAvance(0, "Puntaje de la huella: " + f.getPuntaje());
        alIdentificar.accept(f);
    }

    private void terminar() {
        leyendo = false;
        panel.terminarCaptura();
        if (ficha == null) {
            panel.mostrarResultado("Sin identificar", false);
            panel.mostrarAvance(0, "Presione Leer huella para intentar de nuevo.");
        }
    }
}
