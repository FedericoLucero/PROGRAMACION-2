package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaCartaRosa extends JFrame {

    private int seleccion = 0; // Guardará 1, 2 o 3

    public VentanaCartaRosa(String[] desc1, String[] desc2, String[] desc3) {
        setTitle("Elige tu opción");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 3, 10, 10));

        // Fondo rosa
        Color rosa = new Color(255, 182, 193);
        getContentPane().setBackground(rosa);

        // Paneles para las opciones
        JPanel panel1 = crearPanelOpcion(desc1, 1, rosa);
        JPanel panel2 = crearPanelOpcion(desc2, 2, rosa);
        JPanel panel3 = crearPanelOpcion(desc3, 3, rosa);

        add(panel1);
        add(panel2);
        add(panel3);
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
        String[] mascota = {"Mascota: Perro", "Edad: 2 años", "Raza: Labrador"};
        String[] pareja = {"Pareja: Ana", "Edad: 28", "Profesión: Abogada"};
        String[] hijo = {"Hijo: Juan", "Edad: 5", "Escuela: Primaria"};

        VentanaCartaRosa ventana = new VentanaCartaRosa(mascota, pareja, hijo);
        int eleccion = ventana.mostrarYEsperarSeleccion();

        System.out.println("Elegiste la opción: " + eleccion);
    }
}
