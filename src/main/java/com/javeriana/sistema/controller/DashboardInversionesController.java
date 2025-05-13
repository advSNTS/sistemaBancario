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

public class DashboardInversionesController implements UsuarioAwareController {

    private Usuario usuarioAutenticado;

    @Override
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    @FXML
    private void abrirAbrirInversion() {
        abrirModal("/com/javeriana/sistema/ui/AbrirInversionView.fxml", "Abrir Inversión");
    }

    @FXML
    private void abrirSimularInversion() {
        abrirModal("/com/javeriana/sistema/ui/SimularInversionesView.fxml", "Simular Inversión");
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) getCurrentWindow();
        if (stage != null) stage.close();
    }

    private void abrirModal(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pasar usuario autenticado si aplica
            Object controller = loader.getController();
            if (controller instanceof UsuarioAwareController) {
                ((UsuarioAwareController) controller).setUsuarioAutenticado(usuarioAutenticado);
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
        return Window.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
    }
}
