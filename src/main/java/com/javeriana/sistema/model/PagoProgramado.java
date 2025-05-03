package com.javeriana.sistema.model;

import java.time.LocalDateTime;

public class PagoProgramado {
    private int id;
    private int cuentaOrigenId;
    private String cedulaDestino;
    private double monto;
    private LocalDateTime fechaHoraEjecucion;
    private boolean ejecutado;

    public PagoProgramado(int id, int cuentaOrigenId, String cedulaDestino, double monto, LocalDateTime fechaHoraEjecucion, boolean ejecutado) {
        this.id = id;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cedulaDestino = cedulaDestino;
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

    public String getCedulaDestino() {
        return cedulaDestino;
    }

    public void setCedulaDestino(String cedulaDestino) {
        this.cedulaDestino = cedulaDestino;
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
