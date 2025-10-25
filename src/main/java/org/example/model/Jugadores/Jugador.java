package org.example.model.Jugadores;

import org.example.model.NPCs.NPC;

import java.util.ArrayList;

public class Jugador {
    private int id_jugador;
    private String nombre;
    private Profesion profesion;
    private int patrimonio;
    private ArrayList<NPC> familia;
    private int posicion;
    private int estado_civil; //0/1 porque sqlite no tiene booleano
    private int hijos;
    private int deuda; //Puede pedirse prestamos al banco, aca se registran


    /**
     * Constructor de Jugador
     * @param id id del jugador
     * @param nombre nombre de jugador
     */
    public Jugador(int id, String nombre) {
        this.id_jugador = id;
        this.nombre = nombre;
        this.profesion = null;
        this.patrimonio = 0;
        this.familia = new ArrayList<NPC>();
        this.posicion = 0;
    }

    //constructor sin id
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.patrimonio = 0;
        this.posicion = 0;//Aca cambio a cero porque la tabla esta seteada para aceptar valore>=0
        this.estado_civil = 0;
        this.hijos = 0;
        this.deuda = 0;

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
        setPatrimonio(this.patrimonio + sueldo);
    }

    /**
     * Metodo que mustra los datos del jugador
     */
    public void mostrarJugador(){
        System.out.println("ID: " + getId() + " Nombre: " + getNombre() + " Patrimoio: " + getPatrimonio() );
    }

    public void mostrarPatrimonioJugador(){
        System.out.println(" Nombre: " + getNombre() + " Patrimoio: " + getPatrimonio() );
    }

    /**
     * Getters y Setters
     */
    public int getId() {return id_jugador;}
    public void setId(int id) {this.id_jugador = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public Profesion getProfesion() {return profesion;}
    public void setProfesion(Profesion profesion) {this.profesion = profesion;}

    public int getPatrimonio() {return patrimonio;}
    public void setPatrimonio(int patrimonio) {this.patrimonio = patrimonio;}

    public ArrayList<NPC> getFamilia() {return familia;}
    public void setFamilia(ArrayList<NPC> familia) {this.familia = familia;}

    public int getPosicion() {return posicion;}
    public void setPosicion(int posicion) {this.posicion = posicion;}

    public int getEstado_civil() { return estado_civil;}
    public void setEstado_civil(int estado_civil) {this.estado_civil = estado_civil;}

    public int getHijos() {return hijos;}
    public void setHijos(int hijos) {this.hijos = hijos;}

    public int getDeuda() {return deuda;}
    public void setDeuda(int deuda) {this.deuda = deuda;}
}
