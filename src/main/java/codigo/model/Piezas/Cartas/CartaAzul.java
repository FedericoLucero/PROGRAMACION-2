package codigo.model.Piezas.Cartas;

import codigo.GUI.VentanaCarta;
import codigo.bd.ConexionBD;
import codigo.model.Jugadores.Jugador;
import codigo.utils.PantallaColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartaAzul extends CartaNivel {

    private String titulo;
    private int sueldo;


    public CartaAzul() {
        super();
    }

    public CartaAzul(int nivel) {
        super(nivel);
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getSueldo() { return sueldo; }
    public void setSueldo(int sueldo) { this.sueldo = sueldo; }

    @Override
    public void accion(Jugador jugador) {
        // Creamos una instancia usando el nivel actual
        CartaAzul profesion = new CartaAzul(getNivel());
        List<Integer> ids = profesion.obtenerRandom("CartaAzul", getNivel(),2);

        if (ids.size() < 2) {
            System.out.println("No hay suficientes profesiones para mostrar.");
            return;
        }

        CartaAzul profesion1 = CartaAzul.buscarCartaId(ids.get(0));
        CartaAzul profesion2 = CartaAzul.buscarCartaId(ids.get(1));

        String[][] opciones = {
                { "Profesión: " + profesion1.getTitulo(), "Salario: " + profesion1.getSueldo(), "Nivel: " + profesion1.getNivel() },
                { "Profesión: " + profesion2.getTitulo(), "Salario: " + profesion2.getSueldo(), "Nivel: " + profesion2.getNivel() }
        };

        VentanaCarta ventana = new VentanaCarta("Elige una profesión", PantallaColor.AZUL, opciones);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        if (seleccion == 1) {
            jugador.actualizar("id_profesion", ids.get(0));
            // ventanaJuego.actualizarProfesionJugador(jugador.getId(), profesion1.getTitulo());
        } else if (seleccion == 2) {
            jugador.actualizar("id_profesion", ids.get(1));
            // ventanaJuego.actualizarProfesionJugador(jugador.getId(), profesion2.getTitulo());
        }
    }

    // ==========================
    // MÉTODOS BD
    // ==========================

    public static CartaAzul buscarCartaId(int id_profesion) {
        String sql = "SELECT * FROM CartaAzul WHERE id = ?";
        CartaAzul carta = null;

        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_profesion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta = new CartaAzul(rs.getInt("nivel"));
                carta.setId(rs.getInt("id"));
                carta.setTitulo(rs.getString("titulo"));
                carta.setSueldo(rs.getInt("sueldo"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta azul: " + e.getMessage());
        }

        return carta;
    }
}
