package org.example.model.Jugadores;

import org.example.model.NPCs.NPC;
import java.util.ArrayList;

public class Jugador {
    private int id;
    private String nombre;
    private Profesion profesion;
    private int patrimoio;
    private ArrayList<NPC> familia;
    private int posicion;

    /**
     * Costructor de Jugador
     * @param id id del jugador
     * @param nombre nombre de jugador
     */
    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.profesion = new Profesion();
        this.patrimoio = 0;
        this.familia = new ArrayList<NPC>();
        this.posicion = -1;

    }

    /**
     * Mueve al jugador a una nueva posición en el tablero.
     *
     * @param posicionSiguiente la posición destino del jugador.
     */
    public void moverJugador(int posicionSiguiente) {
        setPosicion(posicionSiguiente);
    }

    public void actualizarPatrimonio(int sueldo) {
        setPatrimoio(this.patrimoio + sueldo);
    }

    /**
     * Metodo que mustra los datos del jugador
     */
    public void mostrarJugador(){
        System.out.println("ID: " + getId() + " Nombre: " + getNombre() + " Patrimoio: " + getPatrimoio() );
    }

    public void mostrarPatrimonioJugador(){
        System.out.println(" Nombre: " + getNombre() + " Patrimoio: " + getPatrimoio() );
    }

    /**
     * Getters y Setters
     */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public Profesion getProfesion() {return profesion;}
    public void setProfesion(Profesion profesion) {this.profesion = profesion;}

    public int getPatrimoio() {return patrimoio;}
    public void setPatrimoio(int patrimoio) {this.patrimoio = patrimoio;}

    public ArrayList<NPC> getFamilia() {return familia;}
    public void setFamilia(ArrayList<NPC> familia) {this.familia = familia;}

    public int getPosicion() {return posicion;}
    public void setPosicion(int posicion) {this.posicion = posicion;}


}
