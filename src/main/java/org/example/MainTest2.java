package org.example;

import org.example.dao.JugadorDAO;
import org.example.model.Jugadores.Jugador;
import org.example.ui.UserInput;

public class MainTest2 {

    public static void main(String[] args) {

        //test jugador dao
        //para usarlo se instancia Jugador y luego se llama al metodo de la clase JugadorDAO para agregarlo
        //Insatancio UI para validacion

        UserInput ui = new UserInput();
        String nombre = ui.getString("Ingrese el nombre del jugador", 3, 10);
        Jugador j1 = new Jugador(nombre);
        JugadorDAO jdao = new JugadorDAO();

        jdao.actualizar("posicion", 2, "jug3");
        jdao.actualizar("profesion", "soldador", "jug3");
    }
}
