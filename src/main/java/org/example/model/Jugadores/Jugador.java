package org.example.model.Jugadores;

import org.example.bd.ConexionBD;
import org.example.model.NPCs.NPC;
import org.example.model.Piezas.Cartas.CartaAzul;
import org.example.model.Piezas.Cartas.CartaNaranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Jugador {
    private int id_jugador;
    private String nombre;
    private CartaAzul profesion;
    private int patrimonio;
    private ArrayList<NPC> familia;
    private int posicion;
    private int estado_civil; // 0/1 porque sqlite no tiene booleano
    private int hijos;
    private int deuda;
    private CartaNaranja casa;

    private static final String url_dinamica ="jdbc:sqlite:BD_LIFE_DINAMIC.sqlite";
    // ==========================
    // CONSTRUCTORES
    // ==========================

    public Jugador(int id, String nombre) {
        this.id_jugador = id;
        this.nombre = nombre;
        this.profesion = null;
        this.patrimonio = 0;
        this.familia = new ArrayList<>();
        this.posicion = 0;
        this.estado_civil = 0;
        this.hijos = 0;
        this.deuda = 0;
    }

    public Jugador(String nombre) {
        this(0, nombre);
    }

    public  Jugador(){};

    // ==========================
    // METODOS PROPIOS
    // ==========================

    public void moverJugador(int posicionSiguiente) {
        setPosicion(posicionSiguiente);
    }

    public void actualizarPatrimonio(int sueldo) {
        setPatrimonio(this.patrimonio + sueldo);
    }

    public void mostrarJugador() {
        System.out.println("ID: " + getId() + " | Nombre: " + getNombre() + " | Patrimonio: " + getPatrimonio());
    }

    public void mostrarPatrimonioJugador() {
        System.out.println("Nombre: " + getNombre() + " | Patrimonio: " + getPatrimonio());
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() { return id_jugador; }
    public void setId(int id) { this.id_jugador = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public CartaAzul getProfesion() { return profesion; }
    public void setProfesion(CartaAzul profesion) { this.profesion = profesion; }

    public int getPatrimonio() { return patrimonio; }
    public void setPatrimonio(int patrimonio) { this.patrimonio = patrimonio; }

    public ArrayList<NPC> getFamilia() { return familia; }
    public void setFamilia(ArrayList<NPC> familia) { this.familia = familia; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public int getEstado_civil() { return estado_civil; }
    public void setEstado_civil(int estado_civil) { this.estado_civil = estado_civil; }

    public int getHijos() { return hijos; }
    public void setHijos(int hijos) { this.hijos = hijos; }

    public int getDeuda() { return deuda; }
    public void setDeuda(int deuda) { this.deuda = deuda; }

    public CartaNaranja getCasa() {return casa;}
    public void setCasa(CartaNaranja casa) {this.casa = casa;}

    // ==========================
    // METODOS DE BD
    // ==========================

    /** Inserta el jugador actual en la BD */
    public void insertar() {
        String sqlInsertar = "INSERT INTO jugador (nombre, patrimonio, posicion, estado_civil, hijos, deudas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {

            stmt.setString(1, nombre);
            stmt.setInt(2, patrimonio);
            stmt.setInt(3, posicion);
            stmt.setInt(4, estado_civil);
            stmt.setInt(5, hijos);
            stmt.setInt(6, deuda);
            stmt.executeUpdate();
            System.out.println("Jugador insertado correctamente: " + nombre);

        } catch (SQLException e) {
            System.err.println("Error al insertar jugador: " + e.getMessage());
        }
    }

    /** Actualiza campos tipo String */
    public boolean actualizar(String campo, String nuevoValor) {
        String sql;
        switch (campo) {
            case "nombre":
                sql = "UPDATE jugador SET nombre=? WHERE id_jugador=?";
                break;
            default:
                System.err.println("Campo no válido para tipo String: " + campo);
                return false;
        }

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoValor);
            stmt.setInt(2, id_jugador);
            int filas = stmt.executeUpdate();
            if (filas > 0) this.nombre = nuevoValor;
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
            return false;
        }
    }

    /** Actualiza campos tipo int */
    public boolean actualizar(String campo, int nuevoValor) {
        String sql;
        switch (campo) {
            case "profesion": sql = "UPDATE jugador SET profesion=? WHERE id_jugador=?";break;
            case "patrimonio": sql = "UPDATE jugador SET patrimonio=? WHERE id_jugador=?"; break;
            case "estado_civil": sql = "UPDATE jugador SET estado_civil=? WHERE id_jugador=?"; break;
            case "hijos": sql = "UPDATE jugador SET hijos=? WHERE id_jugador=?"; break;
            case "posicion": sql = "UPDATE jugador SET posicion=? WHERE id_jugador=?"; break;
            case "deudas": sql = "UPDATE jugador SET deudas=? WHERE id_jugador=?"; break;
            default:
                System.err.println("Campo no válido para tipo int: " + campo);
                return false;
        }

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoValor);
            stmt.setInt(2, id_jugador);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                switch (campo) {
                    case "patrimonio" -> this.patrimonio = nuevoValor;
                    case "estado_civil" -> this.estado_civil = nuevoValor;
                    case "hijos" -> this.hijos = nuevoValor;
                    case "posicion" -> this.posicion = nuevoValor;
                    case "deudas" -> this.deuda = nuevoValor;
                }
            }
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
            return false;
        }
    }

    /** Busca un jugador por id en la BD y actualiza el objeto actual
     * Para profesion y casa utiliza los campos profesion y objeto que contienen ids que refieren a los ids de las tablas que les corresponden
     * */
    public static Jugador buscarJugadorPorId(int idJugador) {
        String sql = "SELECT * FROM jugador WHERE id_jugador = ?";
        Jugador jugador = null;

        try (Connection conn = new ConexionBD(url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJugador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugador = new Jugador();
                jugador.setId(rs.getInt("id_jugador"));
                jugador.setNombre(rs.getString("nombre"));
                jugador.setPatrimonio(rs.getInt("patrimonio"));
                jugador.setPosicion(rs.getInt("patrimonio"));
                jugador.setEstado_civil(rs.getInt("estado_civil"));
                jugador.setHijos(rs.getInt("hijos"));
                jugador.setDeuda(rs.getInt("deudas"));


                // Obtenemos los id de profesion y casa
                int idProfesion = rs.getInt("id_profesion");
                int idCasa = rs.getInt("id_casa");

                // Creo objetos a partir de esos id con los metodos de cada carta y los asigno
                CartaAzul cartaAzul = new CartaAzul();
                CartaAzul profesion = cartaAzul.buscarCartaId(idProfesion);
                CartaNaranja cartaNaranja = new CartaNaranja();
                CartaNaranja casa = cartaNaranja.buscarCartaId(idCasa);

                jugador.setProfesion(profesion);
                jugador.setCasa(casa);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar jugador: " + e.getMessage());
        }

        return jugador;
    }


}
