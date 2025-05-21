package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.InversionService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class InversionesController {

    @FXML private Label lblResultadoSimulacion;

    private final InversionService inversionService = new InversionService();
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
