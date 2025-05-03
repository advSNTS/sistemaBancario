// HistorialTransferenciasController.java
package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.services.TransferenciaService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.time.LocalDateTime;
import java.util.List;

public class HistorialTransferenciasController {

    @FXML private TableView<Transferencia> tablaTransferencias;
    @FXML private TableColumn<Transferencia, Integer> colId;
    @FXML private TableColumn<Transferencia, Integer> colOrigen;
    @FXML private TableColumn<Transferencia, Integer> colDestino;
    @FXML private TableColumn<Transferencia, Double> colMonto;
    @FXML private TableColumn<Transferencia, LocalDateTime> colFecha;
    @FXML private Button btnVolver;

    private final TransferenciaService transferenciaService = new TransferenciaService();
    private int usuarioId;

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        cargarTransferencias();
    }

    private void cargarTransferencias() {
        tablaTransferencias.getItems().clear();

        List<Transferencia> transferencias = transferenciaService.obtenerTransferenciasPorUsuario(usuarioId);

        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colOrigen.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCuentaOrigenId()).asObject());
        colDestino.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCuentaDestinoId()).asObject());
        colMonto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMonto()).asObject());
        colFecha.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFecha()));

        tablaTransferencias.getItems().addAll(transferencias);
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) tablaTransferencias.getScene().getWindow();
        stage.close();
    }
}
