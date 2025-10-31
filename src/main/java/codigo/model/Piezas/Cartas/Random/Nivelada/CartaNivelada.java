package codigo.model.Piezas.Cartas.Random.Nivelada;

import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Cartas.Random.CartaRandom;

public abstract class CartaNivelada extends CartaRandom {

    private int nivel;

    public CartaNivelada() {}

    public CartaNivelada(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    @Override
    public void accion(Jugador jugador) {}
}
