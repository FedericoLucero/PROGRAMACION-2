package org.example.model;


import org.example.model.Jugadores.*;
import org.example.model.Piezas.*;
import org.example.model.Piezas.Cartas.CartaAzul;
import org.example.utils.UserInput;

public class Juego {

    private Jugador[] jugadores;
    private Ruleta ruleta = new Ruleta();
    private Tablero tablero;

    public int suma_verde; // hay que encontrar una solucion para esto


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

        inicializarTrucho(); // metodo que inicializa el juego con la base de datos trucha
        //inicializar(); // metodo que inicializa el juego con la base de datos

        pedirDatosJugadores(); // metodo que pide los nombres a los jugadores

        tablero.mostrarTablero(); // metodo que muestra el tablero

        System.out.println("=== Comienza el juego ===");

        int cantJugadas = 0;  // cantidad de movimientos actuales
        boolean seguirJugando = true;  // variable para finalizar el bucle

        do { // bucle principal del juego

            Jugador jugadorTurno = cambiarJugador(cantJugadas); // cambia el jugador

            int posicion = calcularSiguientePosicion(jugadorTurno.getPosicion(),girarRuleta()); //metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
            moverJugador(jugadorTurno, posicion); // metodo que mueve al jugador a la posicion calculada


            int idCasilla = tablero.getCasillas().get(posicion).getId(); // pedimos el id de la casilla para mostrarlo (id = index + 1) (index del arraylist)
            switch (tablero.getCasillas().get(posicion).getColor()) { // verificamos el color de la casilla

                case "amarilla":
                    System.out.println("Caiste en una casilla amarilla: POS: " + idCasilla);

                    // aca adentro deberia llamar una accion random

                    break;
                case "azul":
                    System.out.println("Caiste en una casilla azul: POS: " + idCasilla);

                    // aca adentro deberia llamarse una accion de estudios
                    /// todo trabajarlo con bd


                    System.out.println("Elige una profesion");

                    System.out.println("=============");
                    System.out.println("1. Ingeniero ");
                    System.out.println("salario: 2000");
                    System.out.println("impuesto: 1000");

                    System.out.println("=============");
                    System.out.println("2. Profesor");
                    System.out.println("salario: 500");
                    System.out.println("impuesto: 50");
                    System.out.println("=============");

                    Profesion profesion = new Profesion();

                    UserInput UI = new UserInput();
                    switch (UI.getInt("",1,2)){
                        case 1:
                            profesion.setTitulo("Ingeniero");
                            profesion.setSalario(2000);
                            break;
                        case 2:
                            profesion.setTitulo("profesor");
                            profesion.setSalario(500);
                            break;
                    }

                    CartaAzul cartaAzul  = new CartaAzul(profesion);
                    cartaAzul.cambiarProfesion(jugadorTurno);
                    jugadorTurno.getProfesion().mostrarProfesion();

                    break;
                case "roja":
                    System.out.println("Caiste en una casilla roja: POS: " + idCasilla);

                    // aca deberia llamarse una accion de pagar

                    break;
                case "verde":
                    System.out.println("Caiste en una casilla verde: POS: " + idCasilla);

                    // aca deberia poder llamar una accion de cobro

                    break;
                case "rosa":
                    System.out.println("Caiste en una casilla rosa: POS: " + idCasilla);

                    // aca deberia llamar una accion de familia

                    break;
                case  "stop":
                    System.out.println("Caiste en una casilla stop: POS: " + idCasilla);

                    // aca deberia llamar acciones especiales
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
     * ❌⚠️❌⚠️️❌⚠️❌  esto deberia cambiar usando bd ️ ❌⚠️❌⚠️❌⚠️
     */
    public void inicializarTrucho(){
        baseDeDatosTrucha bd = new baseDeDatosTrucha();

        //setJugadores(bd.getJugadores()); // "bd tucho" jugadores
        //bd.mostrarDatos();  // "bd trucho" jugadores
        //bd.mostrarCasillas(); // "bd trucho" casillas

        setTablero(bd.getTablero()); // "bd" tablero
    }

    /**
     * Metodo que inicializa el juego con la base de datos
     */
    public void inicializar(){

        // todo
    }


    public void pedirDatosJugadores(){
        UserInput UI = new  UserInput();
        for (int i = 1; i <= jugadores.length; i++) {
            Jugador jugador = new Jugador(i, UI.getString("Ingrese nombre del jugador " +  i + " ", 1, 10 ));
            jugadores[i-1] = jugador;
        }
    }

    public Jugador cambiarJugador(int cantMovActual){
        Jugador jugadorTurno = jugadores[cantMovActual % jugadores.length]; // cambia el jugador

        System.out.print("Turno del jugador con ");
        jugadorTurno.mostrarJugador(); // muestra Id y Nombre

        return jugadorTurno;
    }

    public int girarRuleta(){
        System.out.println("¡Gira la ruleta!");

        int cantPosMover = ruleta.girarConGUI();
        System.out.println("🎲 = " + cantPosMover);

        return cantPosMover;
    }

    public int calcularSiguientePosicion(int posicionAnterior, int numRuleta) {
        int i = posicionAnterior;
        suma_verde = 0;

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

            if (c.getColor().equals("verde")) {
                suma_verde += 1;
            }
        }
        return i;
    }

    public void moverJugador(Jugador jugadorTurno, int posicionSiguiente){
        jugadorTurno.setPosicion(posicionSiguiente);   // mueve al jugador la cantidad de posiciones
    }

    /**
     * Getters y Setters
     */
    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

    public Tablero getTablero() {return tablero;}
    public void setTablero(Tablero tablero) {this.tablero = tablero;}




}
