package org.example.model;

import static org.example.utils.ConsolaColor.*;

import org.example.model.Jugadores.*;
import org.example.model.Piezas.*;
import org.example.utils.ConsolaColor;
import org.example.ui.UserInput;

import java.sql.*;

public class Juego {

    private Jugador[] jugadores;
    private Ruleta ruleta = new Ruleta();
    private Tablero tablero;

    /**
     *  Constructor Juego
     */
    public Juego(int cantJugadores) {
        this.jugadores = new Jugador[cantJugadores]; // espacio para x jugadores maximo
    }

    /**
     * Metodo donde se realiza tod0 el juego
     * @return true volver a jugar o false no volver a jugar
     */
    public boolean jugar() {

        inicializar(); // metodo que inicializa el juego con la base de datos
        pedirDatosJugadores(); // metodo que pide los nombres a los jugadores

        tablero.mostrarTablero(); // metodo que muestra el tablero

        System.out.println("=== Comienza el juego ===");

        int cantJugadas = 0;  // cantidad de movimientos actuales
        boolean seguirJugando = true;  // variable para finalizar el bucle
        do { // bucle principal del juego

            Jugador jugadorTurno = cambiarJugador(cantJugadas); // cambia el jugador

            int posicion = RecorrerHastaSiguientePosicion(jugadorTurno.getPosicion(),girarRuleta()); //metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
            moverJugador(jugadorTurno, posicion); // metodo que mueve al jugador a la posicion calculada

            int idCasilla = tablero.getCasillas().get(posicion).getId(); // pedimos el id de la casilla para mostrarlo (id = index + 1) (index del arraylist)
            switch (tablero.getCasillas().get(posicion).getColor()) {

                case "amarilla":
                    System.out.println(AMARILLO + "Caiste en una casilla amarilla: POS: " + idCasilla + RESET);
                    // acción random
                    break;

                case "azul":
                    System.out.println(ConsolaColor.AZUL + "Caiste en una casilla azul: POS: " + idCasilla + ConsolaColor.RESET);
                    // accion de cambiar profesion
                    break;

                case "roja":
                    System.out.println(ROJO + "Caiste en una casilla roja: POS: " + idCasilla + RESET);
                    // acción de pagar
                    break;

                case "verde":
                    System.out.println(VERDE + "Caiste en una casilla verde: POS: " + idCasilla + RESET);
                    // acción de cobro
                    break;

                case "rosa":
                    System.out.println(ROSA + "Caiste en una casilla rosa: POS: " + idCasilla + RESET);
                    // acción de familia
                    break;

                case "stop":
                    System.out.println(GRIS + "Caiste en una casilla stop: POS: " + idCasilla + RESET);
                    // acción especial
                    break;

                default:
                    System.out.println(GRIS + "Color no reconocido: POS: " + idCasilla + RESET);
                    break;
            }


            if (cantJugadas > 10){ /// todo ⚠️ verificar el fin real del juego ⚠️
                seguirJugando = false;
            }

            cantJugadas = cantJugadas + 1;
        } while (seguirJugando);

        return false; // true volver a jugar o false no volver a jugar
    }


    /**
     * Metodo que inicializa el juego con la base de datos
     */
    public void inicializar() {
        tablero = new Tablero(); // crear instancia de tablero
        String url = "jdbc:sqlite:BD_LIFE_STATIC.sqlite";
        String query = "SELECT id_casilla, color FROM casillas ORDER BY id_casilla;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            /// ⚠️⚠️ esto inicializa el objeto tablero recorriendo la bd
            while (rs.next()) { /// ⚠️⚠️ todo abria que trabajar solo sobre la bd ⚠️⚠️
                int id = rs.getInt("id_casilla");
                String color = rs.getString("color");
                tablero.getCasillas().add(new Casilla(id, color));
            }

            System.out.println("Tablero inicializado con " + tablero.getCasillas().size() + " casillas.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar el tablero: " + e.getMessage());
        }
    }


    /**
     * Solicita los nombres de los jugadores y los guarda en el arreglo
     * Usa la clase {UserInput} para leer los nombres desde consola,
     */
    public void pedirDatosJugadores() {
        UserInput UI = new UserInput();
        for (int i = 1; i <= jugadores.length; i++) {
            Jugador jugador = new Jugador(i, UI.getString("Ingrese nombre del jugador " + i + " ", 1, 10)); // validando que la longitud sea entre 1 y 10 caracteres.
            jugadores[i - 1] = jugador;
        }
    }

    /**
     * Cambia el turno al jugador correspondiente según el número de movimiento actual.
     *
     * @param cantMovActual número total de movimientos realizados hasta el momento.
     * @return el {Jugador} que tiene el turno actual.
     */
    public Jugador cambiarJugador(int cantMovActual) {
        Jugador jugadorTurno = jugadores[cantMovActual % jugadores.length]; // cambia el jugador

        System.out.print("Turno del jugador con ");
        jugadorTurno.mostrarJugador(); // muestra Id y Nombre

        return jugadorTurno;
    }

    /**
     * Simula el giro de la ruleta e imprime el resultado obtenido.
     * @return el número de posiciones que el jugador deberá moverse en el tablero.
     */
    public int girarRuleta() {
        System.out.println("¡Gira la ruleta!");
        int cantPosMover = ruleta.girarConGUI();
        System.out.println("🎲 = " + cantPosMover);
        return cantPosMover;
    }

    /**
     * Recorre el tablero desde una posición inicial hasta la siguiente,
     * deteniéndose si encuentra una casilla con color "stop" o si llega al final del tablero.
     *
     * @param posicionAnterior posición actual del jugador antes de moverse.
     * @param numRuleta número de pasos que indica la ruleta.
     * @return la nueva posición final del jugador después de avanzar.
     */
    public int RecorrerHastaSiguientePosicion(int posicionAnterior, int numRuleta) {
        int i = posicionAnterior;

        for (int pasos = 0; pasos < numRuleta; pasos++) {
            i++;

            if (i >= tablero.getCasillas().size()) {
                System.out.println("Te saliste del tablero. Volvés al final.");
                return tablero.getCasillas().size() - 1;
            }

            Casilla c = tablero.getCasillas().get(i);
            c.mostrarCasilla();

            if (c.getColor().equals("stop")) {
                return i;
            }
        }
        return i;
    }

    /**
     * Mueve al jugador a una nueva posición en el tablero.
     *
     * @param jugadorTurno el jugador que se va a mover.
     * @param posicionSiguiente la posición destino del jugador.
     */
    public void moverJugador(Jugador jugadorTurno, int posicionSiguiente) {
        jugadorTurno.setPosicion(posicionSiguiente);
    }

    /**
     * Getters y Setters
     */
    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

    public Tablero getTablero() {return tablero;}
    public void setTablero(Tablero tablero) {this.tablero = tablero;}

}
