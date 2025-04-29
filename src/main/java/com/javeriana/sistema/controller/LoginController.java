package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Button btnLogin;

    private Usuario usuarioAutenticado; //  Usuario logueado

    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();

        UsuarioService service = new UsuarioService();
        Usuario usuario = service.autenticarUsuario(correo, clave);

        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso para: " + usuario.getNombre());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/DashboardView.fxml"));
                Parent root = loader.load();

                DashboardController controller = loader.getController();
                controller.setUsuarioAutenticado(usuario); // Pasamos el usuario al dashboard

                Stage stage = (Stage) txtCorreo.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Bienvenido, " + usuario.getNombre());
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al cargar DashboardView.fxml");
            }

        } else {
            System.out.println("Credenciales incorrectas.");
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
    private void mostrarUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/UsuariosView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Lista de Usuarios");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirCrearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/CrearCuentaView.fxml"));
            Parent root = loader.load();

            CrearCuentaController controller = loader.getController();
            controller.setUsuarioId(usuarioAutenticado.getId()); // usuario logueado

            Stage stage = new Stage();
            stage.setTitle("Crear Cuenta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
