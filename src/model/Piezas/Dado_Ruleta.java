package model.Piezas;
import java.util.Random;

public class Dado_Ruleta {
    private Random random;

    public Dado_Ruleta() {
        random = new Random();
    }

    public int tirar() {
        return random.nextInt(6) + 1; // genera valores del 1 al 6
    }
}
