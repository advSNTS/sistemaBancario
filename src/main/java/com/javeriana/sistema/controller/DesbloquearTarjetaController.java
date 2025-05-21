package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class DesbloquearTarjetaController {

    @FXML private ComboBox<Tarjeta> comboTarjetas;
    @FXML private Button btnDesbloquear;

    private final TarjetaService tarjetaService = new TarjetaService();
    private int usuarioId;

    @FXML
    public void initialize() {
        usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarTarjetasBloqueadas(usuarioId);
    }

    private void cargarTarjetasBloqueadas(int usuarioId) {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId).stream()
                .filter(Tarjeta::isBloqueada)
                .toList();

        comboTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        comboTarjetas.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                setText((empty || tarjeta == null) ? null : tarjeta.getTipo() + " - Cupo: $" + tarjeta.getCupoDisponible());
            }
        });

        comboTarjetas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tarjeta tarjeta, boolean empty) {
                super.updateItem(tarjeta, empty);
                setText((empty || tarjeta == null) ? null : tarjeta.getTipo() + " - Cupo: $" + tarjeta.getCupoDisponible());
            }
        });
    }

    @FXML
    private void desbloquearTarjeta() {
        Tarjeta seleccionada = comboTarjetas.getValue();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Seleccione una tarjeta para desbloquear.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar desbloqueo");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro que desea desbloquear esta tarjeta?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            tarjetaService.desbloquearTarjeta(seleccionada.getId());
            mostrarAlerta("Éxito", "Tarjeta desbloqueada exitosamente.");
            cargarTarjetasBloqueadas(usuarioId);
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
        cargarTarjetasBloqueadas(usuarioId);
    }
}
