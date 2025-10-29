package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;
import org.example.model.Piezas.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class CartaRoja extends Carta {

    private String descripcion;
    private int valor;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getValor() {return valor;}
    public void setValor(int valor) {this.valor = valor;}
    // ==========================
    //  METODOS BD
    // ==========================

    public static CartaRoja cartaRojaRandom(){
        Random rdm = new Random();
        int carta_random = rdm.nextInt(0,10);
        String sql = "SELECT * FROM CartaRoja WHERE id = ?";
        CartaRoja carta = new CartaRoja();
        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carta_random);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta = new CartaRoja();
                carta.setId(rs.getInt("id"));
                carta.setDescripcion(rs.getString("descripcion"));
                carta.setValor(rs.getInt("valor"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta naranja: " + e.getMessage());
        }

        return carta;

    }

}
