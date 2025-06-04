package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Prestamo;
import com.javeriana.sistema.services.PrestamoService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class VerPrestamosController {

    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, Integer> colId;
    @FXML private TableColumn<Prestamo, Double> colMonto;
    @FXML private TableColumn<Prestamo, Double> colSaldo;
    @FXML private TableColumn<Prestamo, LocalDate> colFecha;
    @FXML private TableColumn<Prestamo, String> colEstado;
    @FXML private Button btnPagarPrestamo;

    private final PrestamoService prestamoService = new PrestamoService();
    private int usuarioId = -1;

    @FXML
    public void initialize() {
        if (usuarioId <= 0 && UsuarioSesion.getInstancia().getUsuario() != null) {
            usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
        }

        if (usuarioId > 0) {
            cargarPrestamos(usuarioId);
        } else {
            mostrarAlerta("Error", "No se pudo obtener el usuario actual.");
        }
    }

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarPrestamos(id);
    }

    private void cargarPrestamos(int usuarioId) {
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosDeUsuario(usuarioId);
        tablaPrestamos.getItems().setAll(prestamos);

        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colMonto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMonto()).asObject());
        colSaldo.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSaldoPendiente()).asObject());
        colFecha.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaAprobacion()));

        colEstado.setCellValueFactory(data -> {
            String estado = (data.getValue().getSaldoPendiente() == 0.0) ? "Pagado" : "Pendiente";
            return new javafx.beans.property.SimpleStringProperty(estado);
        });

        colEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    setStyle(estado.equals("Pagado")
                            ? "-fx-text-fill: green; -fx-font-weight: bold;"
                            : "-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        btnPagarPrestamo.setDisable(true);
        tablaPrestamos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnPagarPrestamo.setDisable(newSel == null);
        });
    }

    public void recargarPrestamos() {
        if (usuarioId > 0) {
            cargarPrestamos(usuarioId);
        }
    }

    @FXML
    private void abrirPagarPrestamo() {
        Prestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/PagarPrestamoView.fxml"));
                Parent root = loader.load();

                PagarPrestamoController controller = loader.getController();
                controller.setPrestamo(prestamoSeleccionado);
                controller.setVerPrestamosController(this);
                controller.setUsuarioId(usuarioId);

                Stage stage = new Stage();
                stage.setTitle("Pagar Préstamo");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "Debes seleccionar un préstamo para pagar.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
