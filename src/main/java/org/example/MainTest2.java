package org.example;

import org.example.model.Jugadores.Jugador;
import org.example.model.Piezas.Cartas.CartaAzul;
import org.example.model.Piezas.Cartas.CartaNaranja;
import org.example.utils.UserInput;

public class MainTest2 {

    public static void main(String[] args) {

        Jugador j1 = Jugador.buscarJugadorPorId(1);
        System.out.println(j1.getProfesion().getTitulo());
        //Test buscar cartaNaranja
        CartaNaranja cartaN1 = CartaNaranja.buscarCartaId(5);
        System.out.println(cartaN1.getDescripcion());
        //Test buscar carta azul
        CartaAzul profesion1 = CartaAzul.buscarCartaId(2);
        System.out.println(profesion1.getTitulo());
    }

}
