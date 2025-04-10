package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController {

    @FXML private TextField txtCedula;
    @FXML private PasswordField txtClave;
    @FXML private Button btnLogin;
    private UsuarioService usuarioService = new UsuarioService();

    public void iniciarSesion(){
        String cedula = txtCedula.getText();
        String clave = txtClave.getText();

        Usuario usuario = usuarioService.iniciarSesion(cedula, clave);

        if (usuario != null) {
            mostrarAlerta("✅ Bienvenido", "Hola " + usuario.getNombre() + " " + usuario.getApellido());
        } else {
            mostrarAlerta("❌ Error", "Usuario o contraseña incorrectos");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
