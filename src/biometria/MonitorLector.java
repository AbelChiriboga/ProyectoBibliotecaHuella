package biometria;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;

/**
 * Vigila el lector de huella cada segundo, en un hilo aparte (no congela las ventanas).
 *
 * Patrón OBSERVADOR: las ventanas se suscriben y reciben el nuevo estado cuando cambia:
 *  - oyentes: el pie de página de cada ventana ("Lector: Conectado / Desconectado").
 *  - avisos:  solo los CAMBIOS reales (no el primer estado al abrir el sistema), para
 *             mostrar el mensaje "Lector conectado" / "Lector desconectado".
 * Singleton: hay un solo monitor para todo el sistema.
 */
public final class MonitorLector {

    private static final MonitorLector INSTANCIA = new MonitorLector();
    private static final int INTERVALO_MS = 1_000;

    private final List<Consumer<EstadoLector>> oyentes = new CopyOnWriteArrayList<>();
    private final List<Consumer<EstadoLector>> avisos = new CopyOnWriteArrayList<>();
    private ScheduledExecutorService hilo;
    private volatile EstadoLector estado;

    private MonitorLector() {
    }

    public static MonitorLector getInstancia() {
        return INSTANCIA;
    }

    /** Arranca la vigilancia (se llama una vez, en Main). */
    public synchronized void iniciar() {
        if (hilo != null) {
            return;
        }
        hilo = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "monitor-lector");
            t.setDaemon(true);                     // no impide cerrar el programa
            return t;
        });
        hilo.scheduleWithFixedDelay(this::revisar, 0, INTERVALO_MS, TimeUnit.MILLISECONDS);
    }

    /** Último estado conocido (DESCONECTADO mientras no se haya revisado). */
    public EstadoLector getEstado() {
        return estado == null ? EstadoLector.DESCONECTADO : estado;
    }

    /** Recibe el estado actual (si ya se revisó) y cada vez que cambie (por ejemplo, el pie de página). */
    public void agregarOyente(Consumer<EstadoLector> oyente) {
        oyentes.add(oyente);
        if (estado != null) {
            oyente.accept(estado);
        }
    }

    public void quitarOyente(Consumer<EstadoLector> oyente) {
        oyentes.remove(oyente);
    }

    /** Recibe solo los cambios mientras el sistema está en uso (para el mensaje emergente). */
    public void alCambiar(Consumer<EstadoLector> aviso) {
        avisos.add(aviso);
    }

    private void revisar() {
        try {
            EstadoLector nuevo = FabricaLector.obtener().verificarEstado();
            EstadoLector anterior = estado;
            if (nuevo != anterior) {
                estado = nuevo;
                SwingUtilities.invokeLater(() -> notificar(anterior, nuevo));
            }
        } catch (RuntimeException | LinkageError e) {
            System.err.println("Monitor del lector: " + e.getMessage());
        }
    }

    private void notificar(EstadoLector anterior, EstadoLector nuevo) {
        oyentes.forEach(o -> o.accept(nuevo));
        if (anterior != null) {                    // el primer estado (al iniciar) no es un "cambio"
            avisos.forEach(a -> a.accept(nuevo));
        }
    }
}
