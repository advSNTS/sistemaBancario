package com.javeriana.sistema.model;

import java.time.LocalDate;

public class Tarjeta {
    private int id;
    private int usuarioId;
    private String tipo; // "Débito" o "Crédito"
    private String estado;
    private double cupoTotal;
    private double cupoDisponible;
    private double deuda;
    private boolean activa;
    private boolean bloqueada;
    private String numero;
    private LocalDate fechaVencimiento; // CAMBIADO de String a LocalDate
    private String cvv;

    public Tarjeta(int id, int usuarioId, String tipo, String estado, double cupoTotal, double cupoDisponible,
                   double deuda, boolean activa, boolean bloqueada, String numero, LocalDate fechaVencimiento, String cvv) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.estado = estado;
        this.cupoTotal = cupoTotal;
        this.cupoDisponible = cupoDisponible;
        this.deuda = deuda;
        this.activa = activa;
        this.bloqueada = bloqueada;
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
