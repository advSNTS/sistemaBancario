package com.javeriana.sistema.model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int usuarioId;
    private double monto;
    private double tasaInteres;
    private int plazoMeses;
    private double saldoPendiente;
    private LocalDate fechaAprobacion;

    public Prestamo(int id, int usuarioId, double monto, double tasaInteres, int plazoMeses, double saldoPendiente, LocalDate fechaAprobacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.tasaInteres = tasaInteres;
        this.plazoMeses = plazoMeses;
        this.saldoPendiente = saldoPendiente;
        this.fechaAprobacion = fechaAprobacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }

    public int getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(int plazoMeses) { this.plazoMeses = plazoMeses; }

    public double getSaldoPendiente() { return saldoPendiente; }
    public void setSaldoPendiente(double saldoPendiente) { this.saldoPendiente = saldoPendiente; }

    public LocalDate getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDate fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    @Override
    public String toString() {
        return "Prestamo{id=" + id + ", usuarioId=" + usuarioId + ", monto=" + monto +
                ", tasaInteres=" + tasaInteres + ", plazoMeses=" + plazoMeses +
                ", saldoPendiente=" + saldoPendiente + ", fechaAprobacion=" + fechaAprobacion + "}";
    }
}
