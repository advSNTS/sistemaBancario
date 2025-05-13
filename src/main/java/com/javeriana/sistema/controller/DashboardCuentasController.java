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

public class DashboardCuentasController implements UsuarioAwareController {

    private Usuario usuario;

    @Override
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void abrirCrearCuenta() {
        abrirVentanaConUsuario("/com/javeriana/sistema/ui/CrearCuentaView.fxml", "Crear Nueva Cuenta");
    }

    @FXML
    private void verCuentas() {
        abrirVentanaConUsuario("/com/javeriana/sistema/ui/VerCuentasView.fxml", "Mis Cuentas");
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

            // Verificamos si el controlador implementa la interfaz para pasar el usuario
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
