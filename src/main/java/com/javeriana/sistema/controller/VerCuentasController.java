package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.services.CuentaBancariaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VerCuentasController {

    @FXML private TableView<CuentaBancaria> tablaCuentas;
    @FXML private TableColumn<CuentaBancaria, Integer> colId;
    @FXML private TableColumn<CuentaBancaria, String> colTipo;
    @FXML private TableColumn<CuentaBancaria, Double> colSaldo;
    @FXML private Button btnOperar;

    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    private int usuarioId;

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @FXML
    public void initialize() {
        tablaCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnOperar.setDisable(newSelection == null);
        });
    }

    public void cargarCuentas() {
        List<CuentaBancaria> cuentasUsuario = cuentaService.obtenerCuentasDeUsuario(usuarioId);
        tablaCuentas.getItems().setAll(cuentasUsuario);

        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colTipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipo()));
        colSaldo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSaldo()).asObject());
    }

    @FXML
    private void abrirOperacionCuenta() {
        CuentaBancaria seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/OperarCuentaView.fxml"));
                Parent root = loader.load();

                OperarCuentaController controller = loader.getController();
                controller.setCuentaBancaria(seleccionada);

                Stage stage = new Stage();
                stage.setTitle("Operar Cuenta");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void abrirDepositarRetirar() {
        CuentaBancaria cuentaSeleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            try {
                // Refrescar desde base de datos usando su ID
                CuentaBancaria cuentaActualizada = cuentaService.obtenerCuentaPorId(cuentaSeleccionada.getId());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/DepositarRetirarView.fxml"));
                Parent root = loader.load();

                DepositarRetirarController controller = loader.getController();
                controller.setCuenta(cuentaActualizada); // Usa la cuenta actualizada
                controller.setVerCuentasController(this);

                Stage stage = new Stage();
                stage.setTitle("Operaciones de Cuenta");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Selecciona una cuenta", "Debes seleccionar una cuenta para realizar esta acción.");
        }
    }

    public void recargarTabla() {
        cargarCuentas();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void abrirSimularInversiones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/SimularInversionesView.fxml"));
            Parent root = loader.load();

            SimularInversionesController controller = loader.getController();
            controller.setVerCuentasController(this);

            Stage stage = new Stage();
            stage.setTitle("Simular Inversiones");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de simulación de inversiones.");
        }
    }
}
