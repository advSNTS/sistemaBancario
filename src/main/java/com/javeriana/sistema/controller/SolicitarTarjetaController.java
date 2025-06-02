package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class SolicitarTarjetaController {

    @FXML private ComboBox<String> comboTipoTarjeta;
    @FXML private TextField txtCupo;
    @FXML private Button btnSolicitar;

    private final TarjetaService tarjetaService = new TarjetaService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    @FXML
    private void initialize() {
        usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        comboTipoTarjeta.getItems().addAll("Débito", "Crédito");
        comboTipoTarjeta.getSelectionModel().selectFirst();
    }

    @FXML
    private void solicitarTarjeta() {
        String tipoSeleccionado = comboTipoTarjeta.getValue();
        String cupoStr = txtCupo.getText();

        if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de tarjeta.");
            return;
        }

        double cupo = 0;
        try {
            cupo = Double.parseDouble(cupoStr);
            if (cupo <= 0) {
                mostrarAlerta("Error", "El cupo debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un número válido para el cupo.");
            return;
        }

        try {
            if ("Débito".equalsIgnoreCase(tipoSeleccionado)) {
                // Buscar cuentas del usuario
                List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
                if (cuentas.isEmpty()) {
                    mostrarAlerta("Error", "Debe tener al menos una cuenta de ahorros para solicitar una tarjeta débito.");
                    return;
                }

                // En un futuro podrías vincularla aquí a una cuenta:
                // cuentaVinculada = cuentas.get(0);

                // Cupo no aplica para débito, pero puedes definir una lógica específica si lo deseas
                cupo = cuentas.get(0).getSaldo(); // o simplemente cupo = 0;

                Tarjeta tarjeta = tarjetaService.solicitarTarjeta(usuarioId, tipoSeleccionado, cupo);
                mostrarAlerta("Éxito", "Tarjeta Débito solicitada y vinculada correctamente.\n\n"
                        + "Número: " + tarjeta.getNumero() + "\n"
                        + "Vencimiento: " + tarjeta.getFechaVencimiento() + "\n"
                        + "CVV: " + tarjeta.getCvv());

            } else {
                // Crédito
                Tarjeta tarjeta = tarjetaService.solicitarTarjeta(usuarioId, tipoSeleccionado, cupo);
                mostrarAlerta("Éxito", "Tarjeta Crédito solicitada correctamente.\n\n"
                        + "Número: " + tarjeta.getNumero() + "\n"
                        + "Vencimiento: " + tarjeta.getFechaVencimiento() + "\n"
                        + "CVV: " + tarjeta.getCvv());
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al solicitar la tarjeta: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
    }
}
