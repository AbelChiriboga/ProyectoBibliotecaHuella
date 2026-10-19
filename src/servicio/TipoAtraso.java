package servicio;

import java.math.BigDecimal;
import java.util.List;
import modelo.Multa;
import modelo.Prestamo;

/**
 * Qué se ve en la pantalla Atrasos. Enum POLIMÓRFICO: cada opción sabe qué préstamos listar,
 * cuántos días y cuánta multa mostrar, y si se puede registrar el pago. La ventana no usa if/else.
 */
public enum TipoAtraso {

    /** Libros que el estudiante todavía no devuelve y ya pasó la fecha: la multa va creciendo. */
    PRESTAMOS_ATRASADOS("Préstamos atrasados") {
        @Override
        public List<Prestamo> listar(AtrasoService s) {
            return s.listarAtrasados();
        }

        @Override
        public int dias(Prestamo p) {
            return p.diasAtraso();
        }

        @Override
        public BigDecimal multa(Prestamo p, BigDecimal multaPorDia) {
            return Multa.calcularMonto(p.diasAtraso(), multaPorDia);     // estimada a hoy
        }

        @Override
        public boolean permitePago() {
            return false;                    // la multa se genera recién al devolver
        }
    },

    /** Libros ya devueltos tarde: la multa está generada y falta pagarla. */
    MULTAS_PENDIENTES("Multas pendientes") {
        @Override
        public List<Prestamo> listar(AtrasoService s) {
            return s.listarMultasPendientes();
        }

        @Override
        public int dias(Prestamo p) {
            return p.getMulta().getDiasAtraso();
        }

        @Override
        public BigDecimal multa(Prestamo p, BigDecimal multaPorDia) {
            return p.getMulta().getMonto();
        }

        @Override
        public boolean permitePago() {
            return true;
        }
    };

    private final String descripcion;

    TipoAtraso(String descripcion) {
        this.descripcion = descripcion;
    }

    public abstract List<Prestamo> listar(AtrasoService servicio);

    public abstract int dias(Prestamo p);

    public abstract BigDecimal multa(Prestamo p, BigDecimal multaPorDia);

    public abstract boolean permitePago();

    @Override
    public String toString() {
        return descripcion;
    }
}
