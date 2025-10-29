package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;
import org.example.model.Piezas.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartaAzul extends Carta {

   private int nivel;
   private String titulo;
   private int sueldo;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getNivel() {return nivel;}
    public void setNivel(int nivel) {this.nivel = nivel;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public int getSueldo() {return sueldo;}
    public void setSueldo(int sueldo) {this.sueldo = sueldo;}

    // ==========================
    // METODOS BD
    // ==========================

    /*
    Obtener random- Argumento:int nivel_buscado, el nivel de profesion 1-bajo, 2-medio, 3:alto
    retorna lista de los ids random seleccionados
    */
    public List<Integer> obtenerRandom(int nivel_buscado){
        String sqlBuscar = "SELECT * FROM CartaAzul WHERE nivel = ? ORDER BY RANDOM() LIMIT 2";
        List<Integer> ids = new ArrayList<>();
        try(Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBuscar)){
            stmt.setInt(1,nivel_buscado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (Exception e ){
            System.out.println("Error al obtener profesion random: " + e.getMessage());
        }
        return ids;
    }

    /*
    BuscarCartaId(id_casa): Busca en la bd la casa con el id del argumento y crea un objeto de tipo CartaAzul(profesion)
    del que pueden consultarse propiedades usando getters y setters
     */
    public static CartaAzul buscarCartaId(int id_profesion){

        String sql = "SELECT * FROM CartaAzul WHERE id = ?";
        CartaAzul carta = null;

        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_profesion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta = new CartaAzul();
                carta.setId(rs.getInt("id"));
                carta.setTitulo(rs.getString("titulo"));
                carta.setSueldo(rs.getInt("sueldo"));
                carta.setNivel(rs.getInt("nivel"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta azul: " + e.getMessage());
        }

        return carta;


    }

}
