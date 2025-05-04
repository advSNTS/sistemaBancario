package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.TarjetaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class BloquearTarjetaController {

    @FXML private ComboBox<Tarjeta> comboTarjetas;
    @FXML private Button btnBloquear;

    private final TarjetaService tarjetaService = new TarjetaService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarTarjetasActivas();
    }

    private void cargarTarjetasActivas() {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId).stream()
                .filter(t -> !t.isBloqueada())
                .toList();
        comboTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        comboTarjetas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                if (tarjeta == null || empty) {
                    setText(null);
                } else {
                    setText(tarjeta.getTipo() + " - Cupo: $" + tarjeta.getCupoDisponible());
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
                    setText(tarjeta.getTipo() + " - Cupo: $" + tarjeta.getCupoDisponible());
                }
            }
        });
    }

    @FXML
    private void bloquearTarjeta() {
        Tarjeta seleccionada = comboTarjetas.getValue();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Seleccione una tarjeta para bloquear.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar bloqueo");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro que desea bloquear esta tarjeta?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            tarjetaService.bloquearTarjeta(seleccionada.getId());
            mostrarAlerta("Éxito", "Tarjeta bloqueada exitosamente.");
            cargarTarjetasActivas(); // Refresca la lista
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
