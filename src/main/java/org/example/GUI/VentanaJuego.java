package org.example.GUI;

import org.example.bd.ConexionBD;
import org.example.model.Jugadores.Jugador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class VentanaJuego extends JFrame {

    private JButton btnGirarRuleta;
    private JLabel lblResultadoRuleta;
    private JPanel panelDescripcion;
    private JLabel lblDescripcion;
    private JLabel lblDescripcion2;

    private JPanel panelTurno;
    private JLabel lblTurnoJugador;

    private ImageIcon iconTablero;

    private final Random random = new Random();
    private Integer numeroRuleta = null;
    private final Object lock = new Object();

    private JPanel[] panelesJugadores;
    private JLabel[][] labelsJugadores;

    //  Variables de la ruleta animada
    private BufferedImage imagenRuleta;
    private double angulo = 0;
    private Timer timer;
    private RuletaPanel panelRuletaAnimada;


    public VentanaJuego(Jugador[] nombresJugadores) {
        setTitle("Juego Life - Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        cargarImagenes();

        JPanel panelTablero = construirPanelTablero();
        JPanel panelDerecho = construirPanelDerecho(nombresJugadores);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTablero, panelDerecho);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(5);

        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    // ---------------------------------------------------------------------
    private void cargarImagenes() {
        try {
            // Carga ruleta desde recursos o ruta
            java.net.URL ruletaURL = getClass().getResource("/imagenes/ruleta.png");
            if (ruletaURL != null) {
                imagenRuleta = ImageIO.read(ruletaURL);
            } else {
                imagenRuleta = ImageIO.read(new File("src/main/resources/imagenes/ruleta.png"));
            }

            // Carga tablero
            java.net.URL tableroURL = getClass().getResource("/imagenes/tablero.jpg");
            if (tableroURL != null) {
                iconTablero = new ImageIcon(new ImageIcon(tableroURL)
                        .getImage().getScaledInstance(750, 600, Image.SCALE_SMOOTH));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------
    private JPanel construirPanelTablero() {
        JPanel panelTablero = new JPanel(new BorderLayout());
        panelTablero.setBorder(BorderFactory.createTitledBorder("Tablero"));

        JLabel lblTablero = new JLabel(iconTablero);
        lblTablero.setHorizontalAlignment(JLabel.CENTER);
        lblTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTablero.add(lblTablero, BorderLayout.CENTER);

        return panelTablero;
    }

    // ---------------------------------------------------------------------
    private JPanel construirPanelDerecho(Jugador[] nombresJugadores) {
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelDerecho.setPreferredSize(new Dimension(320, 0));

        JPanel panelVertical = new JPanel();
        panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));
        panelVertical.setOpaque(false);

        // --- Jugadores ---
        JPanel panelInfoJugadores = construirPanelJugadores(nombresJugadores);
        panelVertical.add(panelInfoJugadores);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Descripción ---
        panelDescripcion = construirPanelDescripcion();
        panelVertical.add(panelDescripcion);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Turno ---
        panelTurno = construirPanelTurno();
        panelVertical.add(panelTurno);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Ruleta animada ---
        JPanel panelRuleta = construirPanelRuleta();
        panelVertical.add(panelRuleta);

        panelDerecho.add(panelVertical);
        return panelDerecho;
    }

    // ---------------------------------------------------------------------
    private JPanel construirPanelJugadores(Jugador[] jugadores) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Jugadores"));
        panelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelesJugadores = new JPanel[jugadores.length];
        labelsJugadores = new JLabel[jugadores.length][5];

        for (int i = 0; i < jugadores.length; i++) {
            Jugador jugador = jugadores[i];
            JPanel panelJugador = new JPanel();
            panelJugador.setLayout(new BoxLayout(panelJugador, BoxLayout.X_AXIS));
            panelJugador.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblNombre = new JLabel(jugador.getNombre() + ": ");
            panelJugador.add(lblNombre);
            panelJugador.add(Box.createRigidArea(new Dimension(5, 0)));

            JLabel lblPos = new JLabel("Pos: [00]");
            labelsJugadores[i][0] = lblPos;
            panelJugador.add(lblPos);
            panelJugador.add(Box.createRigidArea(new Dimension(5, 0)));

            JLabel lblDolar = new JLabel("💲: [00]");
            labelsJugadores[i][1] = lblDolar;
            panelJugador.add(lblDolar);
            panelJugador.add(Box.createRigidArea(new Dimension(5, 0)));

            JLabel lblDeuda = new JLabel("-💲: [00]");
            labelsJugadores[i][2] = lblDeuda;
            panelJugador.add(lblDeuda);
            panelJugador.add(Box.createRigidArea(new Dimension(5, 0)));

            JLabel lblProfesion = new JLabel("🎓: [Ninguna]");
            labelsJugadores[i][3] = lblProfesion;
            panelJugador.add(lblProfesion);

            panelInfo.add(panelJugador);
            panelInfo.add(Box.createRigidArea(new Dimension(0, 10)));
            panelesJugadores[i] = panelJugador;
        }

        return panelInfo;
    }

    // ---------------------------------------------------------------------
    private JPanel construirPanelDescripcion() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Descripción actual"));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setMaximumSize(new Dimension(250, 200));
        panel.setPreferredSize(new Dimension(250, 200));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDescripcion = new JLabel("Esperando acción...");
        lblDescripcion2 = new JLabel("...");

        panel.add(lblDescripcion);
        panel.add(lblDescripcion2);
        return panel;
    }

    // ---------------------------------------------------------------------
    private JPanel construirPanelTurno() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Turno actual"));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(250, 50));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTurnoJugador = new JLabel("Esperando jugador...");
        lblTurnoJugador.setFont(new Font("Arial", Font.BOLD, 14));
        lblTurnoJugador.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTurnoJugador, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------------------------------------------------
    // Aquí está la ruleta animada integrada
    private JPanel construirPanelRuleta() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Ruleta"));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelRuletaAnimada = new RuletaPanel();
        panelRuletaAnimada.setPreferredSize(new Dimension(200, 200));
        panelRuletaAnimada.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(panelRuletaAnimada, BorderLayout.CENTER);

        lblResultadoRuleta = new JLabel("Número: ");
        lblResultadoRuleta.setHorizontalAlignment(JLabel.CENTER);
        lblResultadoRuleta.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblResultadoRuleta, BorderLayout.NORTH);

        btnGirarRuleta = new JButton("Girar Ruleta");
        panel.add(btnGirarRuleta, BorderLayout.SOUTH);

        // Acción de giro con animación controlada
        btnGirarRuleta.addActionListener(e -> girarRuletaAnimada());

        return panel;
    }

    // ---------------------------------------------------------------------
    //  Gira visualmente la ruleta y calcula número
    private void girarRuletaAnimada() {
        btnGirarRuleta.setEnabled(false);

        int tiempoGiro = 2000 + random.nextInt(2000); // 2-4 segundos
        int velocidad = 15; // ms por frame

        if (timer != null && timer.isRunning()) timer.stop();

        timer = new Timer(velocidad, new ActionListener() {
            long startTime = System.currentTimeMillis();

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                double progreso = (double) elapsed / tiempoGiro;

                if (progreso >= 1.0) {
                    timer.stop();
                    numeroRuleta = random.nextInt(10) + 1;
                    lblResultadoRuleta.setText("Número: " + numeroRuleta);
                    // Notificar al hilo que espera el resultado
                    synchronized (lock) {
                        lock.notify();
                    }
                } else {
                    // Efecto de desaceleración
                    angulo += (10 * (1 - progreso));
                    panelRuletaAnimada.repaint();
                }
            }
        });
        timer.start();
    }

    public int esperarResultadoRuleta() {
        btnGirarRuleta.setEnabled(true);
        numeroRuleta = null; // Resetear el resultado anterior

        synchronized (lock) {
            while (numeroRuleta == null) {
                try {
                    lock.wait(); // Esperar a que la ruleta termine de girar
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return -1; // Salir si el hilo es interrumpido
                }
            }
        }
        return numeroRuleta;
    }

    public void actualizarDatosJugador(Jugador[] jugadores, int indice, int posicion) {
        if (indice < 0 || indice >= jugadores.length) return;

        Jugador jugador = jugadores[indice];

        labelsJugadores[indice][0].setText("Pos: [" + String.format("%02d", posicion) + "]");
        labelsJugadores[indice][1].setText("💲: [" + jugador.getPatrimonio() + "]");
        labelsJugadores[indice][2].setText("-💲: [" + jugador.getDeuda() + "]");
        labelsJugadores[indice][3].setText("🎓: [" + (jugador.getProfesion() != null ? jugador.getProfesion().getTitulo() : "Ninguna") + "]");
    }



    // ---------------------------------------------------------------------
    //  Panel interno que dibuja y rota la ruleta
    private class RuletaPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenRuleta == null) return;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int x = getWidth() / 2;
            int y = getHeight() / 2;
            int imgX = imagenRuleta.getWidth() / 2;
            int imgY = imagenRuleta.getHeight() / 2;

            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(angulo));
            g2d.drawImage(imagenRuleta, -imgX, -imgY, null);
            g2d.dispose();
        }
    }

    // ---------------------------------------------------------------------
    public void setDescripcion(String texto1, String texto2, Color colorFondo) {
        lblDescripcion.setText(texto1);
        lblDescripcion2.setText(texto2);
        panelDescripcion.setBackground(colorFondo);
    }

    public void setTurnoJugador(String nombreJugador) {
        lblTurnoJugador.setText("Turno del jugador: " + nombreJugador);
    }

}