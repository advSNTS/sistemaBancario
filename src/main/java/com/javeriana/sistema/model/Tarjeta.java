package com.javeriana.sistema.model;

public class Tarjeta {
    private int id;
    private int usuarioId;
    private String tipo; // "Débito" o "Crédito"
    private double cupoTotal;
    private double cupoDisponible;
    private double deuda;
    private boolean activa;
    private boolean bloqueada;

    public Tarjeta(int id, int usuarioId, String tipo, double cupoTotal, double cupoDisponible, double deuda, boolean activa, boolean bloqueada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.cupoTotal = cupoTotal;
        this.cupoDisponible = cupoDisponible;
        this.deuda = deuda;
        this.activa = activa;
        this.bloqueada = bloqueada;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(double cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public double getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(double cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
}
