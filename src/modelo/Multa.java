package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import util.Validaciones;

/**
 * Multa por devolver tarde. La GENERA el trigger de la BD al registrar la devolución;
 * en Java solo se lee, se marca como pagada y se calcula una vista previa.
 */
public class Multa extends Entidad {

    private Prestamo prestamo;
    private int diasAtraso;
    private BigDecimal monto = BigDecimal.ZERO;
    private boolean pagada;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaPago;

    public Multa() {
    }

    /**
     * Vista previa del monto (días × multa por día) para mostrarla en Devoluciones
     * ANTES de confirmar. El valor que se guarda lo calcula el trigger.
     */
    public static BigDecimal calcularMonto(int diasAtraso, BigDecimal multaPorDia) {
        if (diasAtraso <= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)).setScale(2, RoundingMode.HALF_UP);
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = Validaciones.rango(diasAtraso, "días de atraso", 0, Integer.MAX_VALUE);
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = Validaciones.noNegativo(monto, "monto");
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    /** Monto con formato de dinero, por ejemplo "$2.00". */
    public String getMontoTexto() {
        return "$" + monto.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
