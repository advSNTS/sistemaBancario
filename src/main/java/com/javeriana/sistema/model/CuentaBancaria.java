package com.javeriana.sistema.model;

public class CuentaBancaria {
    private int id;
    private int usuarioId;
    private String tipo;
    private double saldo;
    private boolean activa = true;
    private Double limiteAlerta; // Nuevo campo

    public CuentaBancaria(int id, int usuarioId, String tipo, double saldo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    // Constructor extendido
    public CuentaBancaria(int id, int usuarioId, String tipo, double saldo, Double limiteAlerta) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.saldo = saldo;
        this.limiteAlerta = limiteAlerta;
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public Double getLimiteAlerta() {
        return limiteAlerta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public void setLimiteAlerta(Double limiteAlerta) {
        this.limiteAlerta = limiteAlerta;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", tipo='" + tipo + '\'' +
                ", saldo=" + saldo +
                ", activa=" + activa +
                ", limiteAlerta=" + limiteAlerta +
                '}';
    }
}
