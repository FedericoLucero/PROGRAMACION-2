package model;

import model.Personas.*;
import java.util.Scanner;

public class Juego {
    private Jugador[] jugadores;

    public Juego() {
        this.jugadores = new Jugador[2]; // espacio para 2 jugadores
    }

    public boolean jugar() {
        System.out.println("=== Comienza el juego ===");

        /// juego

        return false;
    }

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}
}
