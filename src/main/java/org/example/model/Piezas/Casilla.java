package org.example.model.Piezas;

import org.example.utils.ConsolaColor;

public class Casilla {
    private int id;
    private String color;

    /**
     * Constructor de Casilla
     * @param id id de la casilla
     * @param color color de la casilla
     */
    public Casilla(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public void mostrarCasilla() {
        String colorTexto;

        switch (color.toLowerCase()) {
            case "amarilla":
                colorTexto = ConsolaColor.AMARILLO;
                break;
            case "azul":
                colorTexto = ConsolaColor.AZUL;
                break;
            case "roja":
                colorTexto = ConsolaColor.ROJO;
                break;
            case "verde":
                colorTexto = ConsolaColor.VERDE;
                break;
            case "rosa":
                colorTexto = ConsolaColor.ROSA;
                break;
            case "stop":
                colorTexto = ConsolaColor.GRIS;
                break;
            default:
                colorTexto = ConsolaColor.RESET;
                break;
        }

        System.out.println(colorTexto + "Casilla: " + id + " | Color: " + color + ConsolaColor.RESET);
    }

    /**
     * Getters y Setters
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
