package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class VerCuentasController {

    @FXML
    private TableView<CuentaBancaria> tablaCuentas;

    @FXML
    private TableColumn<CuentaBancaria, String> colTipo;

    @FXML
    private TableColumn<CuentaBancaria, Double> colSaldo;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarCuentas();
    }

    private void cargarCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        tablaCuentas.getItems().setAll(cuentas);
    }

    @FXML
    public void initialize() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
    }
}
