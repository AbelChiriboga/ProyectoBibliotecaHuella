package servicio;

import excepciones.NegocioException;
import excepciones.ValidacionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import modelo.Ejemplar;
import modelo.OperacionAutenticacion;
import modelo.Parametro;
import modelo.Prestamo;
import servicio.reglas.EstudianteActivo;
import servicio.reglas.LimitePrestamos;
import servicio.reglas.ReglaPrestamo;
import servicio.reglas.SinMultasPendientes;

/**
 * Préstamo de uno o varios libros a la vez, SOLO con la huella del estudiante.
 * Las reglas se revisan aquí (para dar un mensaje claro antes de guardar) y otra vez en la BD
 * con el trigger trg_prestamo_validar (por si alguien inserta por fuera del sistema).
 */
public class PrestamoService extends AtencionConHuella {

    /** Reglas que debe cumplir el estudiante. Se recorren sin if/else (polimorfismo). */
    private final List<ReglaPrestamo> reglas = List.of(
            new EstudianteActivo(), new SinMultasPendientes(), new LimitePrestamos());

    private final dao.EjemplarDAO ejemplarDAO = new dao.EjemplarDAO();

    public PrestamoService() {
        super();
    }

    public PrestamoService(BiometriaService biometria) {
        super(biometria);
    }

    @Override
    protected OperacionAutenticacion operacion() {
        return OperacionAutenticacion.PRESTAMO;
    }

    @Override
    protected String accion() {
        return "registrar préstamos";
    }

    /** Ejemplares que se pueden prestar ahora (estado DISPONIBLE). */
    public List<Ejemplar> listarDisponibles() {
        return ejemplarDAO.buscarDisponibles("");
    }

    /** Fecha hasta la que tendría el libro si se presta hoy (DIAS_PRESTAMO). */
    public LocalDate fechaLimite() {
        return LocalDate.now().plusDays(parametroDAO.obtenerEntero(Parametro.DIAS_PRESTAMO));
    }

    /** Por qué este estudiante no puede llevar ni un libro (vacío = sí puede). Para mostrarlo al identificarlo. */
    public Optional<String> motivoBloqueo(FichaEstudiante ficha) {
        return primerIncumplimiento(ficha, 1);
    }

    /**
     * Registra los préstamos en UNA transacción (o todos o ninguno) y los anota en la bitácora.
     * @return los préstamos guardados, ya con la fecha límite que calculó la BD
     */
    public List<Prestamo> prestar(FichaEstudiante ficha, List<Ejemplar> ejemplares) {
        exigirPermiso();
        exigirIdentificado(ficha);
        if (ejemplares.isEmpty()) {
            throw new ValidacionException("Seleccione al menos un libro.");
        }
        primerIncumplimiento(ficha, ejemplares.size()).ifPresent(motivo -> {
            throw new NegocioException(motivo);
        });
        List<Prestamo> prestamos = new ArrayList<>();
        for (Ejemplar ej : ejemplares) {
            prestamos.add(nuevoPrestamo(ficha, ej));
        }
        prestamoDAO.insertarVarios(prestamos);
        anotarExito(ficha, "Préstamo de " + prestamos.size() + " libro(s): " + codigos(ejemplares));
        return recargar(ficha, prestamos);
    }

    // ---------- Ayudantes ----------

    private Optional<String> primerIncumplimiento(FichaEstudiante ficha, int cantidad) {
        return reglas.stream()
                .map(regla -> regla.incumplimiento(ficha, cantidad))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private static Prestamo nuevoPrestamo(FichaEstudiante ficha, Ejemplar ej) {
        if (!ej.getEstado().esPrestable()) {
            throw new NegocioException("El libro " + ej.getCodigoInventario() + " ya no está disponible.");
        }
        Prestamo p = new Prestamo(ej, ficha.getEstudiante(), Sesion.getInstancia().getUsuario());
        p.setHuellaPrestamo(true);                 // la huella ya se verificó al identificar
        return p;
    }

    private static String codigos(List<Ejemplar> ejemplares) {
        return ejemplares.stream().map(Ejemplar::getCodigoInventario).collect(Collectors.joining(", "));
    }

    /** Vuelve a leer los préstamos recién guardados (la fecha límite la pone el trigger). */
    private List<Prestamo> recargar(FichaEstudiante ficha, List<Prestamo> guardados) {
        Set<Integer> ids = guardados.stream().map(Prestamo::getId).collect(Collectors.toSet());
        return prestamoDAO.listarActivosPorEstudiante(ficha.getEstudiante().getIdEstudiante()).stream()
                .filter(p -> ids.contains(p.getId()))
                .collect(Collectors.toList());
    }
}
