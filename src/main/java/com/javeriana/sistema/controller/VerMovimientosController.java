package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Movimiento;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.MovimientoService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class VerMovimientosController {

    @FXML private ComboBox<CuentaBancaria> comboCuentas;
    @FXML private TableView<Movimiento> tablaMovimientos;
    @FXML private TableColumn<Movimiento, String> colFecha;
    @FXML private TableColumn<Movimiento, String> colTipo;
    @FXML private TableColumn<Movimiento, Double> colMonto;
    @FXML private TableColumn<Movimiento, Integer> colCuentaOrigen;
    @FXML private TableColumn<Movimiento, Integer> colCuentaDestino;

    private final MovimientoService movimientoService = new MovimientoService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    @FXML
    private void initialize() {
        int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        comboCuentas.setItems(FXCollections.observableArrayList(cuentas));

        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
        colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo()));
        colMonto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMonto()).asObject());
        colCuentaOrigen.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCuentaIdOrigen()).asObject());
        colCuentaDestino.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCuentaIdDestino()).asObject());
    }

    @FXML
    private void cargarMovimientos() {
        CuentaBancaria cuenta = comboCuentas.getValue();
        List<Movimiento> movimientos;

        if (cuenta == null) {
            int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
            movimientos = movimientoService.obtenerMovimientosDeUsuario(usuarioId);
        } else {
            movimientos = movimientoService.obtenerMovimientosPorCuenta(cuenta.getId());
        }

        tablaMovimientos.setItems(FXCollections.observableArrayList(movimientos));
    }
}
