package org.example;

import static org.example.GUI.VentanaJuego.pedirCantidadJugadores;
import static org.example.GUI.VentanaJuego.pedirNombresJugadores;
import static org.example.utils.ConsolaColor.*;

import org.example.GUI.VentanaJuego;
import org.example.model.Jugadores.*;
import org.example.model.Piezas.Casilla;
import org.example.model.Piezas.Tablero;
import org.example.utils.ConsolaColor;
import org.example.utils.PantallaColor;
import org.example.utils.UserInput;

import java.awt.*;

public class Juego {

    private Jugador[] jugadores;
    private Tablero tablero = new Tablero();

    private int cantJugadores;
    private int cantMovimientosActuales = 0;
    private VentanaJuego ventanaJuego;

    // ==========================
    // CONSTRUCTORES
    // ==========================

    public Juego() {}

    // ==========================
    // MÉTODOS LÓGICOS
    // ==========================

    /**
     * Metodo donde se realiza tod0 el juego
     * @return true volver a jugar o false no volver a jugar
     */
    public boolean jugar() {

        inicializar(); // inicializa atributos del juego

        // variable para finalizar el bucle
        boolean seguirPartida = true;
        do { // bucle principal de la partida

            // metodo que cambia el jugador
            Jugador jugadorTurno = cambiarJugador(cantMovimientosActuales);

            // metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
            int posicion = tablero.recorrerHastaSiguientePosicion(jugadorTurno,girarRuleta());

            // if que verifica que el jugador no esté en el final
            if (posicion != tablero.getCantCasillas()){

                // metodo que mueve al jugador a la posicion calculada previamente
                jugadorTurno.moverJugador(posicion);

                // switch con metodo que retorna el color de la casilla
                switch (Casilla.obtenerColorCasillaPorId(posicion)) {

                    case "amarilla":
                        ventanaJuego.setDescripcion("Caiste en una casilla AMARILLO"," Posición: " + posicion , PantallaColor.AMARILLO);
                        // todo acción random (quizas girar ruleta y que te pueda tocar cualquier carta)

                        break;

                    case "azul":
                        ventanaJuego.setDescripcion("Caiste en una casilla AZUL"," Posición: "+ posicion, PantallaColor.AZUL);
                        // todo accion de elegir entre dos profesiones de un mismo nivel (podria ser que primero te de nivel 1, luego nievl 2... creciendo)

                        break;

                    case "roja":
                        ventanaJuego.setDescripcion("Caiste en una casilla ROJA"," Posición: "+ posicion, PantallaColor.ROJO);
                        // todo acción de pagar impuesto

                        break;

                    case "verde":
                        ventanaJuego.setDescripcion("Caiste en una casilla VERDE"," Posición: "+ posicion, PantallaColor.VERDE);
                        // todo acción de cobrar bono
                        // todo ya se cobro el sueldo normal cada vez que se paso por "arriba" de una casilla verde

                        break;

                    case "rosa":
                        ventanaJuego.setDescripcion("Caiste en una casilla ROSA"," Posición: " + posicion, PantallaColor.ROSA);
                        // todo acción de que te toque familia
                        // todo falta crear casillas rosas en la bd

                        break;

                    case "naranja":
                        ventanaJuego.setDescripcion("Caiste en una casilla NARANJA"," Posición: " + posicion, PantallaColor.NARANJA);

                        // todo no se si esta la casilla naranaj en el juego original
                        // todo accion de elegir entre dos casas de un mismo nivel (podria ser que primero te de nivel 1, luego nievl 2... creciendo)
                        break;


                    case "stop":
                        ventanaJuego.setDescripcion("Caiste en una casilla STOP"," Posición: " + posicion, PantallaColor.BLANCO);

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

            ventanaJuego.actualizarValoresJugador(jugadorTurno.getId(),jugadorTurno.getPosicion(), jugadorTurno.getPatrimonio(), jugadorTurno.getCantidadCasas(), jugadorTurno.getCantidadFamiliares());

            // if que verifica el final de la partida
            // todo verificar el fin real del juego (cuando todos los jugadores llegan a la meta)
            if (cantMovimientosActuales > 20){

                // todo verificar ganador de la partida

                seguirPartida = false;
            }

            cantMovimientosActuales += 1;
        } while (seguirPartida);

        // cierra la ventanaJuego del juego
        ventanaJuego.dispose();

        // retornamos true si queremo seguir jugando o false si no queremos seguir jugando
        return UserInput.getBolean("¿DESEA JUGAR DE NUEVO?\n1. si\n2. no");
    }

    public void inicializar(){
        this.cantJugadores = pedirCantidadJugadores();
        this.jugadores = pedirNombresJugadores(cantJugadores);
        this.ventanaJuego = new VentanaJuego(jugadores);
    }

    /**
     * Cambia el turno al jugador correspondiente según el número de movimiento actual.
     * @param cantMovActual número total de movimientos realizados hasta el momento.
     * @return el {Jugador} que tiene el turno actual.
     */
    public Jugador cambiarJugador(int cantMovActual) {

        Jugador jugadorTurno = jugadores[cantMovActual % jugadores.length]; // cambia el jugador
        ventanaJuego.setTurnoJugador(jugadorTurno.getNombre()); //escribe el jugador en pantalla

        return jugadorTurno;
    }

    /**
     * Simula el giro de la ruleta e imprime el resultado obtenido.
     * @return el número de posiciones que el jugador deberá moverse en el tablero.
    */
    public int girarRuleta( ) {
        return this.ventanaJuego.girarRuletaSync();
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

}
