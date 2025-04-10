package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Button btnLogin;

    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();

        // Aquí deberíamos validar con la base de datos
        if (correo.equals("admin@mail.com") && clave.equals("1234")) {
            System.out.println("✅ Inicio de sesión exitoso.");
        } else {
            System.out.println("❌ Credenciales incorrectas.");
        }
    }
}
