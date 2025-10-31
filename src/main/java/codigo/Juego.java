package codigo;

import static codigo.GUI.InputsPaneles.*;

import codigo.GUI.VentanaCarta;
import codigo.GUI.VentanaJuego;
import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Cartas.*;

import codigo.model.Piezas.Carta;
import codigo.model.Piezas.Cartas.Random.Nivelada.CartaAzul;
import codigo.model.Piezas.Cartas.Random.Nivelada.CartaNaranja;
import codigo.model.Piezas.Cartas.Random.CartaRoja;
import codigo.model.Piezas.Cartas.Random.CartaRosa;
import codigo.model.Piezas.Ruleta;
import codigo.utils.PantallaColor;

import codigo.bd.ConexionBD;

import codigo.model.Piezas.Casilla;
import codigo.model.Piezas.Tablero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

                    jugadorTurno.moverJugador(siguientePosicion);

                    // obtenemos el color de la casilla
                    String color = Casilla.obtenerColorCasillaPorId(siguientePosicion);

                    // Mostramos información en pantalla
                    ventanaJuego.setDescripcion("Caiste en una casilla " + color.toUpperCase(), "Posición: " + siguientePosicion, PantallaColor.obtenerColor(color));

                    // Obtener nivel de carta
                    int nivel = tablero.obtenerTercio(siguientePosicion);

                    // plicamos polimorfismo
                    Carta carta = crearCartaPorColor(color, nivel);
                    if (carta != null) {
                        carta.accion(jugadorTurno); // polimorfismo
                    }

                } else {
                    ventanaJuego.setDescripcion("¡Llegaste al final!", "Posición: " + siguientePosicion, PantallaColor.BLANCO);
                    jugadorTurno.moverJugador(tablero.getCantCasillas());
                    finJuego -= 1;
                }

                ventanaJuego.actualizarDatosJugador(jugadores,cantMovActual % jugadores.length, siguientePosicion);

                if (finJuego == 0) {
                    seguirPartida = false;
                }
            }

            cantMovActual++;

        } while (seguirPartida);

        finalizar(); // deternima quien es el ganador

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

    private Carta crearCartaPorColor(String color, int nivel) {
        return switch (color.toLowerCase()) {
            case "amarilla" -> new CartaAmarilla(tablero, ruleta, ventanaJuego);
            case "azul", "stopazul" -> new CartaAzul(nivel);
            case "roja" -> new CartaRoja();
            case "verde" -> new CartaVerde();
            case "rosa", "stoprosa" -> new CartaRosa();
            case "naranja", "stopnaranja" -> new CartaNaranja(nivel);
            default -> null;
        };
    }

    public static void agregarDeuda(Jugador jugador, int monto) {
        int deudaActual = jugador.getDeuda();
        int nuevaDeuda = deudaActual + monto;
        jugador.actualizar("deudas", nuevaDeuda);
    }

    public static void cobrarCosto(Jugador jugador, int costo) {
        int patrimonioActual = jugador.getPatrimonio();

        if (patrimonioActual < costo) {
            int deuda = costo - patrimonioActual;
            jugador.actualizar("patrimonio", 0);
            agregarDeuda(jugador, deuda);
        } else {
            jugador.actualizar("patrimonio", patrimonioActual - costo);
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

    public void finalizar() {
        System.out.println("===== RESULTADOS FINALES =====");

        Jugador ganador = null;
        int mayorResultado = Integer.MIN_VALUE;

        // Recorremos todos los jugadores
        for (Jugador jugador : jugadores) {
            int ganancia = jugador.getGanancia();  // bienes acumulados
            int deuda = jugador.getDeuda();        // deudas acumuladas
            int patrimonio = jugador.getPatrimonio(); // dinero en mano
            int resultadoFinal = ganancia + patrimonio - deuda;

            System.out.println("Jugador: " + jugador.getNombre() + " | Ganancia: " + ganancia + " | Patrimonio: " + patrimonio + " | Deuda: " + deuda + " | Resultado Final: " + resultadoFinal);

            // Actualizamos el jugador ganador
            if (resultadoFinal > mayorResultado) {
                mayorResultado = resultadoFinal;
                ganador = jugador;
            }
        }

        if (ganador != null) {
            System.out.println("\n🏆 El ganador es: " + ganador.getNombre() + " con un total de " + mayorResultado);
            VentanaCarta.mostrarCartaInformativa("Fin del Juego", "¡Ganador!", "El jugador " + ganador.getNombre() + " obtuvo el mayor patrimonio neto: " + mayorResultado, PantallaColor.VERDE);
        } else {
            System.out.println("\nNo se pudo determinar un ganador.");
        }
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Jugador[] getJugadores() {return jugadores;}
    public void setJugadores(Jugador[] jugadores) {this.jugadores = jugadores;}

}
