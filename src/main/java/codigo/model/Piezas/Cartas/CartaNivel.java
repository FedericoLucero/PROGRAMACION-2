package codigo.model.Piezas.Cartas;

import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Carta;

public class CartaNivel extends Carta {

    private int nivel;

    public CartaNivel() {}

    public CartaNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    @Override
    public void accion(Jugador jugador) {}
}
