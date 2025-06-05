package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class SolicitarTarjetaController {

    @FXML private ComboBox<String> comboTipoTarjeta;
    @FXML private TextField txtCupo;
    @FXML private Button btnSolicitar;
    @FXML private Label lblCuenta;
    @FXML private ComboBox<CuentaBancaria> comboCuenta;

    private final TarjetaService tarjetaService = new TarjetaService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    private int usuarioId;

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @FXML
    public void initialize() {
        comboTipoTarjeta.setItems(FXCollections.observableArrayList("Crédito", "Débito"));
    }

    @FXML
    private void onTipoSeleccionado() {
        String tipo = comboTipoTarjeta.getValue();
        boolean esDebito = "Débito".equalsIgnoreCase(tipo);

        lblCuenta.setVisible(esDebito);
        comboCuenta.setVisible(esDebito);

        if (esDebito) {
            Usuario usuario = UsuarioSesion.getInstancia().getUsuario();
            if (usuario != null) {
                List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuario.getId());
                comboCuenta.setItems(FXCollections.observableArrayList(cuentas));
            } else {
                mostrarAlerta("Error", "No se ha identificado un usuario activo.");
            }
        }
    }

    @FXML
    private void solicitarTarjeta() {
        Usuario usuario = UsuarioSesion.getInstancia().getUsuario();

        if (usuario == null) {
            mostrarAlerta("Error", "No se ha identificado un usuario activo.");
            return;
        }

        String tipoSeleccionado = comboTipoTarjeta.getValue();
        String cupoStr = txtCupo.getText();

        if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de tarjeta.");
            return;
        }

        double cupo;
        try {
            cupo = Double.parseDouble(cupoStr);
            if (cupo <= 0 && !"Débito".equalsIgnoreCase(tipoSeleccionado)) {
                mostrarAlerta("Error", "El cupo debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un número válido para el cupo.");
            return;
        }

        try {
            Integer cuentaId = null;

            if ("Débito".equalsIgnoreCase(tipoSeleccionado)) {
                CuentaBancaria cuentaSeleccionada = comboCuenta.getValue();
                if (cuentaSeleccionada == null) {
                    mostrarAlerta("Error", "Debe seleccionar una cuenta para la tarjeta débito.");
                    return;
                }
                cuentaId = cuentaSeleccionada.getId();
                cupo = 0.0; // Cupo fijo para tarjetas débito
            }

            Tarjeta tarjeta = tarjetaService.solicitarTarjeta(usuario.getId(), tipoSeleccionado, cupo, cuentaId);

            mostrarAlerta("Éxito", "Tarjeta solicitada correctamente.\n\n"
                    + "Número: " + tarjeta.getNumero() + "\n"
                    + "Vencimiento: " + tarjeta.getFechaVencimiento() + "\n"
                    + "CVV: " + tarjeta.getCvv());

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
}
