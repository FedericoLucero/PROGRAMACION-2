package org.example.GUI;

import org.example.model.Jugadores.Jugador;
import org.example.GUI.InputsPaneles.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class VentanaJuego extends JFrame {

    private JButton btnGirarRuleta;
    private JLabel lblRuleta;
    private JLabel lblTablero;
    private JPanel panelInfoJugadores;
    private JLabel lblResultadoRuleta;
    private JPanel panelDescripcion;
    private JLabel lblDescripcion;
    private JLabel lblDescripcion2;

    private JPanel panelTurno;
    private JLabel lblTurnoJugador;

    private ImageIcon iconRuleta;
    private ImageIcon iconTablero;

    private final Random random = new Random();
    private Integer numeroRuleta = null;
    private final Object lock = new Object();

    private JPanel[] panelesJugadores;
    private JLabel[][] labelsJugadores;

    public VentanaJuego(Jugador[] nombresJugadores) {
        setTitle("Juego Life - Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        cargarImagenes();

        // --- Panel izquierdo (tablero) ---
        JPanel panelTablero = construirPanelTablero();

        // --- Panel derecho (jugadores, descripción, turno, ruleta) ---
        JPanel panelDerecho = construirPanelDerecho(nombresJugadores);

        // --- JSplitPane para unir tablero y panel derecho ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTablero, panelDerecho);
        splitPane.setResizeWeight(0.7); // 70% tablero, 30% panel derecho
        splitPane.setDividerSize(5);

        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cargarImagenes() {
        try {
            java.net.URL ruletaURL = getClass().getResource("/imagenes/ruleta.png");
            if (ruletaURL != null) {
                iconRuleta = new ImageIcon(new ImageIcon(ruletaURL)
                        .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            } else System.err.println("No se encontró la imagen de la ruleta");

            java.net.URL tableroURL = getClass().getResource("/imagenes/tablero.jpg");
            if (tableroURL != null) {
                iconTablero = new ImageIcon(new ImageIcon(tableroURL)
                        .getImage().getScaledInstance(750, 600, Image.SCALE_SMOOTH));
            } else System.err.println("No se encontró la imagen del tablero");

        } catch (Exception e) {
            System.err.println("Error al cargar imágenes: " + e.getMessage());
        }
    }

    private JPanel construirPanelTablero() {
        JPanel panelTablero = new JPanel(new BorderLayout());
        panelTablero.setBorder(BorderFactory.createTitledBorder("Tablero"));

        lblTablero = new JLabel(iconTablero);
        lblTablero.setHorizontalAlignment(JLabel.CENTER);
        lblTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTablero.add(lblTablero, BorderLayout.CENTER);

        return panelTablero;
    }

    private JPanel construirPanelDerecho(Jugador[] nombresJugadores) {
        // Panel derecho con FlowLayout centrado
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelDerecho.setPreferredSize(new Dimension(320, 0));

        // Panel vertical donde se añaden subpaneles
        JPanel panelVertical = new JPanel();
        panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));
        panelVertical.setOpaque(false);

        // --- Jugadores ---
        panelInfoJugadores = new JPanel();
        panelInfoJugadores.setLayout(new BoxLayout(panelInfoJugadores, BoxLayout.Y_AXIS));
        panelInfoJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));
        panelInfoJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelesJugadores = new JPanel[nombresJugadores.length];
        labelsJugadores = new JLabel[nombresJugadores.length][5]; // 0=pos, 1=dólares, 2=casas, 3=familia, 4=profesión

        for (int i = 0; i < nombresJugadores.length; i++) {
            Jugador jugador = nombresJugadores[i];
            JPanel panelJugador = new JPanel();
            panelJugador.setLayout(new BoxLayout(panelJugador, BoxLayout.X_AXIS));
            panelJugador.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblNombre = new JLabel(jugador.getNombre() + ": ");
            panelJugador.add(lblNombre);
            panelJugador.add(Box.createRigidArea(new Dimension(5,0)));

            JLabel lblPos = new JLabel("Pos: [00]");
            lblPos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            labelsJugadores[i][0] = lblPos;
            panelJugador.add(lblPos);
            panelJugador.add(Box.createRigidArea(new Dimension(5,0)));

            JLabel lblDolar = new JLabel("💲: [00]");
            lblDolar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            labelsJugadores[i][1] = lblDolar;
            panelJugador.add(lblDolar);
            panelJugador.add(Box.createRigidArea(new Dimension(5,0)));

            JLabel lblCasa = new JLabel("🏠: [00]");
            lblCasa.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            labelsJugadores[i][2] = lblCasa;
            panelJugador.add(lblCasa);
            panelJugador.add(Box.createRigidArea(new Dimension(5,0)));

            JLabel lblFamilia = new JLabel("👪: [00]");
            lblFamilia.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            labelsJugadores[i][3] = lblFamilia;
            panelJugador.add(lblFamilia);
            panelJugador.add(Box.createRigidArea(new Dimension(5,0)));

            JLabel lblProfesion = new JLabel("🎓: [Ninguna]");
            lblProfesion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            labelsJugadores[i][4] = lblProfesion;
            panelJugador.add(lblProfesion);

            panelInfoJugadores.add(panelJugador);
            panelInfoJugadores.add(Box.createRigidArea(new Dimension(0, 10)));
            panelesJugadores[i] = panelJugador;
        }

        panelVertical.add(panelInfoJugadores);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Descripción ---
        panelDescripcion = new JPanel();
        panelDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción actual"));
        panelDescripcion.setBackground(Color.LIGHT_GRAY);
        panelDescripcion.setMaximumSize(new Dimension(250, 200));
        panelDescripcion.setPreferredSize(new Dimension(250, 200));
        panelDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelLabels.setOpaque(false);

        lblDescripcion = new JLabel("Esperando acción...");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        lblDescripcion2 = new JLabel("...");
        lblDescripcion2.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion2.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescripcion2.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panelLabels.add(lblDescripcion);
        panelLabels.add(Box.createRigidArea(new Dimension(0, 10)));
        panelLabels.add(lblDescripcion2);

        panelDescripcion.setLayout(new BorderLayout());
        panelDescripcion.add(panelLabels, BorderLayout.CENTER);

        panelVertical.add(panelDescripcion);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Turno ---
        panelTurno = new JPanel();
        panelTurno.setBorder(BorderFactory.createTitledBorder("Turno actual"));
        panelTurno.setBackground(Color.WHITE);
        panelTurno.setMaximumSize(new Dimension(250, 50));
        panelTurno.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTurnoJugador = new JLabel("Esperando jugador...");
        lblTurnoJugador.setFont(new Font("Arial", Font.BOLD, 14));
        lblTurnoJugador.setHorizontalAlignment(SwingConstants.CENTER);

        panelTurno.setLayout(new BorderLayout());
        panelTurno.add(lblTurnoJugador, BorderLayout.CENTER);

        panelVertical.add(panelTurno);
        panelVertical.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Ruleta ---
        JPanel panelRuleta = new JPanel(new BorderLayout(5, 5));
        panelRuleta.setBorder(BorderFactory.createTitledBorder("Ruleta"));
        panelRuleta.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblRuleta = new JLabel(iconRuleta);
        lblRuleta.setHorizontalAlignment(JLabel.CENTER);
        lblRuleta.setPreferredSize(new Dimension(200, 200));
        lblRuleta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelRuleta.add(lblRuleta, BorderLayout.CENTER);

        lblResultadoRuleta = new JLabel("Número: ");
        lblResultadoRuleta.setHorizontalAlignment(JLabel.CENTER);
        lblResultadoRuleta.setFont(new Font("Arial", Font.BOLD, 18));
        panelRuleta.add(lblResultadoRuleta, BorderLayout.NORTH);

        btnGirarRuleta = new JButton("Girar Ruleta");
        panelRuleta.add(btnGirarRuleta, BorderLayout.SOUTH);

        panelVertical.add(panelRuleta);

        // --- Añadimos el panel vertical al contenedor centrado ---
        panelDerecho.add(panelVertical);

        return panelDerecho;
    }

    // --- Métodos auxiliares ---
    public void setDescripcion(String texto1, String texto2, Color colorFondo) {
        lblDescripcion.setText(texto1);
        lblDescripcion2.setText(texto2);
        panelDescripcion.setBackground(colorFondo);
    }

    public int girarRuletaSync() {
        btnGirarRuleta.setEnabled(true);
        numeroRuleta = null;
        for (var al : btnGirarRuleta.getActionListeners()) btnGirarRuleta.removeActionListener(al);

        btnGirarRuleta.addActionListener(e -> {
            int numero = random.nextInt(10) + 1;
            btnGirarRuleta.setEnabled(false);
            lblResultadoRuleta.setText("Número: " + numero);
            synchronized (lock) {
                numeroRuleta = numero;
                lock.notify();
            }
        });

        synchronized (lock) {
            while (numeroRuleta == null) {
                try { lock.wait(); } catch (InterruptedException ex) { ex.printStackTrace(); }
            }
        }
        return numeroRuleta;
    }

    public void setTurnoJugador(String nombreJugador) {
        lblTurnoJugador.setText("Turno del jugador: " + nombreJugador);
    }

    public void actualizarValoresJugador(int indice, int posicion, int dolares, int casas, int familia) {
        if (indice < 0 || indice >= labelsJugadores.length) return;
        labelsJugadores[indice][0].setText("Pos: [" + String.format("%02d", posicion) + "]");
        labelsJugadores[indice][1].setText("💲: [" + dolares + "]");
        labelsJugadores[indice][2].setText("🏠: [" + String.format("%02d", casas) + "]");
        labelsJugadores[indice][3].setText("👨‍👩‍👦: [" + String.format("%02d", familia) + "]");
    }

    public void actualizarProfesionJugador(int indice, String profesion) {
        if (indice < 0 || indice >= labelsJugadores.length) return;
        labelsJugadores[indice][4].setText("🎓: [" + profesion + "]");
    }
}
