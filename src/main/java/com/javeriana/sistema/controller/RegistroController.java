package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void registrarUsuario(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String clave = txtClave.getText();

            if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                System.out.println("Todos los campos deben estar completos.");
                return;
            }

            Usuario nuevo = new Usuario(id, nombre, correo, clave);
            usuarioService.registrarUsuario(nuevo);

            System.out.println("Usuario registrado correctamente.");

            volverLogin(event);

        } catch (NumberFormatException e) {
            System.out.println("ID invalido. Debe ser un numero.");
        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    @FXML
    private void volverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/WelcomeView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtId.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("No se pudo cargar la vista de bienvenida: " + e.getMessage());
        }
    }
}
