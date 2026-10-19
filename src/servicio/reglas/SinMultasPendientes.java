package servicio.reglas;

import java.util.Optional;
import servicio.FichaEstudiante;

/**
 * Decisión del equipo: un estudiante con multas SIN PAGAR no puede llevar libros
 * hasta que las pague (aunque esté activo y tenga cupos).
 */
public class SinMultasPendientes implements ReglaPrestamo {

    @Override
    public Optional<String> incumplimiento(FichaEstudiante ficha, int cantidad) {
        return ficha.tieneMultasPendientes()
                ? Optional.of("El estudiante tiene multas pendientes por $" + ficha.getMultasPendientes()
                        .setScale(2, java.math.RoundingMode.HALF_UP).toPlainString() + ". Debe pagarlas antes de llevar libros.")
                : Optional.empty();
    }
}
