package org.example.model.Piezas;

import org.example.GUI.VentanaJuego;

public class Ruleta {

    public Ruleta() {}

    public int girarRuleta(VentanaJuego ventanaJuego) {
        return ventanaJuego.esperarResultadoRuleta();
    }
}
