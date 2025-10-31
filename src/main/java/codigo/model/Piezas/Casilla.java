package codigo.model.Piezas;

import codigo.bd.ConexionBD;

import java.sql.*;

public class Casilla {
    private int id;
    private String color;

    // ==========================
    // CONSTRUCTORES
    // ==========================

    public Casilla(int id, String color) {
        this.id = id;
        this.color = color;
    }

    // ==========================
    // METODOS DE BASE DE DATOS
    // ==========================

    /**
     * Obtiene una casilla específica desde la base de datos según su identificador único.
     * Este métod0 ejecuta una consulta SQL para recuperar los datos de una casilla cuyo id_casilla coincida con el valor recibido como parámetro
     *
     * @param idCasilla el identificador único de la casilla que se desea obtener.
     * @return un objeto Casilla con los datos correspondientes si existe o null si no se encuentra o si ocurre un error durante la consulta.
     */
    public static String obtenerColorCasillaPorId(int idCasilla) {
        String query = "SELECT id_casilla, color FROM Casillas WHERE id_casilla = " + idCasilla;

        try (Connection conn = DriverManager.getConnection(ConexionBD.url_estatica);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {

                // retrona el color
                return rs.getString("color");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la casilla con id " + idCasilla + ": " + e.getMessage());
        }

        return null; // si no se encontró
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
