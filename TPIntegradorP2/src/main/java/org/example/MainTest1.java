package org.example;

import org.example.model.Juego;

public class MainTest1 {

    /**
    * Metodo main que debe ser el ejecutable
    */
    public static void main(String[] args) {

        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"
            Juego juego = new Juego(); // clase principal donde se realiza el juego
            jugarDeNuevo = juego.jugar(); // jugar el juego

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");

    }

}
