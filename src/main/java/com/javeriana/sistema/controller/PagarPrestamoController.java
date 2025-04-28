package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Prestamo;
import com.javeriana.sistema.services.PrestamoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PagarPrestamoController {

    @FXML private TextField txtMontoPago;
    @FXML private Button btnConfirmarPago;

    private Prestamo prestamo;
    private VerPrestamosController verPrestamosController;
    private PrestamoService prestamoService = new PrestamoService();

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setVerPrestamosController(VerPrestamosController controller) {
        this.verPrestamosController = controller;
    }

    @FXML
    private void pagarPrestamo() {
        try {
            double montoPago = Double.parseDouble(txtMontoPago.getText());
            if (montoPago <= 0 || montoPago > prestamo.getSaldoPendiente()) {
                mostrarAlerta("Error", "El monto de pago debe ser mayor a 0 y no exceder el saldo pendiente.");
                return;
            }

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
