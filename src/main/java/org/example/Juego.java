package org.example;

import static org.example.GUI.InputsPaneles.*;
import org.example.GUI.*;

import static org.example.utils.ConsolaColor.*;

import org.example.model.Piezas.Ruleta;
import org.example.utils.PantallaColor;

import org.example.bd.ConexionBD;

import org.example.model.Jugadores.*;
import org.example.model.Piezas.Cartas.*;
import org.example.model.Piezas.Casilla;
import org.example.model.Piezas.Tablero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Juego {

    private Jugador[] jugadores;
    private Tablero tablero = new Tablero();
    private Ruleta ruleta = new Ruleta();

    private int cantMovActual;
    private int finJuego;
    private VentanaJuego ventanaJuego;


    public Juego() {}

    public boolean jugar() {

        inicializar(); // inicializa atributos del juego

        // variable para finalizar el bucle
        boolean seguirPartida = true;

        do { // bucle principal de la partida
            Jugador jugadorTurno = cambiarJugador(cantMovActual);

            // if que verifica que el jugador no esté en el final
            if (jugadorTurno.getPosicion() != tablero.getCantCasillas()) {

                // metodo que calcula la siguiente posicion (teniendo en cuenta stops y fin del tablero)
                int siguientePosicion = tablero.recorrerHastaSiguientePosicion(jugadorTurno,ruleta.girarRuleta(ventanaJuego));
                // verificamos que la posicion sea menor que el final
                if (siguientePosicion < tablero.getCantCasillas()){

                    // metodo que mueve al jugador a la posicion calculada previamente
                    jugadorTurno.moverJugador(siguientePosicion);

                    //Obtener nivel
                    int nivel = tablero.obtenerTercio(siguientePosicion);

                    // switch con metodo que retorna el color de la casilla
                    switch (Casilla.obtenerColorCasillaPorId(siguientePosicion)) {

                        case "amarilla":
                            ventanaJuego.setDescripcion("Caiste en una casilla AMARILLO", " Posición: " + siguientePosicion, PantallaColor.AMARILLO);
                            accionAmarilla(jugadorTurno, nivel);
                            break;
                        case "azul":
                            ventanaJuego.setDescripcion("Caiste en una casilla AZUL", " Posición: " + siguientePosicion, PantallaColor.AZUL);
                            accionAzul(jugadorTurno, nivel);
                            break;
                        case "roja":
                            ventanaJuego.setDescripcion("Caiste en una casilla ROJA"," Posición: "+ siguientePosicion, PantallaColor.ROJO);
                            accionRoja(jugadorTurno);
                            break;
                        case "verde":
                            ventanaJuego.setDescripcion("Caiste en una casilla VERDE", " Posición: " + siguientePosicion, PantallaColor.VERDE);
                            accionVerde(jugadorTurno);
                            break;
                        case "rosa":
                            ventanaJuego.setDescripcion("Caiste en una casilla ROSA", " Posición: " + siguientePosicion, PantallaColor.ROSA);
                            accionRosa();
                            break;
                        case "naranja":
                            ventanaJuego.setDescripcion("Caiste en una casilla NARANJA", " Posición: " + siguientePosicion, PantallaColor.NARANJA);
                            accionNaranja(jugadorTurno, nivel);
                            break;
                        case "stopAzul":
                            ventanaJuego.setDescripcion("Caiste en una casilla STOP AZUL", " Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionAzul(jugadorTurno, nivel);
                            break;
                        case "stopRosa":
                            ventanaJuego.setDescripcion("Caiste en una casilla STOP ROSA", " Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionRosa();
                            break;
                        case "stopNaranja":
                            ventanaJuego.setDescripcion("Caiste en una casilla STOP NARANJA", " Posición: " + siguientePosicion, PantallaColor.BLANCO);
                            accionNaranja(jugadorTurno, nivel);
                            break;
                        default:
                            System.out.println(GRIS + "Color no reconocido: Posición: " + siguientePosicion + RESET);
                            break;
                    }
                } else {
                    ventanaJuego.setDescripcion("llegaste al final", " Posición: " + siguientePosicion, PantallaColor.BLANCO);
                    jugadorTurno.moverJugador(tablero.getCantCasillas());
                    finJuego -= 1;
                }

                ventanaJuego.actualizarValoresJugador(jugadorTurno.getId(), jugadorTurno.getPosicion(), jugadorTurno.getPatrimonio(), jugadorTurno.getCantidadCasas(), jugadorTurno.getCantidadFamiliares());

                if (finJuego == 0) {
                    seguirPartida = false;
                }
            }
            cantMovActual++;
        } while (seguirPartida);


        // variable para finalizar juego
        boolean fin = jugarDeNuevo();

        // cierra la ventanaJuego del juego
        ventanaJuego.dispose();

        //vaciar la bd
        borrarDatosBDDinamica();
        return fin;
    }

    public void inicializar() {

        this.jugadores = pedirNombresJugadores(pedirCantidadJugadores());
        this.finJuego = jugadores.length;
        this.cantMovActual = 0;

        this.ventanaJuego = new VentanaJuego(jugadores);
        for (Jugador jugador : jugadores) {
            jugador.insertar();  // aca se insertan los jugadores en la bd
        }
    }

    public Jugador cambiarJugador(int cantMovActual) {
        Jugador jugadorTurno = jugadores[cantMovActual % jugadores.length];
        ventanaJuego.setTurnoJugador(jugadorTurno.getNombre());
        return jugadorTurno;
    }

    public void accionAmarilla(Jugador jugadorTurno, int nivel) {

        VentanaCarta.mostrarCartaInformativa("Carta Amarilla", "Te tocó carta amarilla", "Debes girar la ruleta (1 a 5)", PantallaColor.AMARILLO);
        switch (ruleta.girarRuleta(ventanaJuego) % 5) {
            case 1: accionAzul(jugadorTurno, nivel);
            break;
            case 2: accionRoja(jugadorTurno);
            break;
            case 3: accionVerde(jugadorTurno);
            break;
            case 4: accionRosa();
            break;
            case 5: accionNaranja(jugadorTurno, nivel);
            break;
        }
    }

    public void accionAzul(Jugador jugadorTurno, int nivel) {
        CartaAzul profesion = new CartaAzul();
        List<Integer> ids = profesion.obtenerRandom(nivel);

        CartaAzul profesion1 = CartaAzul.buscarCartaId(ids.get(0));
        CartaAzul profesion2 = CartaAzul.buscarCartaId(ids.get(1));


        String[][] opciones = {
                { "Profesión: " + profesion1.getTitulo(), "Salario: " + profesion1.getSueldo(), "Nivel: " + profesion1.getNivel()},
                {"Profesión: " + profesion2.getTitulo(), "Salario: " + profesion2.getSueldo(), "Nivel: " + profesion2.getNivel()}
        };
        VentanaCarta ventana = new VentanaCarta("Elige una profesión", PantallaColor.AZUL, opciones);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        if (seleccion == 1){
            jugadorTurno.actualizar("id_profesion", ids.get(0).intValue());
            // ↑ Ya no necesitas setProfesion(), se actualiza automáticamente
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), profesion1.getTitulo());
        }
        if (seleccion == 2){
            jugadorTurno.actualizar("id_profesion", ids.get(1).intValue());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), profesion2.getTitulo());
        }
    }

    public void accionRoja(Jugador jugadorTurno){

        // Llamo una arta aleatoria de deuda, traigo el valor de patrimonio actual lo resto y luego actualizo
        CartaRoja cartaRoja = CartaRoja.cartaRojaRandom();
        int deuda = cartaRoja.getValor();
        int patrimonio = jugadorTurno.getPatrimonio();

        VentanaCarta.mostrarCartaInformativa("Carta roja", "Te tocó carta roja", "paga impuesto: " + deuda, PantallaColor.ROJO);

        int pago = patrimonio + deuda; //Sumo porque los valores enn tabla ya son negativos

        System.out.println("deuda "+deuda);
        jugadorTurno.actualizar("patrimonio",pago);


    }

    public void accionVerde(Jugador jugadorTurno) {

        int patrimonioActual = jugadorTurno.getPatrimonio();
        int bono = patrimonioActual + 1000;

        VentanaCarta.mostrarCartaInformativa("Carta Verde", "Te tocó carta verde", "Cobra bono: " + 1000, PantallaColor.VERDE);
        jugadorTurno.actualizar("patrimonio", bono);
    }

    public void accionRosa() {
        CartaRosa carta = new CartaRosa();
        List<Integer> ids = carta.obtenerRandom();

        CartaRosa c1 = CartaRosa.buscarCartaId(ids.get(0));
        CartaRosa c2 = CartaRosa.buscarCartaId(ids.get(0));
        CartaRosa c3 = CartaRosa.buscarCartaId(ids.get(0));

        String[][] opcionesRosa = {
                {"Accion: " + c1.getTipo() + c1.getDescripcion() + "Esta accion cuesta: " + c1.getCosto()},
                {"Accion: " + c2.getTipo() + c2.getDescripcion() + "Esta accion cuesta: " + c2.getCosto()},
                {"Accion: " + c3.getTipo() + c3.getDescripcion() + "Esta accion cuesta: " + c3.getCosto()}
            };
        VentanaCarta ventana = new VentanaCarta("Elige tu opción", PantallaColor.ROSA, opcionesRosa);
        int eleccion = ventana.mostrarYEsperarSeleccion();



        // todo acción de que te toque familia


    }

    public void accionNaranja(Jugador jugadorTurno, int nivel) {
        CartaNaranja casa = new CartaNaranja();
        List<Integer> ids = casa.obtenerRandom(nivel);

        // Buscar las dos cartas por ID
        CartaNaranja casa1 = CartaNaranja.buscarCartaId(ids.get(0));
        CartaNaranja casa2 = CartaNaranja.buscarCartaId(ids.get(1));

        String[][] opciones = {
                {"Propiedad: " + casa1.getDescripcion(), "Precio compra: " + casa1.getPrecio_compra(), "Precio venta: " + casa1.getPrecio_venta()},
                {"Propiedad: " + casa2.getDescripcion(), "Precio compra: " + casa2.getPrecio_compra(), "Precio venta: " + casa2.getPrecio_venta()}
        };

        VentanaCarta ventana = new VentanaCarta("Elige una casa", PantallaColor.NARANJA, opciones);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        if (seleccion == 1){
            jugadorTurno.actualizar("id_casa",ids.get(0).intValue());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), casa1.getDescripcion());
        }
        if (seleccion == 2){
            jugadorTurno.actualizar("id_casa",ids.get(1).intValue());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), casa2.getDescripcion());
        }

    }

    private void borrarDatosBDDinamica() {
        String sql = "DELETE FROM jugador";

        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int filas = stmt.executeUpdate();
            System.out.println("Se eliminaron " + filas + " registros de la BD dinámica.");

        } catch (SQLException e) {
            System.err.println("Error al limpiar la base de datos dinámica: " + e.getMessage());
        }
    }


    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

}
