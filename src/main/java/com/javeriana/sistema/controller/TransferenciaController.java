package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TransferenciaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class TransferenciaController {

    @FXML private ComboBox<CuentaBancaria> comboOrigen;
    @FXML private ComboBox<CuentaBancaria> comboDestino;
    @FXML private TextField txtMonto;
    @FXML private Button btnTransferir;

    private CuentaBancariaService cuentaService = new CuentaBancariaService();
    private TransferenciaService transferenciaService = new TransferenciaService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentas();
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboOrigen.getItems().addAll(cuentas);
        comboDestino.getItems().addAll(cuentas);
    }

    @FXML
    private void realizarTransferencia() {
        CuentaBancaria origen = comboOrigen.getValue();
        CuentaBancaria destino = comboDestino.getValue();
        String montoTexto = txtMonto.getText();

        if (origen == null || destino == null || montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        if (origen.getId() == destino.getId()) {
            mostrarAlerta("Error", "Las cuentas deben ser diferentes.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser positivo.");
                return;
            }

            if (origen.getSaldo() < monto) {
                mostrarAlerta("Error", "Saldo insuficiente en la cuenta origen.");
                return;
            }

            origen.setSaldo(origen.getSaldo() - monto);
            destino.setSaldo(destino.getSaldo() + monto);

            cuentaService.actualizarCuenta(origen);
            cuentaService.actualizarCuenta(destino);

            Transferencia t = new Transferencia(0, origen.getId(), destino.getId(), monto, LocalDateTime.now());
            transferenciaService.registrar(t);

            mostrarAlerta("Éxito", "Transferencia realizada correctamente.");

            ((Stage) btnTransferir.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un número válido.");
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
