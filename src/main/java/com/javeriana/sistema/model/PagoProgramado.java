package com.javeriana.sistema.model;

import java.time.LocalDateTime;

public class PagoProgramado {
    private int id;
    private int cuentaOrigenId;
    private int cuentaDestinoId;
    private double monto;
    private LocalDateTime fechaHoraEjecucion;
    private boolean ejecutado;

    public PagoProgramado(int id, int cuentaOrigenId, int cuentaDestinoId, double monto, LocalDateTime fechaHoraEjecucion, boolean ejecutado) {
        this.id = id;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.monto = monto;
        this.fechaHoraEjecucion = fechaHoraEjecucion;
        this.ejecutado = ejecutado;
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

    public LocalDateTime getFechaHoraEjecucion() {
        return fechaHoraEjecucion;
    }

    public void setFechaHoraEjecucion(LocalDateTime fechaHoraEjecucion) {
        this.fechaHoraEjecucion = fechaHoraEjecucion;
    }

    public boolean isEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(boolean ejecutado) {
        this.ejecutado = ejecutado;
    }
}
