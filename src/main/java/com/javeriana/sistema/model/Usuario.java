package com.javeriana.sistema.model;

public class Usuario {
    private String cedula;
    private String nombre;
    private String apellido;
    private String contrasena;

    public Usuario(String cedula, String nombre, String apellido, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "Usuario{cedula='" + cedula + "', nombre='" + nombre + "', apellido='" + apellido + "'}";
    }
}