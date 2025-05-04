package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TarjetaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class PagarDeudaController {

    @FXML private ComboBox<Tarjeta> comboTarjetas;
    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TextField txtMonto;
    @FXML private Button btnPagar;

    private final TarjetaService tarjetaService = new TarjetaService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarTarjetasConDeuda();
        cargarCuentas();
    }

    private void cargarTarjetasConDeuda() {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId).stream()
                .filter(t -> t.getDeuda() > 0)
                .toList();
        comboTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        comboTarjetas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                if (tarjeta == null || empty) {
                    setText(null);
                } else {
                    String estado = tarjeta.isBloqueada() ? "BLOQUEADA" : (tarjeta.isActiva() ? "ACTIVA" : "INACTIVA");
                    setText(tarjeta.getTipo() + " - Deuda: $" + tarjeta.getDeuda() + " - " + estado);
                }
            }
        });

        comboTarjetas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                if (tarjeta == null || empty) {
                    setText(null);
                } else {
                    String estado = tarjeta.isBloqueada() ? "BLOQUEADA" : (tarjeta.isActiva() ? "ACTIVA" : "INACTIVA");
                    setText(tarjeta.getTipo() + " - Deuda: $" + tarjeta.getDeuda() + " - " + estado);
                }
            }
        });
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.setItems(FXCollections.observableArrayList(cuentas));

        comboCuentas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (cuenta == null || empty) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - Saldo: $" + cuenta.getSaldo());
                }
            }
        });

        comboCuentas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (cuenta == null || empty) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - Saldo: $" + cuenta.getSaldo());
                }
            }
        });
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
