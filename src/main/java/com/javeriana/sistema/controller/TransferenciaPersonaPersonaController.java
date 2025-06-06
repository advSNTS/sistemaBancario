package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Movimiento;
import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.MovimientoService;
import com.javeriana.sistema.services.TransferenciaService;
import com.javeriana.sistema.services.UsuarioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class TransferenciaPersonaPersonaController {

    @FXML private ComboBox<CuentaBancaria> comboCuentaOrigen;
    @FXML private TextField txtIdentificadorDestino;
    @FXML private TextField txtMonto;
    @FXML private ChoiceBox<String> choiceTipoBusqueda;
    @FXML private Button btnTransferir;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final TransferenciaService transferenciaService = new TransferenciaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final MovimientoService movimientoService = new MovimientoService();

    private int usuarioId;

    @FXML
    private void initialize() {
        choiceTipoBusqueda.setItems(FXCollections.observableArrayList("Cédula", "ID Cuenta"));
        choiceTipoBusqueda.setValue("Cédula");
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentasOrigen();
    }

    private void cargarCuentasOrigen() {
        comboCuentaOrigen.getItems().clear();
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentaOrigen.setItems(FXCollections.observableArrayList(cuentas));

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
        String identificador = txtIdentificadorDestino.getText();
        String tipoBusqueda = choiceTipoBusqueda.getValue();
        String montoTexto = txtMonto.getText();

        if (origen == null || identificador.isEmpty() || montoTexto.isEmpty()) {
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

            if (origen.getLimiteAlerta() != null && monto >= origen.getLimiteAlerta()) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar Transferencia");
                confirmacion.setHeaderText("El monto supera el límite de alerta: $" + origen.getLimiteAlerta());
                confirmacion.setContentText("¿Desea continuar con la transferencia de $" + monto + "?");
                if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return;
                }
            }

            CuentaBancaria destino = null;

            if (tipoBusqueda.equals("Cédula")) {
                Usuario destinatario = usuarioService.buscarPorCedula(identificador);
                if (destinatario == null) {
                    mostrarAlerta("Error", "No se encontró ningún usuario con esa cédula.");
                    return;
                }
                List<CuentaBancaria> cuentasDestino = cuentaService.obtenerCuentasDeUsuario(destinatario.getId());
                if (cuentasDestino.isEmpty()) {
                    mostrarAlerta("Error", "El usuario destino no tiene cuentas registradas.");
                    return;
                }
                destino = cuentasDestino.get(0);
            } else {
                try {
                    int cuentaId = Integer.parseInt(identificador);
                    destino = cuentaService.obtenerCuentaPorId(cuentaId);
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El ID de cuenta debe ser un número.");
                    return;
                }
            }

            if (destino == null || !destino.isActiva()) {
                mostrarAlerta("Error", "La cuenta destino no existe o está desactivada.");
                return;
            }

            origen.setSaldo(origen.getSaldo() - monto);
            destino.setSaldo(destino.getSaldo() + monto);

            cuentaService.actualizarCuenta(origen);
            cuentaService.actualizarCuenta(destino);

            Transferencia transferencia = new Transferencia(0, origen.getId(), destino.getId(), monto, LocalDateTime.now());
            transferenciaService.registrar(transferencia);

            Movimiento envio = new Movimiento(0, origen.getId(), destino.getId(), "Transferencia P2P - Envío", monto, LocalDateTime.now());
            Movimiento recepcion = new Movimiento(0, origen.getId(), destino.getId(), "Transferencia P2P - Recepción", monto, LocalDateTime.now());

            movimientoService.registrarMovimiento(envio);
            movimientoService.registrarMovimiento(recepcion);

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
