import model.*;
import model.Personas.*;
import utils.Conexion;

public class Main {

    public static void main(String[] args) {

        Conexion conexion = new Conexion(); // conexion con bd
        conexion.establecerConexion();


        /// to do trabajar con la base..


        boolean playAgain;
        do {    // bucle principal del juego
            Juego game = new Juego();
            playAgain = game.jugar();

        } while (playAgain);
        System.out.println("JUEGO TERMINADO");

        conexion.cerrarConexion(); // desconexion con bd
        System.exit(0); //cierra el sistema

    }
}