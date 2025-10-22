package org.example.ui;

import java.util.Scanner;

public class UserInput {

    private Scanner scanner;

    public UserInput() {
        scanner = new Scanner(System.in);
    }

    // Leer un entero dentro de un rango
    public int getInt(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("⚠️ Error: el número debe estar entre " + min + " y " + max);
                }
            } else {
                System.out.println("⚠️ Error: ingrese un número entero válido");
                scanner.nextLine(); // limpiar buffer
            }
        }
    }

    // Leer un texto que cumpla longitud mínima y máxima
    public String getString(String prompt, int minLength, int maxLength) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.length() >= minLength && input.length() <= maxLength) {
                return input;
            } else {
                System.out.println("⚠️ Error: el texto debe tener entre " + minLength + " y " + maxLength + " caracteres");
            }
        }
    }

    // Cerrar scanner
    public void close() {
        scanner.close();
    }
}

