package servicio;

import excepciones.NegocioException;
import excepciones.ValidacionException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import modelo.OperacionAutenticacion;
import modelo.Parametro;
import modelo.Prestamo;

/**
 * Devolución de uno o varios libros, SOLO con la huella del mismo estudiante que los llevó.
 * Los triggers de la BD ponen la fecha de devolución, liberan el ejemplar y generan la multa.
 */
public class DevolucionService extends AtencionConHuella {

    public DevolucionService() {
        super();
    }

    public DevolucionService(BiometriaService biometria) {
        super(biometria);
    }

    @Override
    protected OperacionAutenticacion operacion() {
        return OperacionAutenticacion.DEVOLUCION;
    }

    @Override
    protected String accion() {
        return "registrar devoluciones";
    }

    public BigDecimal multaPorDia() {
        return parametroDAO.obtenerDecimal(Parametro.MULTA_POR_DIA);
    }

    /**
     * Registra la devolución en UNA transacción y la anota en la bitácora.
     * @return el resumen con la multa que realmente generó la BD
     */
    public ResumenDevolucion devolver(FichaEstudiante ficha, List<Prestamo> marcados) {
        exigirPermiso();
        exigirIdentificado(ficha);
        if (marcados.isEmpty()) {
            throw new ValidacionException("Marque en la tabla los libros que está devolviendo el estudiante.");
        }
        exigirQueSeanSuyos(ficha, marcados);
        List<Integer> ids = marcados.stream().map(Prestamo::getId).collect(Collectors.toList());
        prestamoDAO.registrarDevoluciones(ids);
        anotarExito(ficha, "Devolución de " + ids.size() + " libro(s): " + marcados.stream()
                .map(p -> p.getEjemplar().getCodigoInventario()).collect(Collectors.joining(", ")));
        return new ResumenDevolucion(devueltos(ficha, ids), multaPorDia());
    }

    /** La huella debe ser del mismo estudiante que tiene los libros (nadie devuelve por otro). */
    private static void exigirQueSeanSuyos(FichaEstudiante ficha, List<Prestamo> marcados) {
        Set<Integer> suyos = ficha.getPrestamosActivos().stream().map(Prestamo::getId).collect(Collectors.toSet());
        marcados.stream().filter(p -> !suyos.contains(p.getId())).findFirst().ifPresent(p -> {
            throw new NegocioException("El préstamo de " + p.getEjemplar().getCodigoInventario()
                    + " no es del estudiante identificado o ya fue devuelto.");
        });
    }

    /** Los préstamos recién devueltos, leídos otra vez de la BD (con fecha de devolución). */
    private List<Prestamo> devueltos(FichaEstudiante ficha, List<Integer> ids) {
        return prestamoDAO.listarPorEstudiante(ficha.getEstudiante().getIdEstudiante()).stream()
                .filter(p -> ids.contains(p.getId()))
                .collect(Collectors.toList());
    }
}
