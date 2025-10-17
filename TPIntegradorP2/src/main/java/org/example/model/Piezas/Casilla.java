package org.example.model.Piezas;

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
