package org.example.model.Piezas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Ruleta {

    private Random random;
    private int resultado;

    public Ruleta() {
        random = new Random();
    }

    /**
     * Método principal para girar la ruleta y mostrar GUI.
     * BLOQUEA hasta que el jugador presione el botón.
     * @return número salido en la ruleta (1 a 10)
     */
    public int girarConGUI() {
        // Dialogo modal
        JDialog dialog = new JDialog((Frame) null, "🎡 Ruleta", true); // true = modal
        dialog.setAlwaysOnTop(true); // ¡Esto lo pone delante de todo!
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        // Etiqueta para mostrar el resultado
        JLabel resultadoLabel = new JLabel("¡Gira la ruleta!", SwingConstants.CENTER);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 32));
        dialog.add(resultadoLabel, BorderLayout.NORTH);

        // Panel para la imagen de la ruleta
        JLabel imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setPreferredSize(new Dimension(300, 200));
        imagenLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ImageIcon ruletaIcon = new ImageIcon("src/imagenes/ruleta.png");
        imagenLabel.setIcon(ruletaIcon);
        dialog.add(imagenLabel, BorderLayout.CENTER);

        // Botón girar
        JButton girarButton = new JButton("🎲 Girar Ruleta");
        girarButton.setFont(new Font("Arial", Font.BOLD, 18));
        dialog.add(girarButton, BorderLayout.SOUTH);

        girarButton.addActionListener(e -> {
            resultado = random.nextInt(10) + 1;
            resultadoLabel.setText("🎲 = " + resultado);
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return resultado;
    }

}
