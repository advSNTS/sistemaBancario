package com.javeriana.sistema.model;

import java.time.LocalDate;

public class Inversion {
    private int id;
    private int cuentaId;
    private double monto;
    private int plazoMeses;
    private double interes;
    private LocalDate fechaInicio;
    private boolean finalizada;

    public Inversion(int id, int cuentaId, double monto, int plazoMeses, double interes, LocalDate fechaInicio, boolean finalizada) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.interes = interes;
        this.fechaInicio = fechaInicio;
        this.finalizada = finalizada;
    }

    public int getId() {
        return id;
    }

    public int getCuentaId() {
        return cuentaId;
    }

    public double getMonto() {
        return monto;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public double getInteres() {
        return interes;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public double calcularValorFinal() {
        return monto + (monto * interes / 100);
    }
}
