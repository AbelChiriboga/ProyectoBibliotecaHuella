package util;

import excepciones.BibliotecaException;
import java.awt.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.SwingWorker;

/**
 * Ejecuta un trabajo lento (por ejemplo, esperar el dedo en el lector) en segundo plano,
 * para que la ventana no se congele. Al terminar vuelve al hilo de Swing para actualizar
 * la pantalla. Si el trabajo lanza una excepción del sistema, se muestra con Mensajes.
 */
public final class Tareas {

    private Tareas() {
    }

    /**
     * @param trabajo    lo que se hace en segundo plano (no debe tocar componentes Swing)
     * @param alTerminar qué hacer con el resultado, ya en el hilo de Swing
     * @param siempre    se ejecuta al final, haya error o no (por ejemplo, volver a habilitar el botón)
     */
    public static <T> void enSegundoPlano(Component padre, Supplier<T> trabajo, Consumer<T> alTerminar, Runnable siempre) {
        enSegundoPlano(padre, trabajo, alTerminar, e -> Mensajes.error(padre, e), siempre);
    }

    /** Igual, pero la ventana decide qué hacer si el trabajo falla (en vez de mostrar el mensaje). */
    public static <T> void enSegundoPlano(Component padre, Supplier<T> trabajo, Consumer<T> alTerminar,
                                          Consumer<BibliotecaException> alFallar, Runnable siempre) {
        new SwingWorker<T, Void>() {
            private BibliotecaException error;

            @Override
            protected T doInBackground() {
                try {
                    return trabajo.get();
                } catch (BibliotecaException e) {
                    error = e;
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    get();                                   // relanza errores inesperados
                    if (error == null) {
                        Mensajes.ejecutar(padre, () -> alTerminar.accept(getResultado()));
                    } else {
                        alFallar.accept(error);
                    }
                } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
                    Mensajes.error(padre, new excepciones.DatosException("Error inesperado: " + e.getMessage(), e));
                } finally {
                    siempre.run();
                }
            }

            private T getResultado() {
                try {
                    return get();
                } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
                    throw new IllegalStateException(e);
                }
            }
        }.execute();
    }
}
