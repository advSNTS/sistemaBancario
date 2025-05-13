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

public class DashboardTransferenciasController implements UsuarioAwareController {

    private Usuario usuario;

    @Override
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void abrirTransferencia() {
        abrirModalConUsuario("/com/javeriana/sistema/ui/TransferenciaPersonaPersonaView.fxml", "Transferencia a Otro Usuario");
    }

    @FXML
    private void abrirCrearPagoProgramado() {
        abrirModalConUsuario("/com/javeriana/sistema/ui/CrearPagoProgramadoView.fxml", "Crear Pago Programado");
    }

    @FXML
    private void abrirVerPagosProgramados() {
        abrirModalConUsuario("/com/javeriana/sistema/ui/VerPagosProgramadosView.fxml", "Pagos Programados");
    }

    @FXML
    private void abrirHistorialTransferencias() {
        abrirModalConUsuario("/com/javeriana/sistema/ui/HistorialTransferenciasView.fxml", "Historial de Transferencias");
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) getCurrentWindow();
        if (stage != null) stage.close();
    }

    private void abrirModalConUsuario(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

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
        return Window.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
    }
}
