package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.TarjetaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SolicitarTarjetaController {

    @FXML private ComboBox<String> comboTipoTarjeta;
    @FXML private TextField txtCupo;
    @FXML private Button btnSolicitar;

    private final TarjetaService tarjetaService = new TarjetaService();
    private int usuarioId;

    @FXML
    private void initialize() {
        comboTipoTarjeta.getItems().addAll("Débito", "Crédito");
        comboTipoTarjeta.getSelectionModel().selectFirst();
    }

    @FXML
    private void solicitarTarjeta() {
        String tipoSeleccionado = comboTipoTarjeta.getValue();
        String cupoStr = txtCupo.getText();

        if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de tarjeta.");
            return;
        }

        double cupo;
        try {
            cupo = Double.parseDouble(cupoStr);
            if (cupo <= 0) {
                mostrarAlerta("Error", "El cupo debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un número válido para el cupo.");
            return;
        }

        try {
            Tarjeta tarjeta = tarjetaService.solicitarTarjeta(usuarioId, tipoSeleccionado, cupo);
            mostrarAlerta("Éxito", "La tarjeta fue solicitada exitosamente.\n\n"
                    + "Número: " + tarjeta.getNumero() + "\n"
                    + "Vencimiento: " + tarjeta.getFechaVencimiento() + "\n"
                    + "CVV: " + tarjeta.getCvv());
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al solicitar la tarjeta: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
    }
}
