package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.InversionService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.javeriana.sistema.model.Movimiento;
import com.javeriana.sistema.services.MovimientoService;
import java.time.LocalDateTime;


import java.time.LocalDate;

public class InversionesController {

    @FXML private Label lblResultadoSimulacion;

    private final InversionService inversionService = new InversionService();
    private final MovimientoService movimientoService = new MovimientoService();

    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
    }

    @FXML
    private void simularFinalizacion() {
        // Simulación: "forzar avance en el tiempo"
        inversionService.simularFinalizacionConFecha(LocalDate.now().plusYears(3));
        lblResultadoSimulacion.setText("Inversiones finalizadas y saldos actualizados.");
    }
}
