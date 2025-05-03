package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.PagoProgramado;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.PagoProgramadoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CrearPagoProgramadoController {

    @FXML private ComboBox<CuentaBancaria> comboCuentaOrigen;
    @FXML private TextField txtCuentaDestinoId;
    @FXML private TextField txtMonto;
    @FXML private DatePicker dateFechaEjecucion;
    @FXML private TextField txtHoraEjecucion;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final PagoProgramadoService pagoService = new PagoProgramadoService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentas();
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentaOrigen.getItems().addAll(cuentas);

        comboCuentaOrigen.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (cuenta == null || empty) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - $" + cuenta.getSaldo());
                }
            }
        });

        comboCuentaOrigen.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (cuenta == null || empty) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - $" + cuenta.getSaldo());
                }
            }
        });
    }

    @FXML
    private void guardarPagoProgramado() {
        CuentaBancaria origen = comboCuentaOrigen.getValue();
        String cuentaDestinoTexto = txtCuentaDestinoId.getText();
        String montoTexto = txtMonto.getText();
        LocalDate fecha = dateFechaEjecucion.getValue();
        String horaTexto = txtHoraEjecucion.getText();

        if (origen == null || cuentaDestinoTexto.isEmpty() || montoTexto.isEmpty() || fecha == null || horaTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        try {
            int cuentaDestinoId = Integer.parseInt(cuentaDestinoTexto);
            double monto = Double.parseDouble(montoTexto);
            LocalTime hora = LocalTime.parse(horaTexto); // Formato HH:mm
            LocalDateTime fechaHoraEjecucion = LocalDateTime.of(fecha, hora);

            // Validar que la cuenta destino exista
            CuentaBancaria cuentaDestino = cuentaService.buscarPorId(cuentaDestinoId);
            if (cuentaDestino == null) {
                mostrarAlerta("Error", "La cuenta destino no existe.");
                return;
            }

            PagoProgramado pago = new PagoProgramado(
                    0,
                    origen.getId(),
                    String.valueOf(cuentaDestinoId), // aún usa String como cedula
                    monto,
                    fechaHoraEjecucion,
                    false
            );

            pagoService.guardar(pago);
            mostrarAlerta("Éxito", "Pago programado registrado exitosamente.");
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID de cuenta destino y el monto deben ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Formato de hora inválido. Usa HH:mm (ej: 14:30).");
        }
    }

    private void limpiarCampos() {
        txtCuentaDestinoId.clear();
        txtMonto.clear();
        txtHoraEjecucion.clear();
        dateFechaEjecucion.setValue(null);
        comboCuentaOrigen.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
