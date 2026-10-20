package servicio;

import biometria.AvisoCaptura;
import biometria.FabricaLector;
import biometria.LectorBiometrico;
import dao.EstudianteDAO;
import dao.HuellaDAO;
import dao.ParametroDAO;
import dao.RegistroAutenticacionDAO;
import excepciones.BibliotecaException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import modelo.Estudiante;
import modelo.Huella;
import modelo.OperacionAutenticacion;
import modelo.Parametro;
import modelo.RegistroAutenticacion;
import modelo.ResultadoAutenticacion;
import modelo.Usuario;
import util.Validaciones;

/**
 * Todo lo que tiene que ver con huellas: registrar, identificar 1:N y anotar cada intento
 * en la bitácora. Trabaja con la INTERFAZ LectorBiometrico, así funciona igual con el
 * lector ZKTeco o con el simulado.
 *
 * Los métodos que usan el lector bloquean hasta que se pone el dedo: llamarlos con util.Tareas.
 */
public class BiometriaService {

    private final LectorBiometrico lector;
    private final HuellaDAO huellaDAO = new HuellaDAO();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final RegistroAutenticacionDAO bitacora = new RegistroAutenticacionDAO();
    private final ParametroDAO parametroDAO = new ParametroDAO();

    public BiometriaService() {
        this(FabricaLector.obtener());
    }

    /** Permite usar otro lector (por ejemplo, el simulado en pruebas). */
    public BiometriaService(LectorBiometrico lector) {
        this.lector = lector;
    }

    public String getNombreLector() {
        return lector.getNombre();
    }

    /**
     * Captura la huella de un dedo para el formulario del estudiante: 3 muestras del mismo dedo.
     * Verifica que esa huella no pertenezca ya a OTRO estudiante. NO guarda nada:
     * la huella se guarda junto con el estudiante al presionar Guardar (EstudianteService).
     */
    public byte[] capturarHuella(Estudiante e, AvisoCaptura aviso) {
        Sesion.getInstancia().exigir(Usuario::puedeRegistrarEstudiantes, "registrar huellas");
        Validaciones.requerido(e, "un estudiante");
        exigirLectorListo();
        lector.nuevaCaptura();
        byte[] plantilla = capturarRegistrando(e, aviso);
        exigirQueNoSeaDeOtro(e, plantilla);
        return plantilla;
    }

    /**
     * Identificación 1:N: compara la muestra con TODAS las huellas registradas y devuelve
     * el estudiante dueño, solo si el puntaje alcanza el parámetro UMBRAL_HUELLA.
     */
    public Optional<Identificacion> identificar(byte[] muestra) {
        Map<Integer, Integer> duenos = cargarPlantillasEnLector();
        int umbral = parametroDAO.obtenerEntero(Parametro.UMBRAL_HUELLA);
        return lector.identificar(muestra)
                .filter(c -> c.getPuntaje() >= umbral)
                .flatMap(c -> estudianteDAO.buscarPorId(duenos.get(c.getIdHuella()))
                        .map(est -> new Identificacion(est, c.getPuntaje())));
    }

    /**
     * Préstamo / devolución: el estudiante pone el dedo y se busca entre TODAS las huellas (1:N).
     * Si nadie coincide (o el puntaje no llega al umbral) se anota el FALLO en la bitácora.
     * Bloquea hasta que se pone el dedo: llamarlo con util.Tareas.
     */
    public Identificacion identificarEstudiante(OperacionAutenticacion operacion) {
        exigirLectorListo();
        lector.nuevaCaptura();
        byte[] muestra = lector.capturar();
        return identificar(muestra).orElseThrow(() -> {
            anotar(null, operacion, ResultadoAutenticacion.FALLO, null, "Huella no reconocida");
            return new excepciones.HuellaNoReconocidaException();
        });
    }

    /** Deja de esperar el dedo (por ejemplo, al cerrar la ventana de préstamos). */
    public void cancelarCaptura() {
        lector.cancelarCaptura();
    }

    /** Anota un intento en la bitácora (préstamo, devolución o registro de huella). */
    public void anotar(Integer idEstudiante, OperacionAutenticacion operacion, ResultadoAutenticacion resultado,
                       Integer puntaje, String detalle) {
        bitacora.insertar(new RegistroAutenticacion(idEstudiante, operacion, resultado, puntaje, detalle));
    }

    // ---------- Ayudantes ----------

    /** Sin lector conectado no se registran ni se leen huellas (el resto del sistema sigue funcionando). */
    private void exigirLectorListo() {
        biometria.EstadoLector estado = lector.verificarEstado();
        if (!estado.permiteCapturar()) {
            throw new excepciones.LectorException("Lector " + estado.getDescripcion().toLowerCase() + ".");
        }
    }

    private byte[] capturarRegistrando(Estudiante e, AvisoCaptura aviso) {
        try {
            return lector.registrar(aviso);
        } catch (BibliotecaException ex) {
            anotar(idParaBitacora(e), OperacionAutenticacion.REGISTRO_HUELLA, ResultadoAutenticacion.FALLO, null,
                    ex.getMessage());
            throw ex;
        }
    }

    private void exigirQueNoSeaDeOtro(Estudiante e, byte[] plantilla) {
        identificar(plantilla)
                .filter(id -> id.getEstudiante().getIdEstudiante() != e.getIdEstudiante())
                .ifPresent(id -> {
                    anotar(idParaBitacora(e), OperacionAutenticacion.REGISTRO_HUELLA, ResultadoAutenticacion.FALLO,
                            id.getPuntaje(), "La huella ya pertenece a " + id.getEstudiante().getCedula());
                    throw new excepciones.HuellaDuplicadaException(id.getEstudiante().getNombreCompleto());
                });
    }

    /** Un estudiante que aún no se guarda no tiene id: en la bitácora va nulo. */
    private static Integer idParaBitacora(Estudiante e) {
        return e.esNuevo() ? null : e.getIdEstudiante();
    }

    /** Pasa todas las huellas de la BD al lector. Devuelve: id de huella → id de estudiante. */
    private Map<Integer, Integer> cargarPlantillasEnLector() {
        List<Huella> huellas = huellaDAO.listar();
        Map<Integer, byte[]> plantillas = new HashMap<>();
        Map<Integer, Integer> duenos = new HashMap<>();
        for (Huella h : huellas) {
            plantillas.put(h.getId(), h.getPlantilla());
            duenos.put(h.getId(), h.getIdEstudiante());
        }
        lector.cargarPlantillas(plantillas);
        return duenos;
    }

    /** Resultado de identificar: el estudiante dueño de la huella y el puntaje del lector. */
    public static final class Identificacion {

        private final Estudiante estudiante;
        private final int puntaje;

        public Identificacion(Estudiante estudiante, int puntaje) {
            this.estudiante = estudiante;
            this.puntaje = puntaje;
        }

        public Estudiante getEstudiante() {
            return estudiante;
        }

        public int getPuntaje() {
            return puntaje;
        }
    }
}
