package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button cerrarSesionButton;

    private Usuario usuarioAutenticado;

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    @FXML
    private void abrirCrearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/CrearCuentaView.fxml"));
            Parent root = loader.load();

            CrearCuentaController crearCuentaController = loader.getController();
            crearCuentaController.setUsuarioId(usuarioAutenticado.getId());

            Stage stage = new Stage();
            stage.setTitle("Crear Nueva Cuenta");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void verCuentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerCuentasView.fxml"));
            Parent root = loader.load();

            VerCuentasController verCuentasController = loader.getController();
            verCuentasController.setUsuarioId(usuarioAutenticado.getId()); // Pasamos el ID del usuario para filtrar cuentas

            Stage stage = new Stage();
            stage.setTitle("Mis Cuentas Bancarias");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) cerrarSesionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Bancario - Iniciar Sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
