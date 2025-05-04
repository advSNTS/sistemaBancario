package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.TarjetaService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class VerTarjetasController {

    @FXML private TableView<Tarjeta> tablaTarjetas;
    @FXML private TableColumn<Tarjeta, String> colTipo;
    @FXML private TableColumn<Tarjeta, String> colEstado;
    @FXML private TableColumn<Tarjeta, Double> colCupo;
    @FXML private TableColumn<Tarjeta, Double> colDeuda;

    private final TarjetaService tarjetaService = new TarjetaService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarTarjetas();
    }

    private void cargarTarjetas() {
        List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasDeUsuario(usuarioId);
        tablaTarjetas.setItems(FXCollections.observableArrayList(tarjetas));

        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));
        colEstado.setCellValueFactory(cell -> {
            Tarjeta t = cell.getValue();
            String estado = t.isBloqueada() ? "BLOQUEADA" : (t.isActiva() ? "ACTIVA" : "INACTIVA");
            return new SimpleStringProperty(estado);
        });
        colCupo.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getCupoDisponible()).asObject());
        colDeuda.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getDeuda()).asObject());
    }
}
