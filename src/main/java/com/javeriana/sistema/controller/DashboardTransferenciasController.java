package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardTransferenciasController {

    @FXML
    private void abrirTransferencia() {
        abrirModal("/com/javeriana/sistema/ui/TransferenciaPersonaPersonaView.fxml", "Transferencia a Otro Usuario");
    }

    @FXML
    private void abrirCrearPagoProgramado() {
        abrirModal("/com/javeriana/sistema/ui/CrearPagoProgramadoView.fxml", "Crear Pago Programado");
    }

    @FXML
    private void abrirVerPagosProgramados() {
        abrirModal("/com/javeriana/sistema/ui/VerPagosProgramadosView.fxml", "Pagos Programados");
    }

    @FXML
    private void abrirHistorialTransferencias() {
        abrirModal("/com/javeriana/sistema/ui/HistorialTransferenciasView.fxml", "Historial de Transferencias");
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
