package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardCategoriasController {

    @FXML
    private javafx.scene.control.Button cerrarSesionButton;

    private void abrirVentanaModal(String titulo, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle(titulo);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void abrirCategoriaCuentas() {
        abrirVentanaModal("Gestión de Cuentas", "/com/javeriana/sistema/ui/DashboardCuentasView.fxml");
    }

    @FXML private void abrirCategoriaPrestamos() {
        abrirVentanaModal("Gestión de Préstamos", "/com/javeriana/sistema/ui/DashboardPrestamosView.fxml");
    }

    @FXML private void abrirCategoriaTransferencias() {
        abrirVentanaModal("Transferencias y Pagos", "/com/javeriana/sistema/ui/DashboardTransferenciasView.fxml");
    }

    @FXML private void abrirCategoriaTarjetas() {
        abrirVentanaModal("Gestión de Tarjetas", "/com/javeriana/sistema/ui/DashboardTarjetasView.fxml");
    }

    @FXML private void abrirCategoriaInversiones() {
        abrirVentanaModal("Gestión de Inversiones", "/com/javeriana/sistema/ui/DashboardInversionesView.fxml");
    }

    @FXML private void abrirCategoriaConfiguraciones() {
        abrirVentanaModal("Configuraciones", "/com/javeriana/sistema/ui/DashboardConfiguracionesView.fxml");
    }

    @FXML
    private void abrirTrazadorMovimientos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerMovimientosView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Trazador de Movimientos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void abrirSoporte() {
        abrirVentanaModal("Soporte al Cliente", "/com/javeriana/sistema/ui/Soporte.fxml");
    }

    @FXML private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/LoginView.fxml"));
            Parent root = loader.load();

            Stage stageLogin = new Stage();
            stageLogin.setTitle("Sistema Bancario - Iniciar Sesión");
            stageLogin.setScene(new Scene(root));
            stageLogin.show();

            Stage ventanaActual = (Stage) cerrarSesionButton.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
