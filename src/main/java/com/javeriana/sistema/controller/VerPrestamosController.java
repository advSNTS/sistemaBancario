package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Prestamo;
import com.javeriana.sistema.services.PrestamoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VerPrestamosController {

    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, Integer> colId;
    @FXML private TableColumn<Prestamo, Double> colMonto;
    @FXML private TableColumn<Prestamo, Double> colSaldoPendiente;
    @FXML private TableColumn<Prestamo, Double> colTasaInteres;
    @FXML private TableColumn<Prestamo, Integer> colPlazoMeses;
    @FXML private Button btnPagar;

    private int usuarioId;
    private PrestamoService prestamoService = new PrestamoService();

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosDeUsuario(usuarioId);

        tablaPrestamos.getItems().setAll(prestamos);
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colMonto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getMonto()).asObject());
        colSaldoPendiente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSaldoPendiente()).asObject());
        colTasaInteres.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTasaInteres()).asObject());
        colPlazoMeses.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPlazoMeses()).asObject());

        tablaPrestamos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnPagar.setDisable(newSelection == null);
        });
    }

    @FXML
    private void abrirPagoPrestamo() {
        Prestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/PagarPrestamoView.fxml"));
                Parent root = loader.load();

                PagarPrestamoController controller = loader.getController();
                controller.setPrestamo(prestamoSeleccionado);
                controller.setVerPrestamosController(this); // Para refrescar la tabla luego del pago

                Stage stage = new Stage();
                stage.setTitle("Pagar Préstamo");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void recargarPrestamos() {
        cargarPrestamos();
    }
}
