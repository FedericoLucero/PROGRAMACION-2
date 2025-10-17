package org.example.model.Piezas;

import java.util.ArrayList;

public class Tablero {
    private ArrayList<Casilla> casillas;

    /**
     * Constructor de tablero
     * @param casillas ArrayList de casillas
     */
    public Tablero(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    /**
     * Metodo para mostrar todas las casillas del tablero
     */
    public void mostrarTablero() {
        System.out.println("═══════════════════════════════════");
        System.out.println("          TABLERO DE JUEGO          ");
        System.out.println("═══════════════════════════════════");
        for (Casilla c : casillas) {
            System.out.println("Casilla " + c.getId() + " | Color: " + c.getColor());
        }
        System.out.println("═══════════════════════════════════\n");
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
