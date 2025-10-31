package codigo.model.Jugadores;

import codigo.bd.ConexionBD;
import codigo.model.Piezas.Cartas.Random.Nivelada.CartaAzul;
import codigo.model.Piezas.Cartas.Random.Nivelada.CartaNaranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jugador {
    private int id_jugador;
    private String nombre;
    private int posicion;

    private int patrimonio;
    private int estado_civil; // 0/1 porque sqlite no tiene booleano
    private int hijos;
    private int deuda;

    private CartaAzul profesion;
    private CartaNaranja casa;
    private int mascota;
    private int ganancia; //Este atributo va contando cuanto gana el jugador con los npc y venta de casa

    // ==========================
    // CONSTRUCTORES
    // ==========================

    public Jugador(int id, String nombre) {
        this.id_jugador = id;
        this.nombre = nombre;
        this.profesion = null;
        this.patrimonio = 0;
        this.posicion = 0;
        this.estado_civil = 0;
        this.hijos = 0;
        this.deuda = 0;
    }
    public Jugador() {}

    public Jugador(String nombre) {
        this(0, nombre);
    }

    public Jugador(int id_jugador, String nombre, int patrimonio) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.patrimonio = patrimonio;
    }

    // ==========================
    // METODOS PROPIOS
    // ==========================

    public void moverJugador(int posicionSiguiente) {
        setPosicion(posicionSiguiente);
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() {return id_jugador;}
    public void setId(int id) {this.id_jugador = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public CartaAzul getProfesion() {return profesion;}
    public void setProfesion(CartaAzul profesion) {this.profesion = profesion;}

    public int getPatrimonio() {return patrimonio;}
    public void setPatrimonio(int patrimonio) {this.patrimonio = patrimonio;}

    public int getPosicion() {return posicion;}
    public void setPosicion(int posicion) {this.posicion = posicion;}

    public int getEstado_civil() {return estado_civil;}
    public void setEstado_civil(int estado_civil) {this.estado_civil = estado_civil;}

    public int getHijos() {return hijos;}
    public void setHijos(int hijos) {this.hijos = hijos;}

    public int getDeuda() {return deuda;}
    public void setDeuda(int deuda) {this.deuda = deuda;}

    public CartaNaranja getCasa() {return casa;}
    public void setCasa(CartaNaranja casa) {this.casa = casa;}

    public int getMascota() {return mascota;}
    public void setMascota(int mascota) {this.mascota = mascota;}

    public int getGanancia() {return ganancia;}
    public void setGanancia(int ganancia) {this.ganancia = ganancia;}

    // ==========================
    // METODOS DE BD
    // ==========================

    /**
     * Inserta el jugador actual en la BD
     */
    public void insertar() {
        String sqlInsertar = "INSERT INTO jugador (nombre, patrimonio, posicion, estado_civil, hijos, deudas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {

            stmt.setString(1, nombre);
            stmt.setInt(2, patrimonio);
            stmt.setInt(3, posicion);
            stmt.setInt(4, estado_civil);
            stmt.setInt(5, hijos);
            stmt.setInt(6, deuda);
            stmt.executeUpdate();
            // Intento normal: getGeneratedKeys()
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    this.id_jugador = rs.getInt(1);
                    System.out.println("Jugador insertado con ID (generatedKeys): " + this.id_jugador);
                    return;
                }
            }

            // Fallback para SQLite
            try (PreparedStatement stmt2 = conn.prepareStatement("SELECT last_insert_rowid() AS id");
                 ResultSet rs2 = stmt2.executeQuery()) {
                if (rs2.next()) {
                    this.id_jugador = rs2.getInt("id");
                    System.out.println("Jugador insertado con ID (last_insert_rowid): " + this.id_jugador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar jugador: " + e.getMessage());
        }
    }

    /**
     * Actualiza campos tipo String
     */
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

        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
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

    /**
     * Actualiza campos tipo int
     */
    public boolean actualizar(String campo, int nuevoValor) {
        String sql;
        switch (campo) {
            case "id_profesion": sql = "UPDATE jugador SET id_profesion=? WHERE id_jugador=?";break;
            case "patrimonio": sql = "UPDATE jugador SET patrimonio=? WHERE id_jugador=?"; break;
            case "estado_civil": sql = "UPDATE jugador SET estado_civil=? WHERE id_jugador=?"; break;
            case "hijos": sql = "UPDATE jugador SET hijos=? WHERE id_jugador=?"; break;
            case "posicion": sql = "UPDATE jugador SET posicion=? WHERE id_jugador=?"; break;
            case "deudas": sql = "UPDATE jugador SET deudas=? WHERE id_jugador=?"; break;
            case "id_casa": sql = "UPDATE jugador SET id_casa=? WHERE id_jugador=?"; break;
            case "ganancia": sql = "UPDATE jugador SET ganancia=? where id_jugador=?"; break;
            default:
                System.err.println("Campo no válido para tipo int: " + campo);
                return false;
        }

        try (Connection conn = new ConexionBD(ConexionBD.url_dinamica).getConnection();
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
                    //Actualizo estos parametros del objeto para evitar errores en calculos
                    case "id_profesion" -> {
                        this.profesion = CartaAzul.buscarCartaId(nuevoValor);
                    }
                    case "id_casa" -> {
                        this.casa = CartaNaranja.buscarCartaId(nuevoValor);
                    }
                    case "ganancia" -> this.ganancia = nuevoValor;
                }
            }
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
            return false;
        }
    }

}
