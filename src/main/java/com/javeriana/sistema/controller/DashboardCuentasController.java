package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardCuentasController {

    @FXML
    private void abrirCrearCuenta() {
        abrirVentana("/com/javeriana/sistema/ui/CrearCuentaView.fxml", "Crear Nueva Cuenta");
    }

    @FXML
    private void verCuentas() {
        abrirVentana("/com/javeriana/sistema/ui/VerCuentasView.fxml", "Mis Cuentas");
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) getCurrentWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void abrirVentana(String rutaFXML, String titulo) {
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

    private Window getCurrentWindow() {
        return Window.getWindows().stream()
                .filter(Window::isFocused)
                .findFirst()
                .orElse(null);
    }
}
