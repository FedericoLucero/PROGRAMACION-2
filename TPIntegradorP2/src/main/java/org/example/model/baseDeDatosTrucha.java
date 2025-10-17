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

        Casilla casilla1 = new Casilla(1, "amarilla");
        Casilla casilla2 = new Casilla(2, "azul");
        Casilla casilla3 = new Casilla(3, "roja");
        Casilla casilla4 = new Casilla(4, "verde");
        Casilla casilla5 = new Casilla(5, "amarilla");
        Casilla casilla6 = new Casilla(6, "azul");
        Casilla casilla7 = new Casilla(7, "roja");
        Casilla casilla8 = new Casilla(8, "verde");
        Casilla casilla9 = new Casilla(9, "amarilla");
        Casilla casilla10 = new Casilla(10, "azul");
        Casilla casilla11 = new Casilla(11, "roja");
        Casilla casilla12 = new Casilla(12, "verde");
        Casilla casilla13 = new Casilla(13, "amarilla");
        Casilla casilla14 = new Casilla(14, "azul");
        Casilla casilla15 = new Casilla(15, "roja");
        Casilla casilla16 = new Casilla(16, "verde");
        Casilla casilla17 = new Casilla(17, "amarilla");
        Casilla casilla18 = new Casilla(18, "azul");
        Casilla casilla19 = new Casilla(19, "roja");
        Casilla casilla20 = new Casilla(20, "verde");

        casillas = new ArrayList<>();
        casillas.add(casilla1);
        casillas.add(casilla2);
        casillas.add(casilla3);
        casillas.add(casilla4);
        casillas.add(casilla5);
        casillas.add(casilla6);
        casillas.add(casilla7);
        casillas.add(casilla8);
        casillas.add(casilla9);
        casillas.add(casilla10);
        casillas.add(casilla11);
        casillas.add(casilla12);
        casillas.add(casilla13);
        casillas.add(casilla14);
        casillas.add(casilla15);
        casillas.add(casilla16);
        casillas.add(casilla17);
        casillas.add(casilla18);
        casillas.add(casilla19);
        casillas.add(casilla20);


        this.tablero = new Tablero(casillas);
    }

    // 🔹 Muestra los jugadores con formato
    public void mostrarDatos() {
        System.out.println("═══════════════════════════════════");
        System.out.println("         LISTADO DE JUGADORES       ");
        System.out.println("═══════════════════════════════════");
        for (Jugador j : jugadores) {
            System.out.println("ID: " + j.getId() + " | Nombre: " + j.getNombre());
        }
        System.out.println("═══════════════════════════════════\n");
    }

    // 🔹 Muestra las casillas con formato similar
    public void mostrarCasillas() {
        System.out.println("═══════════════════════════════════");
        System.out.println("          LISTADO DE CASILLAS       ");
        System.out.println("═══════════════════════════════════");
        for (Casilla c : casillas) {
            System.out.println("ID: " + c.getId() + " | Color: " + c.getColor());
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
