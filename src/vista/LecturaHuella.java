package vista;

import excepciones.BibliotecaException;
import excepciones.Reaccion;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Lectura AUTOMÁTICA de la huella en Préstamos y Devoluciones (sin botón):
 *  - mientras no hay estudiante identificado y el lector está conectado, el lector espera el dedo;
 *  - al reconocer la huella deja de leer y muestra al estudiante;
 *  - al confirmar o cancelar (limpiar) vuelve a esperar al siguiente estudiante;
 *  - si el lector se desconecta se detiene, y al reconectarlo vuelve a escuchar solo;
 *  - al cerrar la ventana se cancela la lectura.
 *
 * COMPOSICIÓN: cada ventana TIENE una LecturaHuella y le pasa qué servicio identifica.
 * Qué hacer ante cada error lo decide la propia excepción (getReaccion) y una tabla de
 * reacciones, sin instanceof ni if/else por tipo de error.
 */
public class LecturaHuella {

    private static final int PAUSA_AVISO_MS = 1500;   // tiempo para leer "Huella no reconocida"

    private final java.awt.Window ventana;
    private final componentes.PanelHuella panel;
    private final Supplier<servicio.FichaEstudiante> identificar;
    private final Consumer<servicio.FichaEstudiante> alIdentificar;
    private final Map<Reaccion, Consumer<BibliotecaException>> reacciones = new EnumMap<>(Reaccion.class);
    private servicio.FichaEstudiante ficha;
    private boolean leyendo;
    private boolean activa = true;

    /**
     * @param identificar   el servicio que espera el dedo y arma la ficha (se ejecuta en segundo plano)
     * @param alIdentificar qué hace la ventana cuando el estudiante quedó identificado
     * @param cancelar      cómo se detiene la espera del dedo al cerrar la ventana
     */
    public LecturaHuella(java.awt.Window ventana, componentes.PanelHuella panel,
                         Supplier<servicio.FichaEstudiante> identificar,
                         Consumer<servicio.FichaEstudiante> alIdentificar, Runnable cancelar) {
        this.ventana = ventana;
        this.panel = panel;
        this.identificar = identificar;
        this.alIdentificar = alIdentificar;
        reacciones.put(Reaccion.REINTENTAR, e -> escuchar());
        reacciones.put(Reaccion.REINTENTAR_AVISANDO, e -> avisarYReintentar(e.getMessage()));
        reacciones.put(Reaccion.DETENER_LECTURA, e -> panel.mostrarResultado(e.getMessage(), false));
        reacciones.put(Reaccion.MOSTRAR_ERROR, e -> {
            panel.mostrarResultado("Sin identificar", false);
            util.Mensajes.error(ventana, e);
        });
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                activa = false;
                cancelar.run();
            }
        });
        limpiar();
    }

    /** Estudiante identificado (o null si todavía no). */
    public servicio.FichaEstudiante getFicha() {
        return ficha;
    }

    /** Vuelve a "sin identificar" y espera el dedo del siguiente estudiante. */
    public void limpiar() {
        ficha = null;
        panel.reiniciar();
        panel.limpiarEstudiante();
        escuchar();
    }

    /** Lo llama el monitor del lector (VentanaBase.alCambiarLector): conectado = empieza a escuchar. */
    public void lectorCambio(biometria.EstadoLector estado) {
        if (ficha == null && !leyendo) {
            panel.mostrarLector(estado);
        }
        if (estado.permiteCapturar()) {
            escuchar();
        }
    }

    // ---------- Lectura ----------

    /** Empieza a esperar el dedo, solo si corresponde (ventana abierta, lector listo, sin estudiante). */
    private void escuchar() {
        if (!activa || leyendo || ficha != null
                || !biometria.MonitorLector.getInstancia().getEstado().permiteCapturar()) {
            return;
        }
        leyendo = true;
        panel.reiniciar();
        util.Tareas.enSegundoPlano(ventana, identificar, this::identificado, this::fallo, () -> leyendo = false);
    }

    private void identificado(servicio.FichaEstudiante f) {
        if (!activa) {
            return;
        }
        ficha = f;
        panel.mostrarResultado("Huella reconocida", true);
        panel.mostrarAvance(0, " ");
        alIdentificar.accept(f);
    }

    /** Se ejecuta antes de que termine la tarea: la reacción se aplica cuando ya terminó. */
    private void fallo(BibliotecaException e) {
        if (activa) {
            javax.swing.SwingUtilities.invokeLater(() -> reacciones.get(e.getReaccion()).accept(e));
        }
    }

    private void avisarYReintentar(String mensaje) {
        panel.mostrarResultado(mensaje, false);
        javax.swing.Timer espera = new javax.swing.Timer(PAUSA_AVISO_MS, ev -> escuchar());
        espera.setRepeats(false);
        espera.start();
    }
}
