package org.example.dao;

import org.example.model.Piezas.Casilla;

import java.sql.*;

public class CasillaDAO {
    // Método para obtener una casilla por ID
    public static Casilla obtenerCasillaPorId(int id) {
        String url = "jdbc:sqlite:BD_LIFE_STATIC.sqlite"; // tu base
        String query = "SELECT id_casilla, color FROM Casillas WHERE id_casilla = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id); // reemplazamos el ?

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idCasilla = rs.getInt("id_casilla");
                    String color = rs.getString("color");
                    return new Casilla(idCasilla, color);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener la casilla: " + e.getMessage());
        }

        return null; // si no se encontró nada
    }
}
