package model.Jugadores;
import model.NPCs.NPC;

import java.util.ArrayList;

public class Jugador {
    private int id;
    private String nombre;
    private Profesion profesion;
    private int patrimoio;
    private ArrayList<NPC> familia;
    private int posicion;

    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.profesion = null;
        this.patrimoio = 0;
        this.familia = new ArrayList<NPC>();
        this.posicion = 0;

    }

    public void mostrarJugador(){
        System.out.println("ID: " + getId() + " Nombre: " + getNombre() );
    }

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
