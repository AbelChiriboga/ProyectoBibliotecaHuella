package componentes;

import java.util.ArrayList;
import java.util.List;

/**
 * Elemento de un combo de FILTRO: un valor con su texto. La opción "Todos" lleva valor null,
 * que para los DAO significa "no filtrar por esto".
 * Ejemplo: Combos.llenar(jcbxOperacion, Opcion.conTodas("Todas", List.of(PRESTAMO, DEVOLUCION)));
 */
public final class Opcion<T> {

    private final T valor;
    private final String texto;

    public Opcion(T valor, String texto) {
        this.valor = valor;
        this.texto = texto;
    }

    /** null = todos. */
    public T getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return texto;
    }

    /** "Todos" primero y después cada valor con su toString(). */
    public static <T> List<Opcion<T>> conTodas(String textoTodos, List<T> valores) {
        List<Opcion<T>> opciones = new ArrayList<>();
        opciones.add(new Opcion<>(null, textoTodos));
        valores.forEach(v -> opciones.add(new Opcion<>(v, String.valueOf(v))));
        return opciones;
    }
}
