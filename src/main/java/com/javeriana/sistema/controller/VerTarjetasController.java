package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.services.TarjetaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerTarjetasController {

    @FXML private TableView<Tarjeta> tablaTarjetas;
    @FXML private TableColumn<Tarjeta, String> colTipo;
    @FXML private TableColumn<Tarjeta, String> colEstado;
    @FXML private TableColumn<Tarjeta, Double> colCupo;
    @FXML private TableColumn<Tarjeta, Double> colDeuda;
    @FXML private TableColumn<Tarjeta, String> colNumero;
    @FXML private TableColumn<Tarjeta, String> colVencimiento;
    @FXML private TableColumn<Tarjeta, String> colCVV;

    private final TarjetaService tarjetaService = new TarjetaService();
    private int usuarioId;

    @FXML
    public void initialize() {
        int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarTarjetas(usuarioId);
    }

    private void cargarTarjetas(int usuarioId) {
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

        colNumero.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNumero()));
        colVencimiento.setCellValueFactory(cell -> {
            String venc = cell.getValue().getFechaVencimiento().format(DateTimeFormatter.ofPattern("MM/yy"));
            return new SimpleStringProperty(venc);
        });
        colCVV.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCvv()));
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarTarjetas(usuarioId); 
    }
}
