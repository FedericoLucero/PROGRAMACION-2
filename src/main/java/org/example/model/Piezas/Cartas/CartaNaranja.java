package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;
import org.example.model.Piezas.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CartaNaranja extends Carta {

    private int nivel;
    private String descripcion;
    private int precio_compra;
    private int precio_venta;


    // ==========================
    // GETTERS Y SETTERS
    // ==========================

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
        try(Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBuscar)){
            stmt.setInt(1,nivel_buscado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (Exception e ){
            System.out.println("Error al obtener casa random: " + e.getMessage());
        }
        return ids;
    }
    /*
    BuscarCartaId(id_casa): Busca en la bd la casa con el id del argumento y crea un objeto de tipo CartaNaranja(casa)
    del que pueden consultarse propiedades usando getters y setters
     */
   public static CartaNaranja buscarCartaId(int id_casa){

       String sql = "SELECT * FROM CartaNaranja WHERE id = ?";
       CartaNaranja carta = null;

       try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setInt(1, id_casa);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               carta = new CartaNaranja();
               carta.setId(rs.getInt("id"));
               carta.setDescripcion(rs.getString("descripcion"));
               carta.setPrecio_compra(rs.getInt("precio_compra"));
               carta.setPrecio_venta(rs.getInt("precio_venta"));
               carta.setNivel(rs.getInt("nivel"));
           }

       } catch (Exception e) {
           System.out.println("Error al buscar carta naranja: " + e.getMessage());
       }

       return carta;

   }

}
