package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.javeriana.sistema.util.UsuarioSesion;


import java.io.IOException;

public class DashboardConfiguracionesController {

    @FXML
    private void abrirConfigurarLimite() {
        abrirModal("/com/javeriana/sistema/ui/ConfigurarLimiteView.fxml", "Configurar Límite");
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

            Object controller = loader.getController();
            if (controller instanceof UsuarioAwareController usuarioAware) {
                usuarioAware.setUsuarioAutenticado(UsuarioSesion.getInstancia().getUsuario());
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
