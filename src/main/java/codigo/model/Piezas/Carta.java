package codigo.model.Piezas;

import codigo.model.Jugadores.Jugador;

public abstract class Carta {
    private int id;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Mtodo polimórfico (todas las cartas lo redefinen)
    public abstract void accion(Jugador jugador);

    // Sobrecargado (solo algunas cartas usarán)
    public void accion(Jugador jugador, int nivel) {
        accion(jugador);
    }
}