package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/** Cómo se muestran fechas y dinero en todo el sistema (un solo lugar). */
public final class Formatos {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Formatos() {
    }

    /** 25/10/2026 (sirve con LocalDate y LocalDateTime); "—" si es nula. */
    public static String fecha(TemporalAccessor f) {
        return f == null ? "—" : FECHA.format(f);
    }

    /** $1.50 */
    public static String dinero(BigDecimal valor) {
        return "$" + (valor == null ? BigDecimal.ZERO : valor).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
