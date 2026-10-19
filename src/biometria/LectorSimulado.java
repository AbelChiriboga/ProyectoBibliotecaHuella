package biometria;

import excepciones.LectorException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Lector SIMULADO para probar el sistema sin el lector conectado.
 *
 * "Poner el dedo" = escribir un código en un cuadro de diálogo (por ejemplo, la cédula del
 * estudiante). El mismo código produce siempre la misma plantilla, así que registrar con
 * "1800000001" y luego identificar con "1800000001" coincide; otro código no coincide.
 */
public class LectorSimulado extends LectorBase {

    private volatile javax.swing.JDialog dialogo;

    private static final int PUNTAJE_COINCIDENCIA = 90;

    private boolean abierto;
    private String ultimoCodigo = "";
    private final Map<Integer, byte[]> plantillas = new HashMap<>();

    @Override
    public String getNombre() {
        return "Simulado";
    }

    @Override
    public void abrir() {
        abierto = true;
    }

    @Override
    public void cerrar() {
        abierto = false;
    }

    @Override
    public boolean estaAbierto() {
        return abierto;
    }

    @Override
    public EstadoLector verificarEstado() {
        abierto = true;
        return EstadoLector.SIMULADO;
    }

    /** Pide el código del "dedo" en un diálogo (en el hilo de Swing, aunque se llame en segundo plano). */
    @Override
    public byte[] capturar() {
        exigirAbierto();
        String[] respuesta = new String[1];
        Runnable pedir = () -> respuesta[0] = pedirCodigo();
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                pedir.run();
            } else {
                SwingUtilities.invokeAndWait(pedir);
            }
        } catch (InterruptedException | InvocationTargetException e) {
            throw new LectorException("Captura cancelada.");
        }
        if (respuesta[0] == null || respuesta[0].isBlank()) {
            throw new LectorException("No se detectó el dedo.");
        }
        ultimoCodigo = respuesta[0].trim();
        return util.Seguridad.sha256("HUELLA:" + ultimoCodigo).getBytes(java.nio.charset.StandardCharsets.US_ASCII);
    }

    /** Ventana donde se "pone el dedo" (se escribe el código). Se cierra si se cancela la captura. */
    private String pedirCodigo() {
        JOptionPane panel = new JOptionPane(
                "LECTOR SIMULADO\nEscriba el código del dedo (por ejemplo, la cédula del estudiante):",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        panel.setWantsInput(true);
        panel.setInitialSelectionValue(ultimoCodigo);
        javax.swing.JDialog d = panel.createDialog(null, "Lector simulado");
        dialogo = d;
        d.setVisible(true);                       // espera hasta que se responda o se cancele
        d.dispose();
        dialogo = null;
        Object valor = panel.getInputValue();
        return valor == JOptionPane.UNINITIALIZED_VALUE || !Integer.valueOf(JOptionPane.OK_OPTION).equals(panel.getValue())
                ? null : (String) valor;
    }

    /** Cierra la ventana del lector simulado si está esperando (por ejemplo, al cerrar Préstamos). */
    @Override
    public void cancelarCaptura() {
        SwingUtilities.invokeLater(() -> {
            if (dialogo != null) {
                dialogo.dispose();
            }
        });
    }

    @Override
    protected boolean mismoDedo(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }

    @Override
    protected byte[] fusionar(byte[][] muestras) {
        return muestras[0].clone();
    }

    @Override
    public void cargarPlantillas(Map<Integer, byte[]> plantillas) {
        this.plantillas.clear();
        this.plantillas.putAll(plantillas);
    }

    @Override
    public Optional<Coincidencia> identificar(byte[] muestra) {
        return plantillas.entrySet().stream()
                .filter(e -> Arrays.equals(e.getValue(), muestra))
                .map(e -> new Coincidencia(e.getKey(), PUNTAJE_COINCIDENCIA))
                .findFirst();
    }
}
