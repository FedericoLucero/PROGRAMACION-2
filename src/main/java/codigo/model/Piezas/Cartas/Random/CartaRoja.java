package codigo.model.Piezas.Cartas.Random;

import codigo.GUI.VentanaCarta;
import codigo.Juego;
import codigo.bd.ConexionBD;
import codigo.model.Jugadores.Jugador;
import codigo.utils.PantallaColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CartaRoja extends CartaRandom {

    private String descripcion;
    private int valor;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getValor() {return valor;}
    public void setValor(int valor) {this.valor = valor;}

    @Override
    public void accion(Jugador jugador) {
        List<Integer> ids = obtenerRandom("CartaRoja", 1);

        if (ids.isEmpty()) {
            System.out.println("No se encontró ninguna carta roja aleatoria.");
            return;
        }

        CartaRoja cartaRoja = CartaRoja.buscarCartaPorId(ids.get(0));
        VentanaCarta.mostrarCartaInformativa("Carta roja", "Te tocó carta roja", "Paga impuesto: " + cartaRoja.getValor(), PantallaColor.ROJO);

        Juego.cobrarCosto(jugador, cartaRoja.getValor());
    }

    public static CartaRoja buscarCartaPorId(int id) {
        String sql = "SELECT * FROM CartaRoja WHERE id = ?";
        CartaRoja carta = new CartaRoja();
        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta.setId(rs.getInt("id"));
                carta.setDescripcion(rs.getString("descripcion"));
                carta.setValor(rs.getInt("valor"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta roja: " + e.getMessage());
        }

        return carta;
    }
}
