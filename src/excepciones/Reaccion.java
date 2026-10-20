package excepciones;

/**
 * Qué debe hacer la pantalla de lectura de huella (Préstamos / Devoluciones) cuando falla una lectura.
 * Cada excepción dice su reacción (polimorfismo) y la ventana la busca en una tabla, sin instanceof.
 */
public enum Reaccion {
    /** Error normal: se muestra el mensaje y se deja de leer. */
    MOSTRAR_ERROR,
    /** Problema del lector (desconectado, cancelado): se deja de leer hasta que vuelva a estar listo. */
    DETENER_LECTURA,
    /** Nadie puso el dedo a tiempo: se vuelve a escuchar sin mostrar nada. */
    REINTENTAR,
    /** La huella no coincidió: se avisa en el panel y se vuelve a escuchar. */
    REINTENTAR_AVISANDO,
    /** Aviso grave del registro de huella (la huella ya es de otro estudiante): se muestra en rojo. */
    ALERTA
}
