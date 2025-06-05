package com.javeriana.sistema.model;

import java.time.LocalDateTime;

public class Movimiento {

    private int id;
    private Integer cuentaIdOrigen;
    private Integer cuentaIdDestino;
    private String tipo;
    private double monto;
    private LocalDateTime fecha;

    public Movimiento(int id, Integer cuentaIdOrigen, Integer cuentaIdDestino, String tipo, double monto, LocalDateTime fecha) {
        this.id = id;
        this.cuentaIdOrigen = cuentaIdOrigen;
        this.cuentaIdDestino = cuentaIdDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

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
}
