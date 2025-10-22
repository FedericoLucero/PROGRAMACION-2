package org.example.model.Piezas;

import org.example.model.Jugadores.Jugador;
import org.example.utils.*;
import java.util.ArrayList;


public class Tablero {

    private ArrayList<Casilla> casillas = new ArrayList<>();

    /**
     * Muestra el tablero en columna con colores según el tipo de casilla usando ConsolaColor.
     */
    public void mostrarTablero() {
        System.out.println("\n=== TABLERO ===\n");

        for (Casilla c : casillas) {
            String color = getColorConsola(c.getColor());
            System.out.println(color + String.format("[%03d: %s]", c.getId(), c.getColor().toUpperCase()) + ConsolaColor.RESET);
        }

        System.out.println("\n================\n");
    }

    /**
     * Devuelve el color correspondiente según la casilla usando ConsolaColor.
     */
    private String getColorConsola(String color) {
        return switch (color.toLowerCase()) {
            case "amarilla" -> ConsolaColor.AMARILLO;
            case "azul"     -> ConsolaColor.AZUL;
            case "roja"     -> ConsolaColor.ROJO;
            case "verde"    -> ConsolaColor.VERDE;
            case "rosa"     -> ConsolaColor.ROSA;
            case "stop"     -> ConsolaColor.GRIS;
            default         -> ConsolaColor.RESET;
        };
    }


    //Metodo para ver casillas intermedias entre posicion inicial y final, segun casillas hacer
    public void accion(Jugador jugador, int posicionInicial, int posicionFinal){
        //bucle que recorra las casillas del tablero entre pi y pf
        int contadorAmarillo = 0; //estos dos contadores son para probara futuro se remplazan con acciones dentro de c/caso
        int contadorVerde = 0;
        for (int i = posicionInicial ; i <= posicionFinal && i < casillas.size(); i++) {
            Casilla c = casillas.get(i);
            switch (c.getColor()) {
                case "amarilla":
                    contadorAmarillo += 1;
                    break;
                case "verde":
                    contadorVerde += 1;
                    break;
            }

        }
        System.out.println("amarillas: " + contadorAmarillo + " verdes: " + contadorVerde);
    }

    /**
     * Getters y Setters
     */
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }
    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }
}


