package org.example.model;

import org.example.model.Jugadores.*;
import org.example.model.Piezas.*;
import org.example.utils.*;

public class Juego {

    private Jugador[] jugadores;
    private Dado_Ruleta dado = new Dado_Ruleta();
    private Tablero tablero;

    /**
     *  Constructor Juego
     */
    public Juego() {
        this.jugadores = new Jugador[3]; // espacio para 3 jugadores maximo
    }

    /**
     * Metodo que inicializa el juego con la base de datos
     * ❌⚠️❌⚠️️❌⚠️❌  esto deberia cambiar usando bd ️ ❌⚠️❌⚠️❌⚠️
     */
    public void inicializarTrucho(){
        baseDeDatosTrucha bd = new baseDeDatosTrucha();

        setJugadores(bd.getJugadores()); // "bd tucho" jugadores
        bd.mostrarDatos();  // "bd trucho" jugadores

        bd.mostrarCasillas(); // "bd trucho" casillas

        setTablero(bd.getTablero()); // "bd" tablero
    }

    /**
     * Metodo que inicializa el juego con la base de datos
     */
    public void inicializar(){
        // todo
    }

    /**
     * Metodo donde se realiza tod0 el juego
     * @return true volver a jugar o false no volver a jugar
     */
    public boolean jugar() {

        inicializarTrucho(); // inicializa el juego con la base de datos trucha
        //inicializar(); // inicializa el juego con la base de datos

        System.out.println("=== Comienza el juego ===");

        int movimientoNum = 0;  // cantidad de movimientos actuales
        boolean seguirJuego = true;  // variable para finalizar el bucle

        do { // bucle principal del juego

            Jugador jugadorTurno = jugadores[movimientoNum % jugadores.length]; // cambia el jugador

            System.out.println("");
            System.out.print("Turno del jugador con ");
            jugadorTurno.mostrarJugador(); // muestra Id y Nombre

            System.out.println("¡Gira el dado!");
            // todo agregar boton de girar
            int cantPosMover = dado.tirar();
            System.out.println( "\uD83C\uDFB2" + " = " + cantPosMover); // muestra el dado en consola ("\uD83C\uDFB2" es un emogi)

            int posicionAnterior = jugadorTurno.getPosicion();   // retorna la posicion del jugador
            int posicionActual = posicionAnterior + cantPosMover; // calcula la nueva posicion
            jugadorTurno.setPosicion(posicionActual);   // mueve al jugador la cantidad de posiciones

            // todo⚠️⚠️ aun no se esta trabajando en las posiciones con el tablero ️⚠️⚠️
            // todo ahora queda trabajar con el tablero (hay que definirlo bien)

            //Adentro de este bloque podemos verificar el color final de la casilla y las que hay entre medio

            tablero.accion(jugadorTurno,posicionAnterior,posicionActual);


            switch (tablero.getCasillas().get(posicionActual).getColor()){  // verifica el color de la casilla
                case "amarilla":
                    System.out.println("Caiste en una casilla amarilla");

                    // aca adentro llamaria a la carta?? 💡💡💡

                    break;
                case "azul":
                    System.out.println("Caiste en una casilla azul");

                    break;
                case "roja":
                    System.out.println("Caiste en una casilla roja");

                    break;
                case "verde":
                    System.out.println("Caiste en una casilla verde");

                    break;
                case "rosa":
                    System.out.println("Caiste en una casilla rosa");

                    break;
            }

            if (movimientoNum > 10){ /// todo ⚠️ verificar el fin real del juego ⚠️
                seguirJuego = false;
            }

            movimientoNum = movimientoNum + 1;
        } while (seguirJuego);

        return false; // true volver a jugar o false no volver a jugar
    }

    /**
     * Getters y Setters
     */
    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

    public Tablero getTablero() {return tablero;}
    public void setTablero(Tablero tablero) {this.tablero = tablero;}




}
