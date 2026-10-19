package servicio.reglas;

import java.util.Optional;
import servicio.FichaEstudiante;

/**
 * Una regla que debe cumplirse para prestar. Cada regla es una clase (POLIMORFISMO):
 * PrestamoService recorre la lista de reglas sin saber cuál es cuál, sin if/else.
 * Agregar una regla nueva = crear una clase y agregarla a la lista (abierto/cerrado).
 */
@FunctionalInterface
public interface ReglaPrestamo {

    /**
     * @param ficha    el estudiante identificado con su huella
     * @param cantidad cuántos libros quiere llevar ahora
     * @return el motivo por el que NO se puede prestar, o vacío si la regla se cumple
     */
    Optional<String> incumplimiento(FichaEstudiante ficha, int cantidad);
}
