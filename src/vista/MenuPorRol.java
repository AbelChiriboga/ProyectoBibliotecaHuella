package vista;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import modelo.Rol;
import modelo.Usuario;

/**
 * Decide qué menú abrir después del login SIN if/else ni instanceof.
 *
 * Cada rol está asociado a la "fábrica" de su menú (Supplier = referencia al constructor).
 * Se le pregunta al usuario su rol con getRol(), que es un método polimórfico
 * (Administrador y Bibliotecario lo implementan distinto), y con ese rol se busca el menú.
 * Si mañana aparece un rol nuevo, solo se agrega una línea aquí: no se toca el Login.
 */
public final class MenuPorRol {

    private static final Map<Rol, Supplier<javax.swing.JFrame>> MENUS = new EnumMap<>(Rol.class);

    static {
        MENUS.put(Rol.ADMINISTRADOR, MenuAdmin::new);
        MENUS.put(Rol.BIBLIOTECARIO, MenuBibliotecario::new);
    }

    private MenuPorRol() {
    }

    /** Crea el menú que corresponde al usuario. */
    public static javax.swing.JFrame crearPara(Usuario usuario) {
        return MENUS.get(usuario.getRol()).get();
    }
}
