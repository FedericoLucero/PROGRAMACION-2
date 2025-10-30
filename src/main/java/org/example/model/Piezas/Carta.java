package org.example.model.Piezas;

import org.example.model.Jugadores.Jugador;

public abstract class Carta {
    private int id;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public void agregarDeuda(Jugador jugador, int monto) {
        int deudaActual = jugador.getDeuda();
        int nuevaDeuda = deudaActual + monto;
        jugador.actualizar("deudas", nuevaDeuda);
    }
}

