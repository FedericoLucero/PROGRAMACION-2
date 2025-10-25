package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class VentanaJuego extends JFrame {

    private JButton btnGirarRuleta;
    private JLabel lblRuleta;
    private JLabel lblTablero;
    private JPanel panelInfoJugadores;
    private JLabel lblResultadoRuleta; // nuevo label para mostrar el número

    private ImageIcon iconRuleta;
    private ImageIcon iconTablero;

    private final Random random = new Random();
    private Integer numeroRuleta = null;
    private final Object lock = new Object();

    public VentanaJuego() {
        setTitle("Juego Life - Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        cargarImagenes();
        construirPanelTablero();
        construirPanelDerecho();

        setVisible(true);
    }

    private void cargarImagenes() {
        try {
            // Cargar ruleta
            java.net.URL ruletaURL = getClass().getResource("/imagenes/ruleta.png");
            if (ruletaURL != null) {
                iconRuleta = new ImageIcon(ruletaURL);
                iconRuleta = new ImageIcon(iconRuleta.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            } else {
                System.err.println("No se encontró la imagen de la ruleta");
            }

            // Cargar tablero
            java.net.URL tableroURL = getClass().getResource("/imagenes/tablero.jpg");
            if (tableroURL != null) {
                iconTablero = new ImageIcon(tableroURL);
                iconTablero = new ImageIcon(iconTablero.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
            } else {
                System.err.println("No se encontró la imagen del tablero");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar imágenes: " + e.getMessage());
        }
    }


    private void construirPanelTablero() {
        JPanel panelTablero = new JPanel(new BorderLayout());
        panelTablero.setBorder(BorderFactory.createTitledBorder("Tablero"));

        lblTablero = new JLabel(iconTablero);
        lblTablero.setHorizontalAlignment(JLabel.CENTER);
        lblTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTablero.add(lblTablero, BorderLayout.CENTER);

        add(panelTablero, BorderLayout.CENTER);
    }

    private void construirPanelDerecho() {
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(250, 0));

        panelInfoJugadores = new JPanel();
        panelInfoJugadores.setLayout(new BoxLayout(panelInfoJugadores, BoxLayout.Y_AXIS));
        panelInfoJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));

        for (int i = 1; i <= 4; i++) {
            JLabel lblJugador = new JLabel("Jugador " + i + ": [info]");
            lblJugador.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelInfoJugadores.add(lblJugador);
            panelInfoJugadores.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelDerecho.add(panelInfoJugadores);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelRuleta = new JPanel(new BorderLayout(5, 5));
        panelRuleta.setBorder(BorderFactory.createTitledBorder("Ruleta"));

        lblRuleta = new JLabel(iconRuleta);
        lblRuleta.setHorizontalAlignment(JLabel.CENTER);
        lblRuleta.setPreferredSize(new Dimension(200, 200));
        lblRuleta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelRuleta.add(lblRuleta, BorderLayout.CENTER);

        // Label para mostrar resultado de la ruleta
        lblResultadoRuleta = new JLabel("Número: ");
        lblResultadoRuleta.setHorizontalAlignment(JLabel.CENTER);
        lblResultadoRuleta.setFont(new Font("Arial", Font.BOLD, 18));
        panelRuleta.add(lblResultadoRuleta, BorderLayout.NORTH); // arriba de la ruleta

        btnGirarRuleta = new JButton("Girar Ruleta");
        panelRuleta.add(btnGirarRuleta, BorderLayout.SOUTH);

        panelDerecho.add(panelRuleta);
        add(panelDerecho, BorderLayout.EAST);
    }

    /**
     * Gira la ruleta de forma "sincrónica", mostrando el número en la ventana.
     */
    public int girarRuletaSync() {
        btnGirarRuleta.setEnabled(true);
        numeroRuleta = null;
        lblResultadoRuleta.setText("Número: ..."); // limpiar resultado previo

        for (var al : btnGirarRuleta.getActionListeners()) {
            btnGirarRuleta.removeActionListener(al);
        }

        btnGirarRuleta.addActionListener(e -> {
            int numero = random.nextInt(10) + 1;
            btnGirarRuleta.setEnabled(false);

            lblResultadoRuleta.setText("Número: " + numero); // mostrar número en la ventana

            synchronized (lock) {
                numeroRuleta = numero;
                lock.notify();
            }
        });

        synchronized (lock) {
            while (numeroRuleta == null) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return numeroRuleta;
    }

    public static void main(String[] args) {
        VentanaJuego ventana = new VentanaJuego();

        // Ahora podemos obtener el número directamente y mostrarlo en main si queremos
        int numero = ventana.girarRuletaSync();
        System.out.println("Número obtenido: " + numero);
    }
}
