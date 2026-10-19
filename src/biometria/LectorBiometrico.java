package biometria;

import java.util.Map;
import java.util.Optional;

/**
 * Contrato de un lector de huella. El resto del sistema solo conoce esta INTERFAZ:
 * no sabe si detrás está el lector ZKTeco real o el simulado (polimorfismo).
 *
 * Todos los métodos de captura BLOQUEAN hasta que se pone el dedo o se vence el tiempo,
 * por eso las ventanas los llaman en segundo plano (util.Tareas).
 */
public interface LectorBiometrico extends AutoCloseable {

    /** Nombre para mostrar en el pie de página ("ZKTeco", "Simulado"). */
    String getNombre();

    /** Inicializa el SDK y abre el dispositivo. */
    void abrir();

    /** Libera el dispositivo. */
    void cerrar();

    boolean estaAbierto();

    /**
     * Revisa si el lector sigue conectado. Si se desconectó, libera el dispositivo;
     * si se volvió a conectar, lo abre de nuevo. Lo llama MonitorLector cada 2 segundos.
     */
    EstadoLector verificarEstado();

    /** Espera un dedo y devuelve su plantilla (template). */
    byte[] capturar();

    /**
     * Registro: toma 3 muestras del MISMO dedo y las une en una sola plantilla.
     * El aviso informa a la ventana cuántas muestras van.
     */
    byte[] registrar(AvisoCaptura aviso);

    /** Carga en memoria las plantillas para identificar (clave = id de la huella). */
    void cargarPlantillas(Map<Integer, byte[]> plantillas);

    /** Identificación 1:N: ¿a cuál de las plantillas cargadas pertenece la muestra? */
    Optional<Coincidencia> identificar(byte[] muestra);

    /** Permite usar el lector en try-with-resources. */
    @Override
    default void close() {
        cerrar();
    }
}
