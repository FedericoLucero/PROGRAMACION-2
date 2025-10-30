package org.example;

import static org.example.GUI.InputsPaneles.*;
import org.example.GUI.*;

import static org.example.utils.ConsolaColor.*;

import org.example.model.Piezas.Carta;
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
                            accionRosa(jugadorTurno);
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
                            accionRosa(jugadorTurno);
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
        finalizar();

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
            case 4: accionRosa(jugadorTurno);
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
        VentanaCarta.mostrarCartaInformativa("Carta roja", "Te tocó carta roja", "paga impuesto: " + cartaRoja.getValor(), PantallaColor.ROJO);
        cobrarCosto(jugadorTurno, cartaRoja.getValor());

    }

    public void accionVerde(Jugador jugadorTurno) {

        int patrimonioActual = jugadorTurno.getPatrimonio();
        int bono = patrimonioActual + 1000;

        VentanaCarta.mostrarCartaInformativa("Carta Verde", "Te tocó carta verde", "Cobra bono: " + 1000, PantallaColor.VERDE);
        jugadorTurno.actualizar("patrimonio", bono);
    }

    public void accionRosa(Jugador jugadorTurno) {
        CartaRosa carta = new CartaRosa();
        List<Integer> ids = carta.obtenerRandom();

        CartaRosa c1 = CartaRosa.buscarCartaId(ids.get(0));
        CartaRosa c2 = CartaRosa.buscarCartaId(ids.get(1));
        CartaRosa c3 = CartaRosa.buscarCartaId(ids.get(2));

        String[][] opcionesRosa = {
                {"Accion: " + c1.getTipo() + "\n" + c1.getDescripcion() + "\n" + "Esta accion cuesta: " + c1.getCosto()},
                {"Accion: " + c2.getTipo() + "\n" + c2.getDescripcion() + "\n" + "Esta accion cuesta: " + c2.getCosto()},
                {"Accion: " + c3.getTipo() + "\n" + c3.getDescripcion() + "\n" + "Esta accion cuesta: " + c3.getCosto()}
        };

        VentanaCarta ventana = new VentanaCarta("Elige tu opción", PantallaColor.ROSA, opcionesRosa);
        int seleccion = ventana.mostrarYEsperarSeleccion();
        //Segun seleccion guardo carta, porque el tipo de carta rosa cambia diferentes campos en jugador
        CartaRosa cartaElegida = switch (seleccion) {
            case 1 -> c1;
            case 2 -> c2;
            case 3 -> c3;
            default -> null;
        };

        if (cartaElegida == null) return;

        // Resto el costo de la accion
        cobrarCosto(jugadorTurno, cartaElegida.getCosto());
        //Agrego ganancia para calcular ganador
        int beneficio = cartaElegida.getBeneficio() + jugadorTurno.getGanancia();
        jugadorTurno.actualizar("ganancia",beneficio);

        // Actualizo campos dependiendo del tipos
        switch (cartaElegida.getTipo()) {
            case "Adoptar":
                if (cartaElegida.getDescripcion().equalsIgnoreCase("Mellisos")) {
                    jugadorTurno.setHijos(jugadorTurno.getHijos() + 2);
                } else {
                    jugadorTurno.setHijos(jugadorTurno.getHijos() + 1);
                }
                break;

            case "Casarse":
                jugadorTurno.setEstado_civil(1); // 1 casado 0 no
                break;

            case "Adoptar Mascota":
                jugadorTurno.setMascota(cartaElegida.getId());
                break;
        }

        //Actualizo bd dinamica
        String sql = "UPDATE jugador SET patrimonio=?, hijos=?, estado_civil=?, id_mascota=? WHERE id_jugador=?";
        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jugadorTurno.getPatrimonio());
            stmt.setInt(2, jugadorTurno.getHijos());
            stmt.setInt(3, jugadorTurno.getEstado_civil());
            stmt.setInt(4, jugadorTurno.getMascota());
            stmt.setInt(5, jugadorTurno.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al actualizar jugador con carta rosa: " + e.getMessage());
        }
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

        CartaNaranja casaElegida = switch (seleccion){
            case 1 -> casa1;
            case 2 -> casa2;
            default -> null;
        };

        if (casaElegida != null){
            //Atualizacion de campo y cobro de deuda
            cobrarCosto(jugadorTurno, casaElegida.getPrecio_compra());
            jugadorTurno.actualizar("id_casa", casaElegida.getId());
            ventanaJuego.actualizarProfesionJugador(jugadorTurno.getId(), casaElegida.getDescripcion());
            //Agrego ganancia para calcular ganador
            int beneficio = casaElegida.getPrecio_venta() + jugadorTurno.getGanancia();
            jugadorTurno.actualizar("ganancia",beneficio);
        }

    }

    public void agregarDeuda(Jugador jugador, int monto) {
        int deudaActual = jugador.getDeuda();
        int nuevaDeuda = deudaActual + monto;
        jugador.actualizar("deudas", nuevaDeuda);
    }
    public void cobrarCosto(Jugador jugador, int costo) {
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

            System.out.println(
                    "Jugador: " + jugador.getNombre() +
                            " | Ganancia: " + ganancia +
                            " | Patrimonio: " + patrimonio +
                            " | Deuda: " + deuda +
                            " | Resultado Final: " + resultadoFinal
            );

            // Actualizamos el jugador ganador
            if (resultadoFinal > mayorResultado) {
                mayorResultado = resultadoFinal;
                ganador = jugador;
            }
        }

        if (ganador != null) {
            System.out.println("\n🏆 El ganador es: " + ganador.getNombre() + " con un total de " + mayorResultado);
            VentanaCarta.mostrarCartaInformativa(
                    "Fin del Juego",
                    "¡Ganador!",
                    "El jugador " + ganador.getNombre() + " obtuvo el mayor patrimonio neto: " + mayorResultado,
                    PantallaColor.VERDE
            );
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
