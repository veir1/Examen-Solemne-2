package me.veir1.municipalidad.entity;

public final class Entity {
    private final String nombre;
    private final String apellido;
    private final char genero;
    private final int edad;

    public Entity(String nombre, String apellido, char genero, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public char getGenero() {
        return genero;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", genero=" + genero +
                ", edad=" + edad +
                '}';
    }
}
