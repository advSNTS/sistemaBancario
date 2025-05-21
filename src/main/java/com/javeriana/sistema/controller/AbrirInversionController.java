package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.InversionService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AbrirInversionController {

    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TextField txtMonto;
    @FXML private ComboBox<String> comboPlazo;
    @FXML private Button btnSimular;
    @FXML private Label lblResultado;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final InversionService inversionService = new InversionService();
    private int usuarioId;

    @FXML
    private void initialize() {
        this.usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarCuentas();
        cargarPlazos();
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.setItems(FXCollections.observableArrayList(cuentas));
    }

    private void cargarPlazos() {
        comboPlazo.setItems(FXCollections.observableArrayList("6 meses", "1 año", "2 años"));
        comboPlazo.getSelectionModel().selectFirst();
    }

    @FXML
    private void simularInversion() {
        CuentaBancaria cuenta = comboCuentas.getValue();
        String montoStr = txtMonto.getText();
        String plazoStr = comboPlazo.getValue();

        if (cuenta == null || montoStr.isEmpty() || plazoStr == null) {
            mostrarAlerta("Error", "Complete todos los campos.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);
            int plazo = plazoStr.equals("6 meses") ? 6 : (plazoStr.equals("1 año") ? 12 : 24);
            double porcentaje = plazo == 6 ? 0.05 : plazo == 12 ? 0.10 : 0.15;
            double retorno = monto * (1 + porcentaje);

            lblResultado.setText("Retorno estimado: $" + String.format("%.2f", retorno));

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Inversión");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Desea proceder con la inversión?");

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                inversionService.crearInversion(cuenta.getId(), monto, plazo);
                mostrarAlerta("Éxito", "Inversión realizada correctamente.");
                txtMonto.clear();
                lblResultado.setText("");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Monto no válido.");
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
