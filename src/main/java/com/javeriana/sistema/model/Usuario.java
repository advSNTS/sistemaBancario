package com.javeriana.sistema.model;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private String preguntaSecreta;
    private String respuestaSecreta;
    private String cedula;

    // Constructor completo
    public Usuario(int id, String nombre, String correo, String clave, String preguntaSecreta, String respuestaSecreta, String cedula) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.preguntaSecreta = preguntaSecreta;
        this.respuestaSecreta = respuestaSecreta;
        this.cedula = cedula;
    }

    // Constructor compatible sin cédula
    public Usuario(int id, String nombre, String correo, String clave, String preguntaSecreta, String respuestaSecreta) {
        this(id, nombre, correo, clave, preguntaSecreta, respuestaSecreta, "");
    }

    // Constructor reducido (sin pregunta y respuesta secreta)
    public Usuario(int id, String nombre, String correo, String clave) {
        this(id, nombre, correo, clave, "", "", "");
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getPreguntaSecreta() { return preguntaSecreta; }
    public void setPreguntaSecreta(String preguntaSecreta) { this.preguntaSecreta = preguntaSecreta; }

    public String getRespuestaSecreta() { return respuestaSecreta; }
    public void setRespuestaSecreta(String respuestaSecreta) { this.respuestaSecreta = respuestaSecreta; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', correo='" + correo + "'}";
    }
}
