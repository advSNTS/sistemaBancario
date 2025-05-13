package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardCategoriasController {

    private Usuario usuarioAutenticado;

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

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

    @FXML
    private void abrirCategoriaCuentas() {
        abrirVentanaModal("Gestión de Cuentas", "/com/javeriana/sistema/ui/DashboardCuentasView.fxml");
    }

    @FXML
    private void abrirCategoriaPrestamos() {
        abrirVentanaModal("Gestión de Préstamos", "/com/javeriana/sistema/ui/DashboardPrestamosView.fxml");
    }

    @FXML
    private void abrirCategoriaTransferencias() {
        abrirVentanaModal("Transferencias y Pagos", "/com/javeriana/sistema/ui/DashboardTransferenciasView.fxml");
    }

    @FXML
    private void abrirCategoriaTarjetas() {
        abrirVentanaModal("Gestión de Tarjetas", "/com/javeriana/sistema/ui/DashboardTarjetasView.fxml");
    }

    @FXML
    private void abrirCategoriaInversiones() {
        abrirVentanaModal("Gestión de Inversiones", "/com/javeriana/sistema/ui/DashboardInversionesView.fxml");
    }

    @FXML
    private void abrirCategoriaSeguridad() {
        abrirVentanaModal("Configuración de Seguridad", "/com/javeriana/sistema/ui/ConfigurarLimiteView.fxml");
    }

    @FXML
    private void abrirSoporte() {
        abrirVentanaModal("Soporte al Cliente", "/com/javeriana/sistema/ui/Soporte.fxml");
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/LoginView.fxml"));
            Parent root = loader.load();

            Stage stageLogin = new Stage();
            stageLogin.setTitle("Sistema Bancario - Iniciar Sesión");
            stageLogin.setScene(new Scene(root));
            stageLogin.show();

            // Cierra la ventana actual
            Stage ventanaActual = (Stage) cerrarSesionButton.getScene().getWindow(); // usa fx:id
            ventanaActual.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private javafx.scene.control.Button cerrarSesionButton; // Asegúrate de tener fx:id
}
