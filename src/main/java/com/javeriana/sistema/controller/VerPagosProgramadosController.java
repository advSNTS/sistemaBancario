package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.PagoProgramado;
import com.javeriana.sistema.services.PagoProgramadoService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class VerPagosProgramadosController {

    @FXML private TableView<PagoProgramado> tablaPagos;
    @FXML private TableColumn<PagoProgramado, String> colCuentaOrigen;
    @FXML private TableColumn<PagoProgramado, String> colCuentaDestino;
    @FXML private TableColumn<PagoProgramado, Double> colMonto;
    @FXML private TableColumn<PagoProgramado, String> colFechaHora;
    @FXML private TableColumn<PagoProgramado, Boolean> colEjecutado;

    private final PagoProgramadoService pagoService = new PagoProgramadoService();
    private int usuarioId;

    @FXML
    public void initialize() {
        usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        cargarPagos();
    }

    private void cargarPagos() {
        tablaPagos.setItems(FXCollections.observableArrayList(
                pagoService.obtenerPagosPorUsuario(usuarioId)
        ));

        colCuentaOrigen.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().getCuentaOrigenId()))
        );
        colCuentaDestino.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().getCuentaDestinoId()))
        );
        colMonto.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getMonto()).asObject()
        );
        colFechaHora.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getFechaHoraEjecucion()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                )
        );
        colEjecutado.setCellValueFactory(cell ->
                new SimpleBooleanProperty(cell.getValue().isEjecutado()).asObject()
        );
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarPagos();
    }
}
