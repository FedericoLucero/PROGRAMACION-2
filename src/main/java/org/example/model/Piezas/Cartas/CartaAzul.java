package org.example.model.Piezas.Cartas;

import org.example.model.Jugadores.Jugador;
import org.example.model.Jugadores.Profesion;

public class CartaAzul {
    private int id;
    private int nivel;
    private String descripcion;
    private Profesion profesion;

    public CartaAzul(int id, int nivel, String descripcion, Profesion profesion) {
        this.id = id;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.profesion = profesion;
    }

    public void cambiarProfesion(Jugador jugador) {
        jugador.setProfesion(this.profesion);
    }

    /**
     * Getters y Setters
     */
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getNivel() {return nivel;}
    public void setNivel(int nivel) {this.nivel = nivel;}

    public Profesion getProfesion() {return profesion;}
    public void setProfesion(Profesion profesion) {this.profesion = profesion;}

    public String getTitulo() { return profesion.getTitulo(); }
}
