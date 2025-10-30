package codigo.model.Piezas;

import codigo.GUI.VentanaJuego;

public class Ruleta {

    public Ruleta() {}

    public int girarRuleta(VentanaJuego ventanaJuego) {
        return ventanaJuego.esperarResultadoRuleta();
    }
}
