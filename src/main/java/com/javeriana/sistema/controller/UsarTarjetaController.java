package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class UsarTarjetaController {

    @FXML private ComboBox<Tarjeta> comboTarjetas;
    @FXML private TextField txtMonto;
    @FXML private Button btnPagar;

    private final TarjetaService tarjetaService = new TarjetaService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    @FXML
    private void initialize() {
        usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarTarjetasActivas();
    }

    private void cargarTarjetasActivas() {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId).stream()
                .filter(t -> !t.isBloqueada() && t.isActiva())
                .toList();

        comboTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        comboTarjetas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                if (tarjeta == null || empty) {
                    setText(null);
                } else {
                    String texto;
                    if ("Débito".equalsIgnoreCase(tarjeta.getTipo()) && tarjeta.getCuentaAsociadaId() != null) {
                        CuentaBancaria cuenta = cuentaService.buscarPorId(tarjeta.getCuentaAsociadaId());
                        double saldo = cuenta != null ? cuenta.getSaldo() : 0.0;
                        texto = "Débito - Saldo cuenta: $" + saldo;
                    } else {
                        texto = "Crédito - Cupo disponible: $" + tarjeta.getCupoDisponible();
                    }
                    setText(texto);
                }
            }
        });

        comboTarjetas.setButtonCell(comboTarjetas.getCellFactory().call(null));
    }

    @FXML
    private void realizarPago() {
        Tarjeta tarjeta = comboTarjetas.getValue();
        String montoTexto = txtMonto.getText();

        if (tarjeta == null || montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una tarjeta y especificar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a cero.");
                return;
            }

            tarjetaService.usarTarjeta(tarjeta.getId(), monto);
            mostrarAlerta("Éxito", "Pago realizado exitosamente.");

            cargarTarjetasActivas();
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

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarTarjetasActivas();
    }
}
