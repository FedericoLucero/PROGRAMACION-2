package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaCartaNaranja extends JFrame {

    private int seleccion = 0; // Guarda 1 o 2

    public VentanaCartaNaranja(String[] desc1, String[] desc2) {
        setTitle("Elige una casa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10));

        // Fondo naranja
        Color naranja = new Color(255, 165, 0);
        getContentPane().setBackground(naranja);

        // Panel para la opción 1
        JPanel panel1 = crearPanelOpcion(desc1, 1, naranja);
        // Panel para la opción 2
        JPanel panel2 = crearPanelOpcion(desc2, 2, naranja);

        add(panel1);
        add(panel2);
    }

    private JPanel crearPanelOpcion(String[] descripciones, int numeroBoton, Color fondo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createTitledBorder("Opción " + numeroBoton));

        // Etiquetas con descripciones centradas
        for (String desc : descripciones) {
            JLabel lbl = new JLabel(desc, SwingConstants.CENTER);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalGlue());
            panel.add(lbl);
        }
        panel.add(Box.createVerticalGlue());

        // Botón inferior
        JButton boton = new JButton("Elegir " + numeroBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener((ActionEvent e) -> {
            seleccion = numeroBoton;
            dispose(); // Cierra la ventana
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(boton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    public int mostrarYEsperarSeleccion() {
        setVisible(true);
        // Espera hasta que se elija una opción
        while (isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
        return seleccion;
    }

    // Main de prueba
    public static void main(String[] args) {
        String[] casa1 = {"Casa: Chalet", "Valor: $200.000", "Ubicación: Centro"};
        String[] casa2 = {"Casa: Departamento", "Valor: $150.000", "Ubicación: Playa"};

        VentanaCartaNaranja ventana = new VentanaCartaNaranja(casa1, casa2);
        int eleccion = ventana.mostrarYEsperarSeleccion();

        System.out.println("Elegiste la opción: " + eleccion);
    }
}
