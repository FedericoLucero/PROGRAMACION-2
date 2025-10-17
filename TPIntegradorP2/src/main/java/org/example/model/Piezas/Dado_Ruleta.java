package org.example.model.Piezas;
import java.util.Random;

public class Dado_Ruleta {
    private Random random;

    /**
     * Constructor de Dado_Ruleta
     */
    public Dado_Ruleta() {
        random = new Random();
    }

    /**
     * Metodo que tira los dados o gira la ruleta
     * @return un numero entero entre 1 y 6
     */
    public int tirar() {
        return random.nextInt(6) + 1; // genera valores del 1 al 6
    }
}
