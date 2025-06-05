package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardTarjetasController {

    @FXML
    private void abrirSolicitarTarjeta() {
        abrirModal("/com/javeriana/sistema/ui/SolicitarTarjetaView.fxml", "Solicitar Tarjeta");
    }

    @FXML
    private void abrirVerTarjetas() {
        abrirModal("/com/javeriana/sistema/ui/VerTarjetasView.fxml", "Ver Tarjetas");
    }

    @FXML
    private void abrirBloquearTarjeta() {
        abrirModal("/com/javeriana/sistema/ui/BloquearTarjetaView.fxml", "Bloquear Tarjeta");
    }

    @FXML
    private void abrirDesbloquearTarjeta() {
        abrirModal("/com/javeriana/sistema/ui/DesbloquearTarjetaView.fxml", "Desbloquear Tarjeta");
    }

    @FXML
    private void abrirUsarTarjeta() {
        abrirModal("/com/javeriana/sistema/ui/UsarTarjetaView.fxml", "Usar Tarjeta");
    }

    @FXML
    private void abrirPagarDeuda() {
        abrirModal("/com/javeriana/sistema/ui/PagarDeudaTarjetaView.fxml", "Pagar Deuda Tarjeta");
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) getCurrentWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void abrirModal(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
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
        return Window.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
    }
}
