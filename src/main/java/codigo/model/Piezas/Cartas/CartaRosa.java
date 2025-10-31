package codigo.model.Piezas.Cartas;

import codigo.GUI.VentanaCarta;
import codigo.bd.ConexionBD;
import codigo.model.Jugadores.Jugador;
import codigo.model.Piezas.Carta;
import codigo.utils.PantallaColor;
import codigo.Juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartaRosa extends CartaRandom {
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

    @Override
    public void accion(Jugador jugadorTurno) {
        CartaRosa carta = new CartaRosa();
        List<Integer> ids = obtenerRandom("CartaRosa", 3);

        CartaRosa c1 = CartaRosa.buscarCartaId(ids.get(0));
        CartaRosa c2 = CartaRosa.buscarCartaId(ids.get(1));
        CartaRosa c3 = CartaRosa.buscarCartaId(ids.get(2));

        String[][] opcionesRosa = {
                {"Accion: " + c1.getTipo() , c1.getDescripcion() , "Esta accion cuesta: " + c1.getCosto()},
                {"Accion: " + c2.getTipo() , c2.getDescripcion() , "Esta accion cuesta: " + c2.getCosto()},
                {"Accion: " + c3.getTipo() , c3.getDescripcion() , "Esta accion cuesta: " + c3.getCosto()}
        };

        VentanaCarta ventana = new VentanaCarta("Elige tu opción", PantallaColor.ROSA, opcionesRosa);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        //Segun seleccion guardo carta, porque el tipo de carta rosa cambia diferentes campos en jugador
        CartaRosa cartaElegida = switch (seleccion) {
            case 1 -> c1;
            case 2 -> c2;
            case 3 -> c3;
            default -> null;
        };

        if (cartaElegida == null) return;

        // Resto el costo de la accion
        Juego.cobrarCosto(jugadorTurno, cartaElegida.getCosto());

        //Agrego ganancia para calcular ganador
        int beneficio = cartaElegida.getBeneficio() + jugadorTurno.getGanancia();
        jugadorTurno.actualizar("ganancia",beneficio);

        // Actualizo campos dependiendo del tipos
        switch (cartaElegida.getTipo()) {
            case "Adoptar":
                if (cartaElegida.getDescripcion().equalsIgnoreCase("Mellisos")) {
                    jugadorTurno.setHijos(jugadorTurno.getHijos() + 2);
                } else {
                    jugadorTurno.setHijos(jugadorTurno.getHijos() + 1);
                }
                break;

            case "Casarse":
                jugadorTurno.setEstado_civil(1); // 1 casado 0 no
                break;

            case "Adoptar Mascota":
                jugadorTurno.setMascota(cartaElegida.getId());
                break;
        }

        //Actualizo bd dinamica
        String sql = "UPDATE jugador SET patrimonio=?, hijos=?, estado_civil=?, id_mascota=? WHERE id_jugador=?";
        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jugadorTurno.getPatrimonio());
            stmt.setInt(2, jugadorTurno.getHijos());
            stmt.setInt(3, jugadorTurno.getEstado_civil());
            stmt.setInt(4, jugadorTurno.getMascota());
            stmt.setInt(5, jugadorTurno.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al actualizar jugador con carta rosa: " + e.getMessage());
        }
    }


    // ==========================
    // METODOS DE BD
    // ==========================
    public int getCosto() {return costo;}
    public void setCosto(int costo) {this.costo = costo;}

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
