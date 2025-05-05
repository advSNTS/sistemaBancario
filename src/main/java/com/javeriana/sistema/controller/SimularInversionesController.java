package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.InversionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SimularInversionesController {

    @FXML private Button btnSimular;
    @FXML private Label lblResultado;

    private final InversionService inversionService = new InversionService();

    @FXML
    private void simularInversiones() {
        try {
            inversionService.simularFinalizacionInversiones();
            lblResultado.setText("Simulación completada. Inversiones finalizadas correctamente.");
            mostrarAlerta("Éxito", "Las inversiones que cumplieron su plazo fueron finalizadas y los fondos retornados.");
        } catch (Exception e) {
            lblResultado.setText("Error en la simulación.");
            mostrarAlerta("Error", "Ocurrió un error al simular las inversiones: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
