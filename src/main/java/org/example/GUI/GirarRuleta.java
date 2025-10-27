package org.example.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GirarRuleta extends JPanel implements ActionListener {
    private BufferedImage imagen;
    private double angulo = 0;
    private Timer timer;

    public GirarRuleta() {
        try {
            // Carga tu imagen (ajustá la ruta)
            imagen = ImageIO.read(new File("/home/bianca/IdeaProjects/PROGRAMACION-2/src/main/resources/imagenes/ruleta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Timer para animar la rotación
        timer = new Timer(50, this); // cada 50 ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen == null) return;

        Graphics2D g2d = (Graphics2D) g;

        // Suaviza la rotación
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int imgX = imagen.getWidth() / 2;
        int imgY = imagen.getHeight() / 2;

        // Mueve el origen de coordenadas al centro del panel
        g2d.translate(x, y);
        // Rota alrededor del centro
        g2d.rotate(Math.toRadians(angulo));
        // Dibuja la imagen centrada
        g2d.drawImage(imagen, -imgX, -imgY, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angulo += 5; // aumenta el ángulo en cada tick
        if (angulo >= 360) angulo = 0;
        repaint();
    }


}
