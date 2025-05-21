package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class CrearCuentaController {

    @FXML
    private ComboBox<String> comboTipoCuenta;

    @FXML
    private TextField txtSaldoInicial;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    @FXML
    public void initialize() {
        comboTipoCuenta.getItems().addAll("Ahorro", "Corriente");
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
    }

    @FXML
    private void crearCuenta() {
        String tipo = comboTipoCuenta.getValue();
        String saldoStr = txtSaldoInicial.getText();

        if (tipo == null || saldoStr.isEmpty()) {
            mostrarAlerta("Error", "Debes llenar todos los campos.");
            return;
        }

        try {
            double saldo = Double.parseDouble(saldoStr);

            int idUsuario = usuarioId > 0 ? usuarioId : UsuarioSesion.getInstancia().getUsuario().getId();
            if (idUsuario <= 0) {
                mostrarAlerta("Error", "No se ha identificado un usuario válido.");
                return;
            }

            CuentaBancaria cuenta = new CuentaBancaria(0, idUsuario, tipo, saldo);
            cuentaService.crearCuenta(cuenta);

            mostrarAlerta("Éxito", "Cuenta creada exitosamente.");

            Stage stage = (Stage) txtSaldoInicial.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El saldo debe ser un número válido.");
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
