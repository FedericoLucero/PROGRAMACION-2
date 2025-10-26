package org.example.utils;

import java.util.Scanner;

public class UserInput {

    // Scanner "global" para toda la aplicación
    private static final Scanner scanner = new Scanner(System.in);

    // Leer un entero dentro de un rango
    public static int getInt(String prompt, int min, int max) {
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
    public static String getString(String prompt, int minLength, int maxLength) {
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

    public static boolean getBolean(String prompt) {
        int value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                if (value == 1) {
                    return true;
                } else if  (value == 2) {
                    return false;
                } else {
                    System.out.println("⚠️ Error: el número debe estar entre " + 1 + " y " + 2);
                }
            } else {
                System.out.println("⚠️ Error: ingrese un número entero válido");
                scanner.nextLine(); // limpiar buffer
            }
        }

    }

    // Cerrar scanner
    public static void close() {
        scanner.close();
    }
}

