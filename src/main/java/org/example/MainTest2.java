package org.example;

import org.example.model.Jugadores.Jugador;
import org.example.model.Piezas.Cartas.CartaAzul;
import org.example.model.Piezas.Cartas.CartaNaranja;
import org.example.model.Piezas.Tablero;
import org.example.utils.UserInput;

import java.util.List;

public class MainTest2 {

    public static void main(String[] args) {

//        Jugador j1 = Jugador.buscarJugadorPorId(1);
//        System.out.println(j1.getProfesion().getTitulo());
//        //Test buscar cartaNaranja
//        CartaNaranja cartaN1 = CartaNaranja.buscarCartaId(5);
//        System.out.println(cartaN1.getDescripcion());
//        //Test buscar carta azul
//        CartaAzul profesion1 = CartaAzul.buscarCartaId(2);
//        System.out.println(profesion1.getTitulo());
        //test uso accion casilla azul
        int posicion = 85;
        Tablero t1 = new Tablero();
        System.out.println(t1.getCantCasillas());
        int nivel = t1.obtenerTercio(posicion);
        CartaAzul profesion = new CartaAzul();
        List<Integer> ids = profesion.obtenerRandom(nivel);
        for (int id: ids){
            System.out.println(id);
        }
        System.out.println(ids.get(1));
        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"

            // metodo que pide la cantidad de jugadores (min 1, max 4)
            //int cantJugadores = UserInput.getInt("Ingrese cantidad de jugadores ",1,4);

            // objeto principal donde se realiza la partida
            Juego juego = new Juego();

            // metodo donde se realiza la partida
            jugarDeNuevo = juego.jugar();

        } while (jugarDeNuevo);
        System.out.println("=== JUEGO TERMINADO ===");
    }

}
