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
        /*
        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"

            UserInput UI = new  UserInput();
            int cantJugadores = UI.getInt("Ingrese cantidad de jugadores ",1,4);

            Juego juego = new Juego(cantJugadores); // clase principal donde se realiza el juego
            jugarDeNuevo = juego.jugar(); // jugar el juego

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");
*/
        //test jugador dao
        //para usarlo se instancia Jugador y luego se llama al metodo de la clase JugadorDAO para agregarlo
        //Insatancio UI para validacion
        UserInput ui = new UserInput();
        String nombre = ui.getString("Ingrese el nombre del jugador",3,10);
        Jugador j1 = new Jugador(nombre);
        JugadorDAO jdao = new JugadorDAO();

        jdao.actualizar("posicion",2,"jug3");
        jdao.actualizar("profesion","soldador","jug3");

    }

}
