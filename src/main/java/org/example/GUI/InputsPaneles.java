package org.example.GUI;

import org.example.model.Jugadores.Jugador;

import javax.swing.*;

public class InputsPaneles {

    // --- Pedir cantidad y nombres ---
    public static int pedirCantidadJugadores() {
        int cantidad = 0;
        boolean valido = false;
        while (!valido) {
            String input = JOptionPane.showInputDialog(null,
                    "Ingrese la cantidad de jugadores (1-4):",
                    "Configuración de juego", JOptionPane.QUESTION_MESSAGE);
            if (input == null) System.exit(0);
            try {
                cantidad = Integer.parseInt(input);
                if (cantidad >= 1 && cantidad <= 4) valido = true;
                else JOptionPane.showMessageDialog(null, "Debe ser un número entre 1 y 4.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
            }
        }
        return cantidad;
    }

    public static Jugador[] pedirNombresJugadores(int cantidad) {
        Jugador[] jugadores = new Jugador[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String nombre;
            do {
                nombre = JOptionPane.showInputDialog(null,
                        "Ingrese el nombre del jugador " + (i + 1) + ":",
                        "Nombre de jugador", JOptionPane.QUESTION_MESSAGE);
                if (nombre == null) System.exit(0);
                nombre = nombre.trim();
            } while (nombre.isEmpty());

            jugadores[i] = new Jugador(i, nombre);
        }
        return jugadores;
    }

    public static boolean jugarDeNuevo() {
        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿DESEA JUGAR DE NUEVO?",
                "Continuar",
                JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
}
