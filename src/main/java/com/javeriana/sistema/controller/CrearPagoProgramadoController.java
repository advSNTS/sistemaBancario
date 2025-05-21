package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.PagoProgramado;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.PagoProgramadoService;
import com.javeriana.sistema.util.UsuarioSesion;
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

    @FXML
    public void initialize() {
        Usuario usuario = UsuarioSesion.getInstancia().getUsuario();
        if (usuario != null) {
            cargarCuentas(usuario.getId());
        } else {
            mostrarAlerta("Error", "No hay un usuario autenticado en sesión.");
        }
    }

    private void cargarCuentas(int usuarioId) {
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
    private void guardarPagoProgramado() {
        CuentaBancaria origen = comboCuentaOrigen.getValue();
        String cedulaDestino = txtCuentaDestinoId.getText().trim();
        String montoTexto = txtMonto.getText();
        LocalDate fecha = dateFechaEjecucion.getValue();
        String horaTexto = txtHoraEjecucion.getText();

        if (origen == null || cedulaDestino.isEmpty() || montoTexto.isEmpty() || fecha == null || horaTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            LocalTime hora = LocalTime.parse(horaTexto); // Formato HH:mm
            LocalDateTime fechaHoraEjecucion = LocalDateTime.of(fecha, hora);

            Usuario usuarioDestino = cuentaService.buscarUsuarioPorCedula(cedulaDestino);
            if (usuarioDestino == null) {
                mostrarAlerta("Error", "No se encontró un usuario con esa cédula.");
                return;
            }

            List<CuentaBancaria> cuentasDestino = cuentaService.obtenerCuentasDeUsuario(usuarioDestino.getId());
            if (cuentasDestino.isEmpty()) {
                mostrarAlerta("Error", "El usuario con esa cédula no tiene cuentas registradas.");
                return;
            }

            CuentaBancaria cuentaDestino = cuentasDestino.get(0); // Tomamos la primera

            PagoProgramado pago = new PagoProgramado(
                    0,
                    origen.getId(),
                    cuentaDestino.getId(),
                    monto,
                    fechaHoraEjecucion,
                    false
            );

            pagoService.guardar(pago);
            mostrarAlerta("Éxito", "Pago programado registrado exitosamente.");
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un número válido.");
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

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentas(usuarioId);
    }
}
