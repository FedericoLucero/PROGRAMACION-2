package codigo.model.Piezas;

import codigo.model.Jugadores.Jugador;

public abstract class Carta {
    private int id;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // ==========================
    // METODOS PROPIOS
    // ==========================

    // Metodo polimórfico (todas las cartas lo redefinen)
    public abstract void accion(Jugador jugador);

    // Sobrecargado
    public void accion(Jugador jugador, int nivel) {}
}