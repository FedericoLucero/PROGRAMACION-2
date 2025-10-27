package org.example.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD{

    private final String url;
    private Connection connection;

    public final static String url_dinamica = "jdbc:sqlite:BD_LIFE_DINAMIC.sqlite";
    public final static String url_estatica = "jdbc:sqlite:BD_LIFE_STATIC.sqlite";
    public ConexionBD(String url) {
        this.url = url;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url);
                System.out.println("Conectado a " + url);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return connection;
    }


    public void cerrarConexion() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada exitosamente");
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
            connection.close();
        }finally {
            connection.close();
        }
    }
}
