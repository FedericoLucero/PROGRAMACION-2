package org.example.model.Piezas.Cartas;

import org.example.bd.ConexionBD;
import org.example.model.Jugadores.Jugador;
import org.example.model.Jugadores.Profesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartaAzul {
   private int id;
   private int nivel;
   private String titulo;
   private int sueldo;
   final static String url_dinamica = "jdbc:sqlite:BD_LIFE_DINAMIC.sqlite";
    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

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
        try(Connection conn = new ConexionBD(url_dinamica).getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBuscar)){
            stmt.setInt(1,nivel_buscado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_carta"));
            }

        } catch (Exception e ){
            System.out.println("Error al obtener profesion random: " + e.getMessage());
        }
        return ids;
    }

}
