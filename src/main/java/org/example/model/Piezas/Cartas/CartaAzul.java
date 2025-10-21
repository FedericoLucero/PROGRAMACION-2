package org.example.model.Piezas.Cartas;

import org.example.model.Jugadores.Jugador;
import org.example.model.Jugadores.Profesion;

public class CartaAzul {
    private Profesion profesion;

    public CartaAzul(Profesion profesion) {
        this.profesion = profesion;
    }

    public void cambiarProfesion(Jugador jugador) {

        jugador.setProfesion(this.profesion);
    }

}
