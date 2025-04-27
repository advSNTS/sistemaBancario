package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OperarCuentaController {

    @FXML private Label labelTipoCuenta;
    @FXML private Label labelSaldoActual;
    @FXML private TextField txtMonto;

    private CuentaBancaria cuentaSeleccionada;
    private CuentaBancariaService cuentaService = new CuentaBancariaService();

    public void setCuentaBancaria(CuentaBancaria cuenta) {
        this.cuentaSeleccionada = cuenta;
        labelTipoCuenta.setText("Tipo: " + cuenta.getTipo());
        labelSaldoActual.setText("Saldo actual: $" + cuenta.getSaldo());
    }

    @FXML
    private void depositar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a cero.");
                return;
            }

            cuentaSeleccionada.setSaldo(cuentaSeleccionada.getSaldo() + monto);
            cuentaService.actualizarCuenta(cuentaSeleccionada);

            mostrarAlerta("Éxito", "Depósito realizado correctamente.");
            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Monto inválido.");
        }
    }

    @FXML
    private void retirar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            if (monto <= 0 || monto > cuentaSeleccionada.getSaldo()) {
                mostrarAlerta("Error", "Monto inválido o saldo insuficiente.");
                return;
            }

            cuentaSeleccionada.setSaldo(cuentaSeleccionada.getSaldo() - monto);
            cuentaService.actualizarCuenta(cuentaSeleccionada);

            mostrarAlerta("Éxito", "Retiro realizado correctamente.");
            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Monto inválido.");
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtMonto.getScene().getWindow();
        stage.close();
    }
}
