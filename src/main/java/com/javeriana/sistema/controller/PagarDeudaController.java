package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Movimiento;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.MovimientoService;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;

public class PagarDeudaController {

    @FXML private ComboBox<Tarjeta> comboTarjetas;
    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TextField txtMonto;
    @FXML private Button btnPagar;

    private final TarjetaService tarjetaService = new TarjetaService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final MovimientoService movimientoService = new MovimientoService();
    private int usuarioId;

    @FXML
    public void initialize() {
        if (UsuarioSesion.getInstancia().getUsuario() != null) {
            setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());
        }
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;

        if (usuarioId <= 0) {
            mostrarAlerta("Error", "ID de usuario inválido.");
            return;
        }

        cargarTarjetasConDeuda();
        cargarCuentas();
    }

    private void cargarTarjetasConDeuda() {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId).stream()
                .filter(t -> t.getDeuda() > 0 && "Crédito".equalsIgnoreCase(t.getTipo()))
                .toList();

        comboTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        comboTarjetas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                setText((tarjeta == null || empty) ? null : tarjeta.getTipo() + " - Deuda: $" + tarjeta.getDeuda());
            }
        });

        comboTarjetas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                setText((tarjeta == null || empty) ? null : tarjeta.getTipo() + " - Deuda: $" + tarjeta.getDeuda());
            }
        });

        if (comboTarjetas.getItems().isEmpty()) {
            mostrarAlerta("Información", "No hay tarjetas de crédito con deuda para pagar.");
        }
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.setItems(FXCollections.observableArrayList(cuentas));

        comboCuentas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((cuenta == null || empty)
                        ? null
                        : cuenta.getTipo() + " - Saldo: $" + cuenta.getSaldo());
            }
        });

        comboCuentas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                setText((cuenta == null || empty)
                        ? null
                        : cuenta.getTipo() + " - Saldo: $" + cuenta.getSaldo());
            }
        });

        if (comboCuentas.getItems().isEmpty()) {
            mostrarAlerta("Información", "No hay cuentas disponibles para pagar la deuda.");
        }
    }

    @FXML
    private void pagarDeuda() {
        Tarjeta tarjeta = comboTarjetas.getValue();
        CuentaBancaria cuenta = comboCuentas.getValue();
        String montoTexto = txtMonto.getText();

        if (tarjeta == null || cuenta == null || montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una tarjeta, una cuenta y especificar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a cero.");
                return;
            }

            tarjetaService.pagarDeudaDesdeCuenta(tarjeta.getId(), cuenta.getId(), monto);

            // Registrar movimiento
            Movimiento movimiento = new Movimiento(
                    0,
                    cuenta.getId(),
                    null,
                    "Pago de Tarjeta",
                    monto,
                    LocalDateTime.now()
            );
            movimientoService.registrarMovimiento(movimiento);

            mostrarAlerta("Éxito", "Deuda pagada correctamente.");
            cargarTarjetasConDeuda();
            cargarCuentas();
            txtMonto.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto ingresado no es válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
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
