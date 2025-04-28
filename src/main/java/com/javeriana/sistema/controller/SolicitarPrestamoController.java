package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.PrestamoService;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.model.CuentaBancaria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class SolicitarPrestamoController {

    @FXML private TextField txtMonto;
    @FXML private TextField txtTasaInteres;
    @FXML private TextField txtPlazoMeses;
    @FXML private Button btnSolicitar;

    private Usuario usuario;

    private PrestamoService prestamoService = new PrestamoService();
    private CuentaBancariaService cuentaService = new CuentaBancariaService();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void solicitarPrestamo() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            double tasaInteres = Double.parseDouble(txtTasaInteres.getText());
            int plazoMeses = Integer.parseInt(txtPlazoMeses.getText());

            if (!prestamoService.puedeSolicitarPrestamo(usuario.getId())) {
                mostrarAlerta("Préstamo Denegado", "No puedes solicitar más préstamos. Debes pagar tus deudas pendientes.");
                return;
            }

            // Registrar el préstamo
            prestamoService.solicitarPrestamo(usuario.getId(), monto, tasaInteres, plazoMeses);

            // Transferir el monto a la cuenta bancaria principal
            List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuario.getId());
            if (!cuentas.isEmpty()) {
                CuentaBancaria cuenta = cuentas.get(0); // Depositar en la primera cuenta que encuentre
                double nuevoSaldo = cuenta.getSaldo() + monto;
                cuenta.setSaldo(nuevoSaldo);
                cuentaService.actualizarCuenta(cuenta);
            }

            mostrarAlerta("Éxito", "¡Préstamo solicitado y fondos transferidos a tu cuenta!");

            Stage stage = (Stage) btnSolicitar.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingresa valores válidos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
