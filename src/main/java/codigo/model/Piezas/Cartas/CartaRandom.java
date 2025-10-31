package codigo.model.Piezas.Cartas;

import codigo.bd.ConexionBD;
import codigo.model.Piezas.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class CartaRandom extends Carta {

    // ==========================
    // Metodo generico obtenerRandom
    // ==========================

    // Para cartas que no necesitan nivel
    public List<Integer> obtenerRandom(String tabla, int limite) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + tabla + " ORDER BY RANDOM() LIMIT ?";
        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener random de " + tabla + ": " + e.getMessage());
        }
        return ids;
    }

    // Para cartas que sí dependen de un nivel
    public List<Integer> obtenerRandom(String tabla, int nivel, int limite) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + tabla + " WHERE nivel = ? ORDER BY RANDOM() LIMIT ?";
        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nivel);
            stmt.setInt(2, limite);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener random de " + tabla + " con nivel " + nivel + ": " + e.getMessage());
        }
        return ids;
    }

    // Metodo abstracto para que cada carta defina cómo usarlo
    public abstract void accion(codigo.model.Jugadores.Jugador jugador);
}
