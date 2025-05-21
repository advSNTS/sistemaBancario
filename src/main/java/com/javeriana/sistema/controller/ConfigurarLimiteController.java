package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ConfigurarLimiteController {

    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TextField txtLimite;
    @FXML private Label lblEstado;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    @FXML
    private void initialize() {
        // No hacer nada aquí directamente. Esperar a que se llame setUsuarioId().
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentas(usuarioId);
    }

    private void cargarCuentas(int usuarioId) {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.setItems(FXCollections.observableArrayList(cuentas));
    }

    @FXML
    private void guardarLimite() {
        CuentaBancaria cuenta = comboCuentas.getValue();
        String limiteStr = txtLimite.getText();

        if (cuenta == null || limiteStr.isEmpty()) {
            mostrarAlerta("Por favor, seleccione una cuenta y escriba un valor.");
            return;
        }

        try {
            double nuevoLimite = Double.parseDouble(limiteStr);
            if (nuevoLimite < 0) throw new NumberFormatException();

            cuenta.setLimiteAlerta(nuevoLimite);
            cuentaService.actualizarCuenta(cuenta);

            lblEstado.setText("Límite actualizado correctamente.");
            txtLimite.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Ingrese un número válido.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
