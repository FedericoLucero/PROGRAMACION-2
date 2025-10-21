package org.example.model;

import org.example.model.Jugadores.Jugador;
import org.example.model.Piezas.Casilla;
import org.example.model.Piezas.Tablero;

import java.util.ArrayList;

public class baseDeDatosTrucha {

    private Jugador[] jugadores;
    private ArrayList<Casilla> casillas;
    private Tablero tablero;

    public baseDeDatosTrucha() {
        Jugador jugador1 = new Jugador(1, "fede");
        Jugador jugador2 = new Jugador(2, "bian");
        Jugador jugador3 = new Jugador(3, "pepe");
        jugadores = new Jugador[]{jugador1, jugador2, jugador3};

        casillas = new ArrayList<>();
        String[] colores = {"amarilla", "azul", "roja", "verde"};

        for (int i = 0; i <= 99; i++) {
            String color = colores[i % colores.length];
            Casilla casilla = new Casilla(i + 1, color);
            casillas.add(casilla);
        }

        //  elegís qué casillas querés que sean "stop"
        int[] indicesStop = {4, 10, 25, 50, 75}; // elegí los índices que quieras

        for (int indice : indicesStop) {
            if (indice >= 0 && indice < casillas.size()) {
                casillas.get(indice + 1).setColor("stop");
            }
        }

        this.tablero = new Tablero(casillas);
    }

    //  Muestra los jugadores con formato
    public void mostrarJugadores() {
        System.out.println("═══════════════════════════════════");
        System.out.println("         LISTADO DE JUGADORES       ");
        System.out.println("═══════════════════════════════════");
        for (Jugador j : jugadores) {
            System.out.println("ID: " + j.getId() + " | Nombre: " + j.getNombre());
        }
        System.out.println("═══════════════════════════════════\n");
    }



    public Jugador[] getJugadores() {
        return jugadores;
    }
    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
