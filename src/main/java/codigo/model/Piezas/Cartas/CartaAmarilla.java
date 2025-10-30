package codigo.model.Piezas.Cartas;

import codigo.GUI.VentanaCarta;
import codigo.model.Piezas.Tablero;
import codigo.utils.PantallaColor;
import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Carta;
import codigo.GUI.VentanaJuego; // si lo usas
import codigo.model.Piezas.Ruleta; // si lo usas

public class CartaAmarilla extends Carta {

    private Tablero tablero;
    private Ruleta ruleta;
    private VentanaJuego ventanaJuego;

    public CartaAmarilla(Tablero tablero, Ruleta ruleta, VentanaJuego ventanaJuego) {
        this.tablero = tablero;
        this.ruleta = ruleta;
        this.ventanaJuego = ventanaJuego;
    }

    @Override
    public void accion(Jugador jugadorTurno) {

        int nivel = tablero.obtenerTercio(jugadorTurno.getPosicion());

        VentanaCarta.mostrarCartaInformativa("Carta Amarilla", "Te tocó carta amarilla", "Debes girar la ruleta (1 a 5)", PantallaColor.AMARILLO);

        int resultado = (ruleta.girarRuleta(ventanaJuego) % 5 ) + 1;
        System.out.println(resultado);
        Carta carta;
        switch (resultado) {

            case 1 -> carta = new CartaAzul(nivel);
            case 2 -> carta = new CartaRoja();
            case 3 -> carta = new CartaVerde();
            case 4 -> carta = new CartaRosa();
            case 5 -> carta = new CartaNaranja(nivel);
            default -> carta = null;
        }

        if (carta != null) {
            carta.accion(jugadorTurno); // Polimorfismo
        }
    }
}
