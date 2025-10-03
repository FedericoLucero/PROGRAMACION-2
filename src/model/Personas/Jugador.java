package model.Personas;
import model.Personas.Caracteristicas.*;

import java.util.ArrayList;

public class Jugador extends Persona {
    private Profesion profesion;
    private int patrimoio;
    private ArrayList<Personaje> familia;
    private int posicion;

    public Jugador(String id, String nombre) {
        super(id, nombre);
        this.profesion = null;
        this.patrimoio = 0;
        this.familia = new ArrayList<Personaje>();
        this.posicion = 0;

    }


    public Profesion getProfesion() {return profesion;}
    public void setProfesion(Profesion profesion) {this.profesion = profesion;}

    public int getPatrimoio() {return patrimoio;}
    public void setPatrimoio(int patrimoio) {this.patrimoio = patrimoio;}

    public ArrayList<Personaje> getFamilia() {return familia;}
    public void setFamilia(ArrayList<Personaje> familia) {this.familia = familia;}

    public int getPosicion() {return posicion;}
    public void setPosicion(int posicion) {this.posicion = posicion;}


}
