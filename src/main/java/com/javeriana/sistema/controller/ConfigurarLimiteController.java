package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class ConfigurarLimiteController {

    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TextField txtLimite;
    @FXML private Button btnGuardar;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    @FXML
    public void initialize() {
        int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);

        if (cuentas == null || cuentas.isEmpty()) {
            mostrarAlerta("Aviso", "No se encontraron cuentas bancarias.");
            return;
        }

        comboCuentas.getItems().setAll(cuentas);

        comboCuentas.setConverter(new StringConverter<>() {
            @Override
            public String toString(CuentaBancaria cuenta) {
                return cuenta == null ? "" : "Cuenta #" + cuenta.getId() + " - " + cuenta.getTipo() + " ($" + cuenta.getSaldo() + ")";
            }

            @Override
            public CuentaBancaria fromString(String string) {
                return null;
            }
        });

        comboCuentas.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (empty || cuenta == null) {
                    setText(null);
                } else {
                    setText("Cuenta #" + cuenta.getId() + " - " + cuenta.getTipo() + " ($" + cuenta.getSaldo() + ")");
                }
            }
        });
    }

    @FXML
    private void guardarLimite() {
        CuentaBancaria cuentaSeleccionada = comboCuentas.getValue();
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
