package com.javeriana.sistema.model;

import java.time.LocalDateTime;

public class Transferencia {
    private int id;
    private int cuentaOrigenId;
    private int cuentaDestinoId;
    private double monto;
    private LocalDateTime fecha;

    public Transferencia(int id, int cuentaOrigenId, int cuentaDestinoId, double monto, LocalDateTime fecha) {
        this.id = id;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(int cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public int getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(int cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "id=" + id +
                ", cuentaOrigenId=" + cuentaOrigenId +
                ", cuentaDestinoId=" + cuentaDestinoId +
                ", monto=" + monto +
                ", fecha=" + fecha +
                '}';
    }
}


