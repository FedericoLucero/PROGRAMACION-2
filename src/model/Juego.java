package model;

import model.Jugadores.Jugador;
import model.Piezas.Dado_Ruleta;
import model.Piezas.Tablero;


public class Juego {
    private Jugador[] jugadores;
    private Dado_Ruleta dado = new Dado_Ruleta();
    private Tablero tablero;

    public Juego() {
        this.jugadores = new Jugador[3]; // espacio para 3 jugadores max
    }

    public void inicializar(){  /// ⚠️ ❌ todo esto deberia cambiar usando bd ️ ❌ ⚠️
        baseDeDatosTrucha bd = new baseDeDatosTrucha();

        setJugadores(bd.getJugadores()); // bd jugadores
        bd.mostrarDatos();  // bd jugadores

        bd.mostrarCasillas(); // bd casillas
        setTablero(bd.getTablero()); // bd tablero

    }

    public boolean jugar() { // metodo principal del juego
        inicializar();
        System.out.println("=== Comienza el juego ===");

        int movimientoNum = 0;  // cantidad de movimientos
        boolean seguir = true;  // variable para finalizar el bucle

        do { // bucle principal del juego

            Jugador jugadorTurno = jugadores[movimientoNum % jugadores.length]; // cambia el jugador
            System.out.println("");
            System.out.print("Turno del jugador con ");
            jugadorTurno.mostrarJugador();


            System.out.println("Gira el dado!");
            int cantPosMover = dado.tirar();
            System.out.println( "\uD83C\uDFB2" + " = " + cantPosMover); // muestra el dado en consola


            int posicionAnterior = jugadorTurno.getPosicion();   // pide la posicion del jugador
            int posicionActual = posicionAnterior + cantPosMover;
            jugadorTurno.setPosicion(posicionActual);   // mueve al jugador la cantidad de posiciones
            // ⚠️⚠️ aun no se esta trabajando en las posiciones con el tablero ️⚠️⚠️


            // ahora queda trabajar con el tablero (hay que definirlo bien)

            switch (tablero.getCasillas().get(posicionActual).getColor()){  // verifica el color de la casilla
                case "amarilla":
                    System.out.println("Caiste en una casilla amarilla");

                    // aca adentro llamaria a la carta?? 💡💡💡💡💡💡💡

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

            /// juego

            if (movimientoNum > 10){ /// ⚠️ verificar el fin real del juego ⚠️
                seguir = false;
            }

            movimientoNum = movimientoNum + 1;
        } while (seguir);





        /// juego

        return false; // true volver a jugar - false no volver a jugar
    }

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

    public Tablero getTablero() {return tablero;}
    public void setTablero(Tablero tablero) {this.tablero = tablero;}
}
