package codigo.model.Piezas.Cartas.Random.Nivelada;

import codigo.GUI.VentanaCarta;
import codigo.Juego;
import codigo.bd.ConexionBD;
import codigo.model.Jugadores.Jugador;
import codigo.utils.PantallaColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CartaNaranja extends CartaNivelada {

    private String descripcion;
    private int precio_compra;
    private int precio_venta;

    public CartaNaranja() {
        super();
    }

    public CartaNaranja(int nivel) {
        super(nivel);
    }

    @Override
    public void accion(Jugador jugador) {
        CartaNaranja casa = new CartaNaranja(getNivel());
        List<Integer> ids = casa.obtenerRandom("CartaNaranja",getNivel(),2);

        if (ids.size() < 2) {
            System.out.println("No hay suficientes casas para mostrar.");
            return;
        }

        CartaNaranja casa1 = CartaNaranja.buscarCartaId(ids.get(0));
        CartaNaranja casa2 = CartaNaranja.buscarCartaId(ids.get(1));

        String[][] opciones = {
                {"Propiedad: " + casa1.getDescripcion(), "Precio compra: " + casa1.getPrecio_compra(), "Precio venta: " + casa1.getPrecio_venta()},
                {"Propiedad: " + casa2.getDescripcion(), "Precio compra: " + casa2.getPrecio_compra(), "Precio venta: " + casa2.getPrecio_venta()}
        };

        VentanaCarta ventana = new VentanaCarta("Elige una casa", PantallaColor.NARANJA, opciones);
        int seleccion = ventana.mostrarYEsperarSeleccion();

        CartaNaranja casaElegida = switch (seleccion) {
            case 1 -> casa1;
            case 2 -> casa2;
            default -> null;
        };

        if (casaElegida != null) {
            Juego.cobrarCosto(jugador, casaElegida.getPrecio_compra());
            jugador.actualizar("id_casa", casaElegida.getId());
            // ventanaJuego.actualizarPropiedadJugador(jugador.getId(), casaElegida.getDescripcion());

            int beneficio = casaElegida.getPrecio_venta() + jugador.getGanancia();
            jugador.actualizar("ganancia", beneficio);
        }
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecio_compra() { return precio_compra; }
    public void setPrecio_compra(int precio_compra) { this.precio_compra = precio_compra; }

    public int getPrecio_venta() { return precio_venta; }
    public void setPrecio_venta(int precio_venta) { this.precio_venta = precio_venta; }

    // ==========================
    // METODOS BD
    // ==========================

    public static CartaNaranja buscarCartaId(int id_casa) {
        String sql = "SELECT * FROM CartaNaranja WHERE id = ?";
        CartaNaranja carta = null;

        try (Connection conn = new ConexionBD(ConexionBD.url_estatica).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_casa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carta = new CartaNaranja(rs.getInt("nivel"));
                carta.setId(rs.getInt("id"));
                carta.setDescripcion(rs.getString("descripcion"));
                carta.setPrecio_compra(rs.getInt("precio_compra"));
                carta.setPrecio_venta(rs.getInt("precio_venta"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar carta naranja: " + e.getMessage());
        }

        return carta;
    }
}
