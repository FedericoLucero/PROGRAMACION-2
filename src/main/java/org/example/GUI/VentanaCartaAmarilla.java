package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import org.example.utils.PantallaColor;

public class VentanaCartaAmarilla {

    /**
     * Muestra una ventana modal con el mensaje y un botón OK.
     * El programa no continúa hasta que se presione OK.
     */
    public static void llamarVentanaAmarilla() {
        // Crear el diálogo modal
        JDialog dialog = new JDialog((Frame) null, "Carta Amarilla", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout(10, 10));

        // Fondo amarillo
        dialog.getContentPane().setBackground(Color.YELLOW);

        // Panel central con etiquetas
        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.YELLOW);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel lblMensaje = new JLabel("Te tocó carta amarilla", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruccion = new JLabel("Debes girar la ruleta (1 a 5)", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(lblMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(lblInstruccion);
        panelCentro.add(Box.createVerticalGlue());

        dialog.add(panelCentro, BorderLayout.CENTER);

        // Botón OK
        JButton btnOk = new JButton("ok");
        btnOk.addActionListener(e -> dialog.dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(PantallaColor.AMARILLO);
        panelBoton.add(btnOk);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        // Mostrar el diálogo (modal)
        dialog.setVisible(true);
    }

    // Main de prueba
    public static void main(String[] args) {
        System.out.println("Antes de mostrar ventana");
        VentanaCartaAmarilla.llamarVentanaAmarilla();
        System.out.println("Después de presionar OK");
    }
}

