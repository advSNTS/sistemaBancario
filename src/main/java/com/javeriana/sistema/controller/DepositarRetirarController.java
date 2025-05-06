package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepositarRetirarController {

    @FXML private Label lblSaldo;
    @FXML private TextField txtMonto;
    @FXML private Label lblLimiteAlerta;

    private CuentaBancaria cuenta;
    private CuentaBancariaService cuentaService = new CuentaBancariaService();
    private VerCuentasController verCuentasController; // Para recargar tabla luego

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
        actualizarLabelSaldo();
    }

    private void actualizarLabelSaldo() {
        lblSaldo.setText("Saldo Actual: $" + String.format("%.2f", cuenta.getSaldo()));
        if (cuenta.getLimiteAlerta() != null) {
            lblLimiteAlerta.setText("Límite de Alerta: $" + String.format("%.2f", cuenta.getLimiteAlerta()));
        } else {
            lblLimiteAlerta.setText("Límite de Alerta: No configurado");
        }
    }

    public void setVerCuentasController(VerCuentasController controller) {
        this.verCuentasController = controller;
    }

    @FXML
    private void depositar() {
        operar(true);
    }

    @FXML
    private void retirar() {
        String montoTexto = txtMonto.getText();
        if (montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Debes ingresar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor que cero.");
                return;
            }

            double saldoActual = cuenta.getSaldo();
            if (monto > saldoActual) {
                mostrarAlerta("Error", "Saldo insuficiente para retirar esa cantidad.");
                return;
            }

            Double limite = cuenta.getLimiteAlerta();
            boolean sobrepasaLimite = limite != null && monto >= limite;
            boolean retiraTodo = Math.abs(monto - saldoActual) < 0.01;

            if (sobrepasaLimite || retiraTodo) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmación requerida");
                confirmacion.setHeaderText("Límite de alerta o retiro total detectado");
                confirmacion.setContentText("Está a punto de retirar una cantidad igual o superior al límite de alerta o el total del saldo. ¿Desea continuar?");
                if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return;
                }
            }

            cuenta.setSaldo(saldoActual - monto);
            cuentaService.actualizarCuenta(cuenta);

            mostrarAlerta("Éxito", "Retiro realizado correctamente.");

            if (verCuentasController != null) {
                verCuentasController.recargarTabla();
            }

            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto ingresado no es válido.");
        }
    }

    private void operar(boolean esDeposito) {
        String montoTexto = txtMonto.getText();
        if (montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Debes ingresar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor que cero.");
                return;
            }

            if (!esDeposito && monto > cuenta.getSaldo()) {
                mostrarAlerta("Error", "Saldo insuficiente para retirar esa cantidad.");
                return;
            }

            double nuevoSaldo = esDeposito ? cuenta.getSaldo() + monto : cuenta.getSaldo() - monto;
            cuenta.setSaldo(nuevoSaldo);

            cuentaService.actualizarCuenta(cuenta);

            mostrarAlerta("Éxito", esDeposito ? "Depósito realizado correctamente." : "Retiro realizado correctamente.");

            if (verCuentasController != null) {
                verCuentasController.recargarTabla();
            }

            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto ingresado no es válido.");
        }
    }

    @FXML
    private void cancelarOperacion() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtMonto.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
