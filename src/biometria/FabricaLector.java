  package biometria;

/**
 * Entrega el lector que usa todo el sistema (una sola instancia: el dispositivo se abre una vez).
 *
 * Por defecto es el lector ZKTeco real. Si no está conectado, el sistema sigue funcionando
 * y solo se bloquea lo que necesita huella; MonitorLector lo abre solo cuando se conecte.
 *
 * Para probar SIN el lector se puede usar el simulado: en NetBeans, Propiedades del proyecto →
 * Run → VM Options:  -Dbiblioteca.lector=simulado
 * Quien pide el lector recibe un LectorBiometrico y no sabe cuál de los dos es (polimorfismo).
 */
public final class FabricaLector {

    public static final String PROPIEDAD = "biblioteca.lector";

    private static LectorBiometrico lector;

    private FabricaLector() {
    }

    public static synchronized LectorBiometrico obtener() {
        if (lector == null) {
            lector = "simulado".equalsIgnoreCase(System.getProperty(PROPIEDAD))
                    ? new LectorSimulado() : new LectorZKTeco();
            // Al cerrar el programa se libera el dispositivo
            Runtime.getRuntime().addShutdownHook(new Thread(lector::cerrar));
        }
        return lector;
    }
}
