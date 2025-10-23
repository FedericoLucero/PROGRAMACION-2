package org.example;

import org.example.dao.JugadorDAO;
import org.example.model.Juego;
import org.example.model.Jugadores.Jugador;
import org.example.model.Jugadores.Profesion;
import org.example.ui.UserInput;

import java.sql.DriverManager;

public class MainTest1 {

    /**
    * Metodo main que debe ser el ejecutable
    */
    public static void main(String[] args) {

        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"

            int cantJugadores = UserInput.getInt("Ingrese cantidad de jugadores ",1,4);

            Juego juego = new Juego(cantJugadores); // clase principal donde se realiza el juego
            jugarDeNuevo = juego.jugar(); // jugar el juego

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");

    }

}
