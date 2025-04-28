package com.javeriana.sistema.model;

public class CuentaBancaria {
    private int id;
    private int usuarioId;
    private String tipo;
    private double saldo;

    public CuentaBancaria(int id, int usuarioId, String tipo, double saldo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.saldo = saldo;
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
}
