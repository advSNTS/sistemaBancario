package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.InversionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SimularInversionesController {

    @FXML private Button btnSimular;
    @FXML private Label lblResultado;

    private final InversionService inversionService = new InversionService();

    private VerCuentasController verCuentasController;

    // Método para inyectar el controlador de VerCuentas desde quien abre la vista
    public void setVerCuentasController(VerCuentasController controller) {
        this.verCuentasController = controller;
    }

    @FXML
    private void simularInversiones() {
        try {
            inversionService.simularFinalizacionInversiones();
            lblResultado.setText("Simulación completada. Inversiones finalizadas correctamente.");

            mostrarAlerta("Éxito", "Las inversiones que cumplieron su plazo fueron finalizadas y los fondos retornados.");

            // Refrescar tabla de cuentas si hay un controlador de cuentas conectado
            if (verCuentasController != null) {
                verCuentasController.recargarTabla();
            }

            // Cerrar ventana actual
            Stage stage = (Stage) btnSimular.getScene().getWindow();
            stage.close();

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
