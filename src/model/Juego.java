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
        for (Jugador j : jugadores) {
            System.out.println("Jugador: " + j.getNombre());
        }
        return false;
    }

    // Método que pide datos al usuario
    public void cargarJugadores() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < jugadores.length; i++) {
            System.out.print("Ingrese ID del jugador " + (i + 1) + ": ");
            String id = sc.nextLine();

            System.out.print("Ingrese nombre del jugador " + (i + 1) + ": ");
            String nombre = sc.nextLine();

            jugadores[i] = new Jugador(id, nombre);
        }
    }

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}
}
