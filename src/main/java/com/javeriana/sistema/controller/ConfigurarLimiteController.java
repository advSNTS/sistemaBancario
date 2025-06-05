package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.List;

public class ConfigurarLimiteController {

    @FXML private ComboBox<CuentaBancaria> comboCuenta;
    @FXML private TextField txtLimite;
    @FXML private Button btnGuardar;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    @FXML
    public void initialize() {
        int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);

        comboCuenta.getItems().setAll(cuentas);

        comboCuenta.setConverter(new StringConverter<>() {
            @Override
            public String toString(CuentaBancaria cuenta) {
                return cuenta == null ? "" : "Cuenta #" + cuenta.getId() + " - " + cuenta.getTipo() + " ($" + cuenta.getSaldo() + ")";
            }

            @Override
            public CuentaBancaria fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void guardarLimite() {
        CuentaBancaria cuentaSeleccionada = comboCuenta.getValue();
        String textoLimite = txtLimite.getText();

        if (cuentaSeleccionada == null || textoLimite.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una cuenta y escribir un límite.");
            return;
        }

        try {
            double nuevoLimite = Double.parseDouble(textoLimite);
            cuentaSeleccionada.setLimiteAlerta(nuevoLimite);
            cuentaService.actualizarCuenta(cuentaSeleccionada);
            mostrarAlerta("Éxito", "Límite de alerta actualizado correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un número válido para el límite.");
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
