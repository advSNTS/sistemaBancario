package com.javeriana.sistema.model;

import java.time.LocalDateTime;

public class Movimiento {

    private int id;
    private Integer cuentaIdOrigen;
    private Integer cuentaIdDestino;
    private String tipo;
    private double monto;
    private LocalDateTime fecha;

    // Constructor completo
    public Movimiento(int id, Integer cuentaIdOrigen, Integer cuentaIdDestino, String tipo, double monto, LocalDateTime fecha) {
        this.id = id;
        this.cuentaIdOrigen = cuentaIdOrigen;
        this.cuentaIdDestino = cuentaIdDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Movimiento() {}

    public int getId() {
        return id;
    }

    public Integer getCuentaIdOrigen() {
        return cuentaIdOrigen;
    }

    public Integer getCuentaIdDestino() {
        return cuentaIdDestino;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCuentaIdOrigen(Integer cuentaIdOrigen) {
        this.cuentaIdOrigen = cuentaIdOrigen;
    }

    public void setCuentaIdDestino(Integer cuentaIdDestino) {
        this.cuentaIdDestino = cuentaIdDestino;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
