package codigo;


public class Main {

    /**
     * Metodo main que debe ser el ejecutable
     */
    public static void main(String[] args) {

        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"

            // objeto principal donde se realiza la partida
            Juego juego = new Juego();
            // metodo donde se realiza la partida
            jugarDeNuevo = juego.jugar();

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");
    }

}