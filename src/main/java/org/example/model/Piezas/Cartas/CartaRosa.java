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
            System.out.println("Error al obtener relacion random: " + e.getMessage());
        }
        return ids;
    }




}
