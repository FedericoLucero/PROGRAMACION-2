package codigo.model.Piezas.Cartas;

import codigo.utils.PantallaColor;
import codigo.GUI.VentanaCarta;
import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Carta;

public class CartaVerde extends Carta {
    @Override
    public void accion(Jugador jugador) {
        int patrimonioActual = jugador.getPatrimonio();
        int bono = patrimonioActual + 1000;

        VentanaCarta.mostrarCartaInformativa("Carta Verde", "Te tocó carta verde", "Cobra bono: " + 1000, PantallaColor.VERDE);
        jugador.actualizar("patrimonio", bono);
    }
}
