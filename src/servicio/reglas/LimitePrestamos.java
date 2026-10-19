package servicio.reglas;

import java.util.Optional;
import servicio.FichaEstudiante;

/** No superar el máximo de préstamos activos (parámetro MAX_PRESTAMOS). */
public class LimitePrestamos implements ReglaPrestamo {

    @Override
    public Optional<String> incumplimiento(FichaEstudiante ficha, int cantidad) {
        if (ficha.getCupos() == 0) {
            return Optional.of("El estudiante ya tiene " + ficha.getCantidadActivos() + " préstamos activos (máximo "
                    + ficha.getMaximoPrestamos() + "). Debe devolver un libro antes de llevar otro.");
        }
        return cantidad > ficha.getCupos()
                ? Optional.of("Solo puede llevar " + ficha.getCupos() + " libro(s) más (máximo "
                        + ficha.getMaximoPrestamos() + " a la vez). Desmarque " + (cantidad - ficha.getCupos()) + ".")
                : Optional.empty();
    }
}
