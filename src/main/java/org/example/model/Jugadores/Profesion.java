package org.example.model.Jugadores;

public class Profesion {
    private int id;
    private String descripcion;
    private String titulo;
    private int sueldo;

    public Profesion() {
    }

    public void mostrarProfesion() {
        System.out.println("Profesión: " + titulo);
        System.out.println("Salario: $" + sueldo);
    }

    /**
     * Getters y Setters
     */
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getSueldo() {return sueldo;}
    public void setSueldo(int sueldo) {this.sueldo = sueldo;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
}
