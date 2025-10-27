package org.example;

import static org.example.GUI.InputsPaneles.*;
import static org.example.utils.ConsolaColor.*;

import org.example.GUI.*;
import org.example.model.Jugadores.*;
import org.example.model.Piezas.Cartas.CartaAzul;
import org.example.model.Piezas.Casilla;
import org.example.model.Piezas.Tablero;
import org.example.utils.PantallaColor;
import org.example.utils.UserInput;
import java.util.List;

public class Juego {

    private Jugador[] jugadores;
    private Tablero tablero = new Tablero();

    private int cantJugadores;
    private int cantMovimientosActuales = 0;
    private int finJuego;

    private VentanaJuego ventanaJuego;

    UserInput ui = new UserInput();
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

            // if que verifica que el jugador no esté en el final
            if (jugadorTurno.getPosicion() != tablero.getCantCasillas()) {

                // metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
                int siguientePosicion = tablero.recorrerHastaSiguientePosicion(jugadorTurno,girarRuleta());

                // verificamos que la posicion sea menor que el final
                if (siguientePosicion < tablero.getCantCasillas()){

                    // metodo que mueve al jugador a la posicion calculada previamente
                    jugadorTurno.moverJugador(siguientePosicion);

                    //Obtener nivel
                    int nivel = tablero.obtenerTercio(siguientePosicion);

                    // switch con metodo que retorna el color de la casilla
                    switch (Casilla.obtenerColorCasillaPorId(siguientePosicion)) {

                        case "amarilla":

                            ventanaJuego.setDescripcion("Caiste en una casilla AMARILLO"," Posición: " + siguientePosicion , PantallaColor.AMARILLO);
                            accionAmarilla(jugadorTurno,nivel);
                            break;

                        case "azul":

                            ventanaJuego.setDescripcion("Caiste en una casilla AZUL"," Posición: "+ siguientePosicion, PantallaColor.AZUL);
                            accionAzul(jugadorTurno,nivel);
                            break;

                        case "roja":

                            ventanaJuego.setDescripcion("Caiste en una casilla ROJA"," Posición: "+ siguientePosicion, PantallaColor.ROJO);
                            accionRoja();
                            break;

                        case "verde":

                            ventanaJuego.setDescripcion("Caiste en una casilla VERDE"," Posición: "+ siguientePosicion, PantallaColor.VERDE);
                            accionVerde();
                            break;

                        case "rosa":

                            ventanaJuego.setDescripcion("Caiste en una casilla ROSA"," Posición: " + siguientePosicion, PantallaColor.ROSA);
                            accionRosa();
                            break;

                        case "naranja":

                            ventanaJuego.setDescripcion("Caiste en una casilla NARANJA"," Posición: " + siguientePosicion, PantallaColor.NARANJA);
                            accionNaranja();
                            break;

                        // =================
                        // caso de STOPS
                        // acción especial (las casiilas de stop son siempre fijas en el tablero)
                        // =================

                        case "stopAzul": // casilla 3

                            ventanaJuego.setDescripcion("Caiste en una casilla STOP AZUL"," Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionAzul(jugadorTurno,nivel);
                            break;

                        case "stopRosa": // casilla 28

                            ventanaJuego.setDescripcion("Caiste en una casilla STOP ROSA"," Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionRosa();
                            break;

                        case "stopNaranja": // casilla 40

                            ventanaJuego.setDescripcion("Caiste en una casilla STOP NARANJA"," Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionNaranja();
                            break;

                        default:
                            System.out.println(GRIS + "Color no reconocido: Posición: " + siguientePosicion + RESET);
                            break;
                    }

                } else {  // la posicion del jugador es mayor o igual a el final

                    ventanaJuego.setDescripcion("llegaste al final"," Posición: " + siguientePosicion, PantallaColor.BLANCO);
                    jugadorTurno.moverJugador(tablero.getCantCasillas());
                    finJuego -= 1;
                }

                // actualizamos informaciondel jugador en la ventana
                ventanaJuego.actualizarValoresJugador(jugadorTurno.getId(),jugadorTurno.getPosicion(), jugadorTurno.getPatrimonio(), jugadorTurno.getCantidadCasas(), jugadorTurno.getCantidadFamiliares());

                // if que verifica el final de la partida
                if (finJuego == 0){ // verificar el fin real del juego (cuando todos los jugadores llegan a la meta)

                    // todo verificar ganador de la partida

                    seguirPartida = false;
                }
            }


            cantMovimientosActuales += 1;
        } while (seguirPartida);


        // variable para finalizar juego
        boolean fin = jugarDeNuevo();

        // cierra la ventanaJuego del juego
        ventanaJuego.dispose();


        return fin;
    }

    public void inicializar(){
        this.cantJugadores = pedirCantidadJugadores();
        this.finJuego = cantJugadores;
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

    public void accionAmarilla(Jugador jugadorTurno,int nivel) {

        // todo acción random (quizas girar ruleta y que te pueda tocar cualquier carta)
        VentanaCartaAmarilla.llamarVentanaAmarilla();

        switch (girarRuleta() % 5) {
            case 1:
                // accion azul
                accionAzul(jugadorTurno, nivel);
                break;
            case 2:
                // accion roja
                accionRoja();
                break;
            case 3:
                //accion verde
                accionVerde();
                break;
            case 4:
                // accion rosa
                accionRosa();
                break;
            case 5:
                // accion naranja
                accionNaranja();
                break;
        }
    }

    /**
     * Permite al jugador elegir entre dos profesiones del mismo nivel.
     * Genera dos opciones aleatorias, muestra una ventana de elección
     * y actualiza la profesión del jugador según su seleccion.
     */
    public void accionAzul(Jugador jugadorTurno, int nivel) {

        // Crear la carta y obtener las dos profesiones aleatorias
        CartaAzul profesion = new CartaAzul();
        List<Integer> ids = profesion.obtenerRandom(nivel);

        // Buscar las dos cartas por ID
        CartaAzul profesion1 = CartaAzul.buscarCartaId(ids.get(0));
        CartaAzul profesion2 = CartaAzul.buscarCartaId(ids.get(1));

        // Crear las descripciones para mostrar en la ventana
        String[] desc1 = { "Profesión: " + profesion1.getTitulo(), "Salario: " + profesion1.getSueldo(), "Nivel: " + profesion1.getNivel()};
        String[] desc2 = {"Profesión: " + profesion2.getTitulo(), "Salario: " + profesion2.getSueldo(), "Nivel: " + profesion2.getNivel()};

        // Mostrar la ventana de elección
        VentanaCartaAzul ventana = new VentanaCartaAzul(desc1, desc2);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        if (seleccion == 1){
            jugadorTurno.actualizar("id_profesion",ids.get(0).intValue());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), profesion1.getTitulo());
        }
        if (seleccion == 2){
            jugadorTurno.actualizar("id_profesion",ids.get(1).intValue());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), profesion2.getTitulo());
        }
    }

    public void accionRoja(){
        VentanaCartaRoja.llamarVentanaRoja();
        // todo acción de pagar impuesto
    }

    public void accionVerde(){
        VentanaCartaVerde.llamarVentanaVerde();
        // todo acción de cobrar bono
        // todo ya se cobro el sueldo normal cada vez que se paso por "arriba" de una casilla verde
    }
    public void accionRosa(){

        String[] mascota = {"Mascota: Perro", "Edad: 2 años", "Raza: Labrador"}; // codigo de prueba
        String[] pareja = {"Pareja: Ana", "Edad: 28", "Profesión: Abogada"};// codigo de prueba
        String[] hijo = {"Hijo: Juan", "Edad: 5", "Escuela: Primaria"};// codigo de prueba

        VentanaCartaRosa ventana = new VentanaCartaRosa(mascota, pareja, hijo);// codigo de prueba
        int eleccion = ventana.mostrarYEsperarSeleccion();// codigo de prueba


        // todo acción de que te toque familia
        // todo falta crear casillas rosas en la bd
    }
    public void accionNaranja(){
        String[] casa1 = {"Casa: Chalet", "Valor: $200.000", "Ubicación: Centro"}; // codigo de prueba
        String[] casa2 = {"Casa: Departamento", "Valor: $150.000", "Ubicación: Playa"}; // codigo de prueba

        VentanaCartaNaranja ventana = new VentanaCartaNaranja(casa1, casa2);// codigo de prueba
        int eleccion = ventana.mostrarYEsperarSeleccion();// codigo de prueba


        // todo no se si esta la casilla naranaj en el juego original
        // todo accion de elegir entre dos casas de un mismo nivel (podria ser que primero te de nivel 1, luego nievl 2... creciendo)
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

}
