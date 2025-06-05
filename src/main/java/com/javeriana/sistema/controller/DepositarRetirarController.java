package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DepositarRetirarController {

    @FXML private Label lblSaldo;
    @FXML private TextField txtMonto;
    @FXML private Label lblLimiteAlerta;

    private CuentaBancaria cuenta;
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private VerCuentasController verCuentasController;

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuentaService.obtenerCuentaPorId(cuenta.getId());
        actualizarLabelSaldo();
    }

    public void setVerCuentasController(VerCuentasController controller) {
        this.verCuentasController = controller;
    }

    private void actualizarLabelSaldo() {
        lblSaldo.setText("Saldo Actual: $" + String.format("%.2f", cuenta.getSaldo()));
        lblLimiteAlerta.setText(cuenta.getLimiteAlerta() != null ?
                "Límite de Alerta: $" + String.format("%.2f", cuenta.getLimiteAlerta()) :
                "Límite de Alerta: No configurado");
    }

    @FXML
    private void depositar() {
        operar(true);
    }

    @FXML
    private void retirar() {
        operar(false);
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

            boolean sobrepasaLimite = !esDeposito && cuenta.getLimiteAlerta() != null && monto >= cuenta.getLimiteAlerta();
            boolean retiraTodo = !esDeposito && Math.abs(monto - cuenta.getSaldo()) < 0.01;

            if (sobrepasaLimite || retiraTodo) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmación requerida");
                confirmacion.setHeaderText("Operación sensible detectada");
                confirmacion.setContentText("La operación supera el límite de alerta o retira todo el saldo. ¿Desea continuar?");
                if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return;
                }
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
