package org.example.model.Piezas;

import org.example.model.Jugadores.Jugador;

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

    //Metodo para ver casillas intermedias entre posicion inicial y final, segun casillas hacer

    public void accion(Jugador jugador,int posicionInicial, int posicionFinal){
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
