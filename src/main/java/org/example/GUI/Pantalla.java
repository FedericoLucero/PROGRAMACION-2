package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pantalla extends JFrame implements ActionListener {

    private JButton btnIniciar;
    private JButton btnCargar;
    private JButton btnSalir;

    public Pantalla() {
        // Configuración de la ventana
        setTitle("El Juego de la Vida");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 230, 250));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Título
        JLabel titulo = new JLabel("LIFE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espacio
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botones
        btnIniciar = new JButton("Iniciar Juego");
        btnCargar = new JButton("Cargar Partida");
        btnSalir = new JButton("Salir");

        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar listeners
        btnIniciar.addActionListener(this);
        btnCargar.addActionListener(this);
        btnSalir.addActionListener(this);

        // Agregar botones al panel
        panel.add(btnIniciar);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(btnCargar);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(btnSalir);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnIniciar) {
            JOptionPane.showMessageDialog(this, "Iniciando nuevo juego...");
            // Aquí podrías abrir la ventana del tablero
        } else if (e.getSource() == btnCargar) {
            JOptionPane.showMessageDialog(this, "Cargando partida...");
            // Aquí podrías conectar con tu base de datos
        } else if (e.getSource() == btnSalir) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Pantalla().setVisible(true);
        });
    }
}
