package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de utilidad para manejar la conexión y la inicialización
 * de la base de datos SQLite para la agenda.
 */
public class ConexionDB {

    /** Ruta del archivo SQLite (se creará en el directorio raíz del proyecto). */
    private static final String URL = "jdbc:sqlite:BDIntegrador.sqlite";

    /**
     * Conecta con la base de datos SQLite.
     * @return conexión activa o null si ocurre un error.
     */
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println(" Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Conecta con la base de datos SQLite y activa las claves foráneas.
     * @return conexión activa o null si falla la conexión.
     */
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println(" Error al conectar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prueba la conexión a la base de datos e imprime el resultado.
     */
    public static void probarConexion() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println(" Conexión establecida con SQLite: " + URL);
            } else {
                System.err.println(" No se pudo establecer la conexión con la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println(" Error al probar la conexión: " + e.getMessage());
        }
    }

    /**
     * Crea todas las tablas necesarias para la agenda.
     * Si ya existen, las elimina primero.
     *
    public static void inicializarBase() {
        try (Connection conn = getConnection()) {
            if (conn == null) {
                System.err.println(" Conexión nula, no se pueden crear las tablas.");
                return;
            }

            try (Statement stmt = conn.createStatement()) {
                // Eliminar tablas si existen
                stmt.execute("DROP TABLE IF EXISTS direcciones;");
                stmt.execute("DROP TABLE IF EXISTS emails;");
                stmt.execute("DROP TABLE IF EXISTS contactos;");

                // Crear tabla contactos
                stmt.execute("""
                    CREATE TABLE contactos (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        apellido TEXT NOT NULL,
                        telefono TEXT
                    );
                """);

                // Crear tabla emails
                stmt.execute("""
                    CREATE TABLE emails (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        contacto_id INTEGER NOT NULL,
                        email TEXT NOT NULL,
                        FOREIGN KEY (contacto_id) REFERENCES contactos(id) ON DELETE CASCADE
                    );
                """);

                // Crear tabla direcciones
                stmt.execute("""
                    CREATE TABLE direcciones (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        contacto_id INTEGER NOT NULL,
                        direccion TEXT NOT NULL,
                        FOREIGN KEY (contacto_id) REFERENCES contactos(id) ON DELETE CASCADE
                    );
                """);

                System.out.println(" Tablas creadas correctamente en agenda.sqlite");
            }

        } catch (SQLException e) {
            System.err.println(" Error al crear las tablas: " + e.getMessage());
        }
    }

     */
}
