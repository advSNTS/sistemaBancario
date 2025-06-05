package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Button btnLogin;

    private boolean mostrarMensajeRegistro = false;

    public void setMostrarMensajeRegistro(boolean valor) {
        this.mostrarMensajeRegistro = valor;
    }

    @FXML
    private void initialize() {
        if (mostrarMensajeRegistro) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            alert.showAndWait();
            mostrarMensajeRegistro = false;
        }
    }

    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();

        UsuarioService service = new UsuarioService();
        Usuario usuario = service.autenticarUsuario(correo, clave);

        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso para: " + usuario.getNombre());

            UsuarioSesion.setUsuarioActual(usuario);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/DashboardCategoriasView.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) txtCorreo.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Bienvenido, " + usuario.getNombre());
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al cargar DashboardCategoriasView.fxml");
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de autenticación");
            alert.setHeaderText(null);
            alert.setContentText("Correo o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    @FXML
    private void abrirVentanaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/RegistroView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Error al cargar RegistroView.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void abrirRecuperarContrasena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/RecuperarContrasenaView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Recuperar Contraseña");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
