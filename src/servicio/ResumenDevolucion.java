package servicio;

import java.math.BigDecimal;
import java.util.List;
import modelo.Multa;
import modelo.Prestamo;

/**
 * Cálculo de lo que se va a cobrar al devolver los libros marcados.
 * Usa la MISMA fórmula que el trigger trg_devolucion_liberar (días de atraso × multa por día),
 * por eso lo que se ve antes de confirmar es lo que la BD genera.
 */
public final class ResumenDevolucion {

    private final int diasRetraso;
    private final BigDecimal multaPorDia;
    private final BigDecimal total;

    public ResumenDevolucion(List<Prestamo> prestamos, BigDecimal multaPorDia) {
        this.multaPorDia = multaPorDia;
        this.diasRetraso = prestamos.stream().mapToInt(Prestamo::diasAtraso).sum();
        this.total = prestamos.stream()
                .map(p -> Multa.calcularMonto(p.diasAtraso(), multaPorDia))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getDiasRetraso() {
        return diasRetraso;
    }

    public BigDecimal getMultaPorDia() {
        return multaPorDia;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public boolean hayMulta() {
        return total.signum() > 0;
    }
}
