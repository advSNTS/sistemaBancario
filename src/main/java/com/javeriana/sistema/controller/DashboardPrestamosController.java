package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardPrestamosController implements UsuarioAwareController {

    private Usuario usuario;

    @Override
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void verPrestamos() {
        abrirVentanaConUsuario("/com/javeriana/sistema/ui/VerPrestamosView.fxml", "Ver Préstamos");
    }

    @FXML
    private void abrirSolicitarPrestamo() {
        abrirVentanaConUsuario("/com/javeriana/sistema/ui/SolicitarPrestamoView.fxml", "Solicitar Préstamo");
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) getCurrentWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void abrirVentanaConUsuario(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            // Pasar usuario al controlador si aplica
            Object controller = loader.getController();
            if (controller instanceof UsuarioAwareController) {
                ((UsuarioAwareController) controller).setUsuarioAutenticado(usuario);
            }

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle(titulo);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Window getCurrentWindow() {
        return javafx.stage.Window.getWindows().stream()
                .filter(Window::isFocused)
                .findFirst()
                .orElse(null);
    }
}
