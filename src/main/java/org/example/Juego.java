package org.example;

import static org.example.utils.ConsolaColor.*;

import org.example.GUI.VentanaJuego;
import org.example.dao.TableroDAO;
import org.example.model.Jugadores.*;
import org.example.utils.ConsolaColor;
import org.example.ui.UserInput;

public class Juego {

    private Jugador[] jugadores;
    // private Ruleta ruleta = new Ruleta();
    private TableroDAO tableroDAO = new TableroDAO();

    /**
     *  Constructor Juego
     */
    public Juego(int cantJugadores) {
        this.jugadores = new Jugador[cantJugadores]; // todo deberiamos pasar solo el array de jugadores, de ahi sacamos la cantidad de jugadores e inicializamos todo ??

    }

    /**
     * Metodo donde se realiza tod0 el juego
     * @return true volver a jugar o false no volver a jugar
     */
    public boolean jugar() {

        // metodo que pide los nombres a los jugadores
        pedirDatosJugadores();

        // metodo que muestra el tablero en consola
        tableroDAO.mostrarTablero();

        System.out.println("=== Comienza el juego ===");
        VentanaJuego ventanaJuego = new VentanaJuego();

        // metodo que retorna la cantidad de casillas del tablero
        int cantCasillas = tableroDAO.contarCasillas(); // todo deberia ser un atributo de la clase tablero ??

        // variable de cantidad de movimientos actuales
        int cantJugadas = 0;

        // variable para finalizar el bucle
        boolean seguirPartida = true;

        // bucle principal de la partida
        do {

            // metodo que cambia el jugador
            Jugador jugadorTurno = cambiarJugador(cantJugadas);

            // metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
            int posicion = tableroDAO.recorrerHastaSiguientePosicion(jugadorTurno,girarRuleta(ventanaJuego));

            // if que verifica que el jugador no esté en el final
            if (posicion != cantCasillas){

                // metodo que mueve al jugador a la posicion calculada previamente
                jugadorTurno.moverJugador(posicion);

                // switch con metodo que retorna el color de la casilla
                switch (tableroDAO.casillaDAO.obtenerCasillaPorId(posicion).getColor()) {

                    case "amarilla":
                        System.out.println(AMARILLO + "Caiste en una casilla amarilla: Posición: " + posicion + RESET);
                        // todo acción random (quizas girar ruleta y que te pueda tocar cualquier carta)

                        break;

                    case "azul":
                        System.out.println(AZUL + "Caiste en una casilla azul: Posición: " + posicion + ConsolaColor.RESET);
                        // todo accion de elegir entre dos profesiones de un mismo nivel (podria ser que primero te de nivel 1, luego nievl 2... creciendo)

                        break;

                    case "roja":
                        System.out.println(ROJO + "Caiste en una casilla roja: Posición: " + posicion + RESET);
                        // todo acción de pagar impuesto

                        break;

                    case "verde":
                        System.out.println(VERDE + "Caiste en una casilla verde: Posición: " + posicion + RESET);
                        // todo acción de cobrar bono
                        // todo ya se cobro el sueldo normal cada vez que se paso por "arriba" de una casilla verde

                        break;

                    case "rosa":
                        System.out.println(ROSA + "Caiste en una casilla rosa: Posición: " + posicion + RESET);
                        // todo acción de que te toque familia
                        // todo falta crear casillas rosas en la bd

                        break;

                    case "naranja":
                        // todo no se si esta la casilla naranaj en el juego original

                        System.out.println(NARANJA + "Caiste en una casilla naranja: Posición: " + posicion + RESET);
                        // todo accion de elegir entre dos casas de un mismo nivel (podria ser que primero te de nivel 1, luego nievl 2... creciendo)
                        break;


                    case "stop":
                        System.out.println(GRIS + "Caiste en una casilla stop: Posición: " + posicion + RESET);
                        // todo acción especial (las casiilas de stop son siempre fijas en el tablero)
                        // todo por ejemplo casilla 5 siempre te obliga a elegir una profesion
                        // todo por ejemplo casilla 11 simpre te hace girar la ruleta con solo dos opciones (cobrar/pagar)

                        if (posicion==5){
                            jugadorTurno.setProfesion(new Profesion("ingeEjemplo",100)); //profesion de prueba
                            jugadorTurno.getProfesion().mostrarProfesion();
                        }

                        break;

                    default:
                        System.out.println(GRIS + "Color no reconocido: Posición: " + posicion + RESET);
                        break;
                }
            }

            // if que verifica el final de la partida
            // todo verificar el fin real del juego (cuando todos los jugadores llegan a la meta)
            if (cantJugadas > 10){

                // todo verificar ganador de la partida

                seguirPartida = false;
            }

            cantJugadas += 1;
        } while (seguirPartida);

        // cierra la ventanaJuego del juego
        ventanaJuego.dispose();

        // retornamos true si queremo seguir jugando o false si no queremos seguir jugando
        return UserInput.getBolean("¿DESEA JUGAR DE NUEVO?\n1. si\n2. no");
    }

    /**
     * Solicita los nombres de los jugadores y los guarda en el arreglo
     * Usa la clase {UserInput} para leer los nombres desde consola,
     */
    public void pedirDatosJugadores() {
        for (int i = 1; i <= jugadores.length; i++) {
            Jugador jugador = new Jugador(i, UserInput.getString("Ingrese nombre del jugador " + i + " ", 1, 10)); // validando que la longitud sea entre 1 y 10 caracteres.
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
    public int girarRuleta( VentanaJuego ventanaJuego) {
        return ventanaJuego.girarRuletaSync();
    }

        /*
    public int girarRuleta() {
        System.out.println("¡Gira la ruleta!");

        // metodo que abre una ventana con una ruleta
        int cantPosMover = ruleta.girarConGUI();

        System.out.println("🎲 = " + cantPosMover);
        return cantPosMover;
    }

     */


    /**
     * Getters y Setters
     */
    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

}
