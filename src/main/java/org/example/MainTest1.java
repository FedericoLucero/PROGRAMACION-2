package org.example;

import org.example.ui.UserInput;

public class MainTest1 {

    /**
    * Metodo main que debe ser el ejecutable
    */
    public static void main(String[] args) {

        boolean jugarDeNuevo;

        // bucle "voler a jugar"
        do {

            // metodo que pide la cantidad de jugadores (min 1, max 4)
            int cantJugadores = UserInput.getInt("Ingrese cantidad de jugadores ",1,4);

            // objeto principal donde se realiza la partida
            Juego juego = new Juego(cantJugadores);

            // metodo donde se realiza la partida
            jugarDeNuevo = juego.jugar();

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");
    }

}
