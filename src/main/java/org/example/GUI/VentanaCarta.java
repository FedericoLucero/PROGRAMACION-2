package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase unificada para todas las cartas del juego.
 * Cartas de elección (azul, naranja, rosa) y cartas informativas (amarilla, verde, roja)
 * Obliga al usuario a elegir una opción antes de continuar.
 */
public class VentanaCarta extends JFrame {

    private int seleccion = 0; // Guardará la opción elegida
    private Color colorFondo;

    public VentanaCarta(String titulo, Color colorFondo, String[][] opciones) {
        this.colorFondo = colorFondo;

        setTitle(titulo);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No se puede cerrar
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, opciones.length, 10, 10));
        getContentPane().setBackground(colorFondo);

        // Evita cierre de ventana sin elegir
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                advertencia();
            }
        });

        // Evita perder foco o click fuera
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // No hacer nada
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (seleccion == 0) {
                    advertencia();
                    requestFocus(); // vuelve a pedir foco
                }
            }
        });

        for (int i = 0; i < opciones.length; i++) {
            add(crearPanelOpcion(opciones[i], i + 1));
        }
    }

    private void advertencia() {
        Toolkit.getDefaultToolkit().beep(); // beep de advertencia
        JOptionPane.showMessageDialog(
                null,
                "Debes elegir una opción antes de continuar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private JPanel crearPanelOpcion(String[] descripciones, int numeroBoton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createTitledBorder("Opción " + numeroBoton));

        for (String desc : descripciones) {
            JLabel lbl = new JLabel(desc, SwingConstants.CENTER);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalGlue());
            panel.add(lbl);
        }
        panel.add(Box.createVerticalGlue());

        JButton boton = new JButton("Elegir " + numeroBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> {
            seleccion = numeroBoton; // Guardamos la elección
            dispose(); // Solo se cierra al elegir
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(boton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Muestra la ventana y bloquea la ejecución hasta que se seleccione una opción.
     */
    public int mostrarYEsperarSeleccion() {
        setVisible(true);
        while (seleccion == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        }
        return seleccion;
    }

    /**
     * Método estático para cartas informativas (amarilla, verde, roja).
     */
    public static void mostrarCartaInformativa(String titulo, String mensaje, String instruccion, Color color) {
        JDialog dialog = new JDialog((Frame) null, titulo, true); // modal
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(color);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(color);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel lblMensaje = new JLabel(mensaje, SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruccion = new JLabel(instruccion, SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(lblMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(lblInstruccion);
        panelCentro.add(Box.createVerticalGlue());

        dialog.add(panelCentro, BorderLayout.CENTER);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dialog.dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(color);
        panelBoton.add(btnOk);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        dialog.setVisible(true); // Modal, bloquea hasta presionar OK
    }
}
