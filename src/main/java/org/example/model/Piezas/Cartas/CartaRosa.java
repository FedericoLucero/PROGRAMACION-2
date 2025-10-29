package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;
import org.example.model.Piezas.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartaRosa extends Carta {
    private int id;
    private String tipo;
    private String descripcion;
    private int beneficio;
    private int costo;
    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    @Override public int getId() {return id;}
    @Override public void setId(int id) {this.id = id;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getBeneficio() {return beneficio;}
    public void setBeneficio(int beneficio) {this.beneficio = beneficio;}

    // ==========================
    // METODOS logicos
    // ==========================



    // ==========================
    // METODOS DE BD
    // ==========================
    public int getCosto() {return costo;}
    public void setCosto(int costo) {this.costo = costo;}

    public List<Integer> obtenerRandom(){
        String sqlBuscar = "SELECT * FROM CartaNaranja ORDER BY RANDOM() LIMIT 3";
        List<Integer> ids = new ArrayList<>();
        try(Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBuscar)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (Exception e ){
            System.out.println("Error al obtener carta rosa random: " + e.getMessage());
        }
        return ids;
    }

    public static CartaRosa buscarCartaId(int id_npc){

        String sql = "SELECT * FROM CartaRosa WHERE id = ?";
        CartaRosa carta = null;

        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_npc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta = new CartaRosa();
                carta.setId(rs.getInt("id"));
                carta.setDescripcion(rs.getString("descripcion"));
                carta.setTipo(rs.getString("tipo"));
                carta.setCosto(rs.getInt("costo"));
                carta.setBeneficio(rs.getInt("beneficio"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta rosa: " + e.getMessage());
        }

        return carta;

    }


}
