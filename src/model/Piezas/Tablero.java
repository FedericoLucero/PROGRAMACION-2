package model.Piezas;

import java.util.ArrayList;

public class Tablero {
    private ArrayList<Casilla> casillas;

    public Tablero(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    public void mostrarTablero() {
        System.out.println("═══════════════════════════════════");
        System.out.println("          TABLERO DE JUEGO          ");
        System.out.println("═══════════════════════════════════");
        for (Casilla c : casillas) {
            System.out.println("Casilla " + c.getId() + " | Color: " + c.getColor());
        }
        System.out.println("═══════════════════════════════════\n");
    }

    public ArrayList<Casilla> getCasillas() {return casillas;}
    public void setCasillas(ArrayList<Casilla> casillas) {this.casillas = casillas;}
}
