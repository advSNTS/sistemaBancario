package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Prestamo;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.PrestamoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PagarPrestamoController {

    @FXML private TextField txtMontoPago;
    @FXML private Button btnConfirmarPago;
    @FXML private ComboBox<CuentaBancaria> comboCuentas;

    private Prestamo prestamo;
    private VerPrestamosController verPrestamosController;
    private PrestamoService prestamoService = new PrestamoService();
    private CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setVerPrestamosController(VerPrestamosController controller) {
        this.verPrestamosController = controller;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        cargarCuentas();
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);

        if (cuentas.isEmpty()) {
            System.out.println("No se encontraron cuentas para el usuario ID: " + usuarioId);
        } else {
            System.out.println("Se encontraron " + cuentas.size() + " cuentas para el usuario ID: " + usuarioId);
        }

        // Limpiar primero por si ya tenía
        comboCuentas.getItems().clear();
        comboCuentas.getItems().addAll(cuentas);

        // Mostrar bonito
        comboCuentas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (empty || cuenta == null) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - $" + String.format("%.2f", cuenta.getSaldo()));
                }
            }
        });

        comboCuentas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (empty || cuenta == null) {
                    setText(null);
                } else {
                    setText(cuenta.getTipo() + " - $" + String.format("%.2f", cuenta.getSaldo()));
                }
            }
        });
    }


    @FXML
    private void pagarPrestamo() {
        try {
            CuentaBancaria cuentaSeleccionada = comboCuentas.getValue();
            if (cuentaSeleccionada == null) {
                mostrarAlerta("Error", "Debes seleccionar una cuenta bancaria.");
                return;
            }

            double montoPago = Double.parseDouble(txtMontoPago.getText());
            if (montoPago <= 0 || montoPago > prestamo.getSaldoPendiente()) {
                mostrarAlerta("Error", "El monto de pago debe ser mayor a 0 y no exceder el saldo pendiente.");
                return;
            }

            if (cuentaSeleccionada.getSaldo() < montoPago) {
                mostrarAlerta("Error", "Saldo insuficiente en la cuenta seleccionada.");
                return;
            }

            // Actualizar cuenta descontando saldo
            cuentaSeleccionada.setSaldo(cuentaSeleccionada.getSaldo() - montoPago);
            cuentaService.actualizarCuenta(cuentaSeleccionada);

            // Actualizar préstamo
            double nuevoSaldo = prestamo.getSaldoPendiente() - montoPago;
            prestamo.setSaldoPendiente(nuevoSaldo);
            prestamoService.actualizarPrestamo(prestamo);

            mostrarAlerta("Éxito", "Pago realizado correctamente.");

            verPrestamosController.recargarPrestamos();

            Stage stage = (Stage) btnConfirmarPago.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingresa un monto válido.");
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
