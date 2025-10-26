package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CartaNaranja {
    private int id;
    private String descripcion;
    private int precio_compra;
    private int precio_venta;
    private int nivel;
    final static String url_dinamica = "jdbc:sqlite:BD_LIFE_DINAMIC.sqlite";
    // ==========================
    // GETTERS Y SETTERS
    // ==========================
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getPrecio_compra() {return precio_compra;}
    public void setPrecio_compra(int precio_compra) {this.precio_compra = precio_compra;}

    public int getPrecio_venta() {return precio_venta;}
    public void setPrecio_venta(int precio_venta) {this.precio_venta = precio_venta;}

    public int getNivel() {return nivel;}
    public void setNivel(int nivel) {this.nivel = nivel;}

    // ==========================
    // METODOS PROPIOS
    // ==========================



    // ==========================
    // METODOS DE BD
    // ==========================

    /*
    Obtener random- Argumento:int nivel ,el nivel de casa 1-bajo, 2-medio, 3:alto
    retorna lista de los ids random seleccionados
     */
    public List<Integer> obtenerRandom(int nivel_buscado){
        String sqlBuscar = "SELECT * FROM CartaNaranja WHERE nivel = ? ORDER BY RANDOM() LIMIT 2";
        List<Integer> ids = new ArrayList<>();
        try(Connection conn = new ConexionBD(url_dinamica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBuscar)){
            stmt.setInt(1,nivel_buscado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_carta"));
            }

        } catch (Exception e ){
            System.out.println("Error al obtener casa random: " + e.getMessage());
        }
        return ids;
    }


}
