package utils;
import java.sql.*;

public class Conexion {

    private Connection conn;

    public Connection establecerConexion() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos.sqlite");
            System.out.println("Conexión establecida.");
        } catch (SQLException ex) {
            System.err.println("Error al establecer conexión: " + ex.getMessage());
        }
        return conn;
    }

    public void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}
