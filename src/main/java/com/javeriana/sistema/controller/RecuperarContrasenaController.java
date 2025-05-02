package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecuperarContrasenaController {

    @FXML private TextField txtCorreo;
    @FXML private Label lblPregunta;
    @FXML private TextField txtRespuesta;
    @FXML private PasswordField txtNuevaClave;

    private UsuarioService usuarioService = new UsuarioService();
    private Usuario usuario;

    @FXML
    private void initialize() {
        lblPregunta.setText("Ingresa tu correo y presiona Enter");

        txtCorreo.setOnAction(event -> mostrarPregunta());
    }

    private void mostrarPregunta() {
        String correo = txtCorreo.getText();
        usuario = usuarioService.buscarPorCorreo(correo);

        if (usuario != null) {
            lblPregunta.setText(usuario.getPreguntaSecreta());
        } else {
            lblPregunta.setText("Correo no registrado.");
        }
    }

    @FXML
    private void restablecerContrasena() {
        if (usuario == null) {
            mostrarAlerta("Error", "Debes ingresar un correo válido primero.");
            return;
        }

        String respuestaIngresada = txtRespuesta.getText();
        String nuevaClave = txtNuevaClave.getText();

        if (!respuestaIngresada.equalsIgnoreCase(usuario.getRespuestaSecreta())) {
            mostrarAlerta("Error", "Respuesta secreta incorrecta.");
            return;
        }

        usuarioService.actualizarClavePorCorreo(usuario.getCorreo(), nuevaClave);
        mostrarAlerta("Éxito", "Contraseña actualizada correctamente.");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
