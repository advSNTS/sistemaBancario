package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.InversionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDate;


public class SimularInversionesController {

    @FXML private Button btnSimular;
    @FXML private Label lblResultado;

    private final InversionService inversionService = new InversionService();

    private VerCuentasController verCuentasController;

    // Metodo para inyectar el controlador de VerCuentas desde quien abre la vista
    public void setVerCuentasController(VerCuentasController controller) {
        this.verCuentasController = controller;
    }

    @FXML
    private void simularInversiones() {
        try {
            // Fecha simulada en diciembre de 2027
            LocalDate fechaSimulada = LocalDate.of(2027, 12, 15);
            inversionService.simularFinalizacionConFecha(fechaSimulada);

            lblResultado.setText("Simulación completada con fecha: " + fechaSimulada);

            mostrarAlerta("Éxito", "Las inversiones fueron finalizadas simulando el tiempo hasta " + fechaSimulada + ".");

            if (verCuentasController != null) {
                verCuentasController.recargarTabla();
            }

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
