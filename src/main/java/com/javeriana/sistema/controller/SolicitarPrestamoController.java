package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.PrestamoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class SolicitarPrestamoController {

    @FXML private TextField txtMonto;
    @FXML private TextField txtTasaInteres;
    @FXML private TextField txtPlazoMeses;
    @FXML private Button btnSolicitar;
    @FXML private ComboBox<CuentaBancaria> comboCuentas;

    private final PrestamoService prestamoService = new PrestamoService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarCuentas(usuario.getId());
    }

    @FXML
    public void initialize() {
        // Solo se cargarán las cuentas si el usuario ya ha sido seteado
        if (usuario != null) {
            cargarCuentas(usuario.getId());
        }
    }

    private void cargarCuentas(int usuarioId) {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.getItems().addAll(cuentas);

        comboCuentas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((empty || cuenta == null) ? null : cuenta.getTipo() + " - $" + String.format("%.2f", cuenta.getSaldo()));
            }
        });

        comboCuentas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((empty || cuenta == null) ? null : cuenta.getTipo() + " - $" + String.format("%.2f", cuenta.getSaldo()));
            }
        });
    }

    @FXML
    private void solicitarPrestamo() {
        if (usuario == null) {
            mostrarAlerta("Error", "No se ha identificado un usuario activo.");
            return;
        }

        try {
            double monto = Double.parseDouble(txtMonto.getText());
            double tasaInteres = Double.parseDouble(txtTasaInteres.getText());
            int plazoMeses = Integer.parseInt(txtPlazoMeses.getText());

            if (!prestamoService.puedeSolicitarPrestamo(usuario.getId())) {
                mostrarAlerta("Préstamo Denegado", "No puedes solicitar más préstamos. Debes pagar tus deudas pendientes.");
                return;
            }

            CuentaBancaria cuenta = comboCuentas.getValue();
            if (cuenta == null) {
                mostrarAlerta("Error", "Debes seleccionar una cuenta para depositar el préstamo.");
                return;
            }

            prestamoService.solicitarPrestamo(usuario.getId(), monto, tasaInteres, plazoMeses);

            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaService.actualizarCuenta(cuenta);

            mostrarAlerta("Éxito", "¡Préstamo solicitado y fondos transferidos a tu cuenta seleccionada!");
            Stage stage = (Stage) btnSolicitar.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingresa valores válidos en todos los campos.");
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
