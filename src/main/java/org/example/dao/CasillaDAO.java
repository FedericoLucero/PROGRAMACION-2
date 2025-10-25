package org.example.dao;

import org.example.model.Piezas.Casilla;

import java.sql.*;

public class CasillaDAO extends DAO{

    private Casilla casilla = new Casilla();

    /**
     * Obtiene una casilla desde la base de datos por su ID.
     *
     * @param idCasilla id de la casilla.
     * @return objeto Casilla si existe, o null si no se encontró.
     */
    public Casilla obtenerCasillaPorId(int idCasilla) {
        String query = "SELECT id_casilla, color FROM Casillas WHERE id_casilla = " + idCasilla;

        try (Connection conn = DriverManager.getConnection(url_static);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int id = rs.getInt("id_casilla");
                String color = rs.getString("color");
                casilla.setId(id);
                casilla.setColor(color);
                return casilla;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la casilla con id " + idCasilla + ": " + e.getMessage());
        }

        return null; // si no se encontró
    }

    /*


     * Devuelve el color de una casilla según su id_casilla
     * @param idCasilla id de la casilla
     * @return color de la casilla como String, o null si no existe

    public String getColorPorId(int idCasilla) {
        String query = "SELECT color FROM Casillas WHERE id_casilla = " + idCasilla;

        try (Connection conn = DriverManager.getConnection(url_static);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString("color");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el color de la casilla: " + e.getMessage());
        }

        return null; // si no se encontró la casilla
    }

     */

}
