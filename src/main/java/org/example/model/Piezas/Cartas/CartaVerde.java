package org.example.model.Piezas.Cartas;

import org.example.utils.PantallaColor;
import org.example.GUI.VentanaCarta;
import org.example.model.Jugadores.Jugador;
import org.example.model.Piezas.Carta;

public class CartaVerde extends Carta {
    @Override
    public void accion(Jugador jugador) {
        int patrimonioActual = jugador.getPatrimonio();
        int bono = patrimonioActual + 1000;

        VentanaCarta.mostrarCartaInformativa("Carta Verde", "Te tocó carta verde", "Cobra bono: " + 1000, PantallaColor.VERDE);
        jugador.actualizar("patrimonio", bono);
    }
}
