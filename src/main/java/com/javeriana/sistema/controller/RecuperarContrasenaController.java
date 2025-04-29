package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RecuperarContrasenaController {

    @FXML private TextField txtCorreo;
    @FXML private Label lblPregunta;
    @FXML private TextField txtRespuesta;
    @FXML private PasswordField txtNuevaClave;

    private UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioEncontrado;

    @FXML
    private void restablecerContrasena() {
        if (usuarioEncontrado == null) {
            // Buscar el usuario
            String correo = txtCorreo.getText();
            usuarioEncontrado = usuarioService.buscarPorCorreo(correo);

            if (usuarioEncontrado != null) {
                lblPregunta.setText(usuarioEncontrado.getPreguntaSecreta());
            } else {
                mostrarAlerta("Error", "No se encontró usuario con ese correo.");
            }
        } else {
            // Validar respuesta
            if (txtRespuesta.getText().equalsIgnoreCase(usuarioEncontrado.getRespuestaSecreta())) {
                // Cambiar contraseña
                String nuevaClave = txtNuevaClave.getText();
                if (nuevaClave.length() < 6) {
                    mostrarAlerta("Error", "La contraseña debe tener mínimo 6 caracteres.");
                    return;
                }
                usuarioEncontrado.setClave(nuevaClave);
                usuarioService.actualizarUsuario(usuarioEncontrado);
                mostrarAlerta("Éxito", "Contraseña actualizada correctamente.");

                // Cerrar ventana
                Stage stage = (Stage) txtCorreo.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "Respuesta incorrecta.");
            }
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
