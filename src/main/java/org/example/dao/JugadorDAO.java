package org.example.dao;

import org.example.bd.ConexionBD;
import org.example.model.Jugadores.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JugadorDAO {
    private final String url_dinamica ="jdbc:sqlite:BDIntegrador.sqlite";

    /* crear jugador
       leer jugador
       actualizar jugador
     */

    public JugadorDAO() {
    }

    /* Crear jugador
    Al crear jugador solo se requiere el nombre, posteriormente con otra logica se le asignara profesion
     */
    public void insertar(Jugador j) {
        String sqlInsertar = "INSERT INTO jugador (nombre, patrimonio, posicion, estado_civil, hijos, deudas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new ConexionBD(url_dinamica).getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {
            stmt.setString(1, j.getNombre());
           // stmt.setString(2, j.getProfesion().getTitulo());
            stmt.setDouble(2, j.getPatrimonio());
            stmt.setInt(3, j.getPosicion());
            stmt.setInt(4, j.getEstado_civil());
            stmt.setInt(5, j.getHijos());
            stmt.setDouble(6, j.getDeuda());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar jugador: " + e.getMessage());
        }
    }

    /*
    Actualizar: campo:nombre columna a actualizar,
    nuevoValor: int/string del valor nuevo, el metodo actualizar tiene sobrecarga por lo que elige el metodo correcto segun el tipo de este
    nombreActual: Por ahora el metodo modifica los campos a partir del nombre del jugador(mirar test), pero puede cambiarse a id

     */
    // Para campos String (nombre, profesion)
    public boolean actualizar(String campo, String nuevoValor, String nombreActual) {
        String sql;

        switch (campo) {
            case "nombre":
                sql = "UPDATE jugador SET nombre=? WHERE nombre=?";
                break;
            case "profesion":
                sql = "UPDATE jugador SET profesion=? WHERE nombre=?";
                break;
            default:
                System.err.println("Campo no válido para tipo String: " + campo);
                return false;
        }

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoValor);
            stmt.setString(2, nombreActual);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
            return false;
        }
    }

    // Para campos int (patrimonio, estado_civil, hijos, posicion, deudas)
    public boolean actualizar(String campo, int nuevoValor, String nombreActual) {
        String sql;

        switch (campo) {
            case "patrimonio":
                sql = "UPDATE jugador SET patrimonio=? WHERE nombre=?";
                break;
            case "estado_civil":
                sql = "UPDATE jugador SET estado_civil=? WHERE nombre=?";
                break;
            case "hijos":
                sql = "UPDATE jugador SET hijos=? WHERE nombre=?";
                break;
            case "posicion":
                sql = "UPDATE jugador SET posicion=? WHERE nombre=?";
                break;
            case "deudas":
                sql = "UPDATE jugador SET deudas=? WHERE nombre=?";
                break;
            default:
                System.err.println("Campo no válido para tipo int: " + campo);
                return false;
        }

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoValor);
            stmt.setString(2, nombreActual);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
            return false;
        }
    }

}
