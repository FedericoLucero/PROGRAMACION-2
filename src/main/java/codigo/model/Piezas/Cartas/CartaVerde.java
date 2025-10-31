package codigo.model.Piezas.Cartas;

import codigo.utils.PantallaColor;
import codigo.GUI.VentanaCarta;
import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Carta;

public class CartaVerde extends Carta {

    private int bono = 1000;

    // ==========================
    // METODOS PROPIOS
    // ==========================

    @Override
    public void accion(Jugador jugador) {
        int patrimonioActual = jugador.getPatrimonio();
        int bono = patrimonioActual + this.bono;

        VentanaCarta.mostrarCartaInformativa("Carta Verde", "Te tocó carta verde", "Cobra bono: " + 1000, PantallaColor.VERDE);
        jugador.actualizar("patrimonio", bono);
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getBono() {return bono;}
    public void setBono(int bono) {this.bono = bono;}
}
