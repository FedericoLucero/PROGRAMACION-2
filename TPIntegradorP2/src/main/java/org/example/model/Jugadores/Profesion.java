package org.example.model.Jugadores;

public class Profesion {
    private String titulo;
    private int salario;

    /**
     * Constructor de Profesion
     * @param titulo titulo de la profesion
     * @param salario salario de la profesion
     */
    public Profesion(String titulo, int salario) {
        this.titulo = titulo;
        this.salario = salario;
    }

    public int getSalario() {return salario;}
    public void setSalario(int salario) {this.salario = salario;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

}
