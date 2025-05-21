package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TransferenciaService;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class TransferenciaPersonaPersonaController {

    @FXML private ComboBox<CuentaBancaria> comboCuentaOrigen;
    @FXML private TextField txtCedulaDestino;
    @FXML private TextField txtMonto;
    @FXML private Button btnTransferir;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final TransferenciaService transferenciaService = new TransferenciaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private int usuarioId;

    @FXML
    private void initialize() {
        int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarCuentasOrigen(usuarioId);
    }

    private void cargarCuentasOrigen(int usuarioId) {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentaOrigen.getItems().addAll(cuentas);

        comboCuentaOrigen.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((cuenta == null || empty) ? null : cuenta.getTipo() + " - $" + cuenta.getSaldo());
            }
        });

        comboCuentaOrigen.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((cuenta == null || empty) ? null : cuenta.getTipo() + " - $" + cuenta.getSaldo());
            }
        });
    }

    @FXML
    private void realizarTransferencia() {
        CuentaBancaria origen = comboCuentaOrigen.getValue();
        String cedulaDestino = txtCedulaDestino.getText();
        String montoTexto = txtMonto.getText();

        if (origen == null || cedulaDestino.isEmpty() || montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
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

            // Validación de límite de alerta
            if (origen.getLimiteAlerta() != null && monto >= origen.getLimiteAlerta()) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar Transferencia");
                confirmacion.setHeaderText("El monto supera el límite de alerta: $" + origen.getLimiteAlerta());
                confirmacion.setContentText("¿Desea continuar con la transferencia de $" + monto + "?");

                if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return;
                }
            }

            // Buscar usuario destino
            Usuario destinatario = usuarioService.buscarPorCedula(cedulaDestino);
            if (destinatario == null) {
                mostrarAlerta("Error", "No se encontró ningún usuario con esa cédula.");
                return;
            }

            List<CuentaBancaria> cuentasDestino = cuentaService.obtenerCuentasDeUsuario(destinatario.getId());
            if (cuentasDestino.isEmpty()) {
                mostrarAlerta("Error", "El usuario destino no tiene cuentas registradas.");
                return;
            }

            CuentaBancaria destino = cuentasDestino.get(0);
            if (!destino.isActiva()) {
                mostrarAlerta("Error", "La cuenta destino está desactivada.");
                return;
            }

            // Ejecutar transferencia
            origen.setSaldo(origen.getSaldo() - monto);
            destino.setSaldo(destino.getSaldo() + monto);

            cuentaService.actualizarCuenta(origen);
            cuentaService.actualizarCuenta(destino);

            Transferencia transferencia = new Transferencia(0, origen.getId(), destino.getId(), monto, LocalDateTime.now());
            transferenciaService.registrar(transferencia);

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

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentasOrigen(usuarioId);
    }
}
