import model.*;
import utils.Conexion;

public class Main {

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        conexion.establecerConexion();  // conexion con bd

        /// to do trabajar con la base..

        boolean jugarDeNuevo;
        do {    // bucle "voler a jugar"
            Juego juego = new Juego(); // clase principal donde se realiza el juego
            jugarDeNuevo = juego.jugar(); // jugar el juego

        } while (jugarDeNuevo);
        System.out.println("JUEGO TERMINADO");

        conexion.cerrarConexion(); // desconexion con bd

    }
}