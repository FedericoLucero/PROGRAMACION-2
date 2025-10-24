package org.example.dao;

import org.example.model.Jugadores.Jugador;
import org.example.utils.ConsolaColor;

import java.sql.*;

public class TableroDAO extends DAO {

    public CasillaDAO casillaDAO =  new CasillaDAO();

    /**
     * Constructor
     */
    public TableroDAO() {}

    /**
     * Recorre el tablero desde una posición anterior hasta la siguiente posición según la ruleta,
     * deteniéndose en casillas "stop" y aplicando efectos de casillas verdes. ✅
     */
    public int recorrerHastaSiguientePosicion(Jugador jugador, int numRuleta) {

        int posicionAnterior = jugador.getPosicion();
        int idCasillaActual = posicionAnterior;

        String query = "SELECT id_casilla, color FROM Casillas WHERE id_casilla > ? ORDER BY id_casilla LIMIT ?";

        try (Connection conn = DriverManager.getConnection(url_static);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, posicionAnterior);
            pstmt.setInt(2, numRuleta);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    idCasillaActual = rs.getInt("id_casilla");
                    String color = rs.getString("color");

                    // Mostrar casilla
                    System.out.println(getColorConsola(color)
                            + String.format("[%03d: %s]", idCasillaActual, color.toUpperCase())
                            + ConsolaColor.RESET);

                    // Efecto verde
                    if (color.equalsIgnoreCase("verde") && jugador.getProfesion() != null) {
                        jugador.actualizarPatrimonio(jugador.getProfesion().getSueldo());
                    }

                    // Casilla stop
                    if (color.equalsIgnoreCase("stop")) {
                        return idCasillaActual;
                    }
                }

            }

        } catch (SQLException e) {
            System.err.println("Error al recorrer el tablero: " + e.getMessage());
            return -1;
        }

        return idCasillaActual;
    }

    public int contarCasillas() {
        String query = "SELECT COUNT(*) AS total FROM " + "Tablero";
        try (Connection conn = DriverManager.getConnection(url_static);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error al contar filas: " + e.getMessage());
        }
        return 0; // retorna 0 si hubo un error
    }


    /**
     * Muestra todas las casillas del tablero directamente desde la base de datos
     */
    public void mostrarTablero() {
        String query = "SELECT id_casilla, color FROM Casillas ORDER BY id_casilla";

        System.out.println("\n=== TABLERO ===\n");

        try (Connection conn = DriverManager.getConnection(url_static);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id_casilla");
                String color = rs.getString("color");

                String colorConsola = getColorConsola(color);
                System.out.println(colorConsola + String.format("[%03d: %s]", id, color.toUpperCase()) + ConsolaColor.RESET);
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar tablero: " + e.getMessage());
        }

        System.out.println("\n================\n");
    }

    /**
     * Método auxiliar para convertir el color de la base de datos en código de consola
     */
    private String getColorConsola(String color) {
        switch (color.toLowerCase()) {
            case "amarilla": return ConsolaColor.AMARILLO;
            case "roja": return ConsolaColor.ROJO;
            case "verde": return ConsolaColor.VERDE;
            case "azul": return ConsolaColor.AZUL;
            case "rosa": return ConsolaColor.ROSA;
            case "gris": return ConsolaColor.GRIS;
            default: return ConsolaColor.RESET;
        }
    }
}
