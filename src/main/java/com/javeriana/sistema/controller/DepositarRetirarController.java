package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepositarRetirarController {

    @FXML
    private Label lblTipoCuenta;

    @FXML
    private Label lblSaldoActual;

    @FXML
    private TextField txtMonto;

    private CuentaBancaria cuentaActual;
    private CuentaBancariaService cuentaService = new CuentaBancariaService();

    private VerCuentasController verCuentasController; // para refrescar luego

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuentaActual = cuenta;
        actualizarVista();
    }

    public void setVerCuentasController(VerCuentasController controller) {
        this.verCuentasController = controller;
    }

    private void actualizarVista() {
        lblTipoCuenta.setText(cuentaActual.getTipo());
        lblSaldoActual.setText(String.format("$ %.2f", cuentaActual.getSaldo()));
    }

    @FXML
    private void depositar() {
        String montoStr = txtMonto.getText();
        if (montoStr.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0.");
                return;
            }
            cuentaActual.setSaldo(cuentaActual.getSaldo() + monto);
            cuentaService.actualizarCuenta(cuentaActual);
            mostrarAlerta("Éxito", "Depósito realizado.");

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Monto inválido.");
        }
    }

    @FXML
    private void retirar() {
        String montoStr = txtMonto.getText();
        if (montoStr.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0 || monto > cuentaActual.getSaldo()) {
                mostrarAlerta("Error", "Monto inválido o saldo insuficiente.");
                return;
            }
            cuentaActual.setSaldo(cuentaActual.getSaldo() - monto);
            cuentaService.actualizarCuenta(cuentaActual);
            mostrarAlerta("Éxito", "Retiro realizado.");

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Monto inválido.");
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblTipoCuenta.getScene().getWindow();
        if (verCuentasController != null) {
            verCuentasController.recargarTabla();
        }
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
