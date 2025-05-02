// RegistroController.java
package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RegistroController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private ComboBox<String> comboPreguntaSecreta;
    @FXML private TextField txtRespuestaSecreta;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        List<String> preguntas = Arrays.asList(
                "¿Cómo se llama tu primera mascota?",
                "¿Cuál es el nombre de tu escuela primaria?",
                "¿Cuál es tu comida favorita?",
                "¿En qué ciudad naciste?",
                "¿Nombre de tu mejor amigo de infancia?"
        );
        comboPreguntaSecreta.getItems().addAll(preguntas);
    }

    @FXML
    private void registrarUsuario() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String clave = txtClave.getText();
            String pregunta = comboPreguntaSecreta.getValue();
            String respuesta = txtRespuestaSecreta.getText();

            if (pregunta == null || respuesta.isEmpty()) {
                mostrarAlerta("Faltan datos", "Selecciona una pregunta secreta y proporciona tu respuesta.");
                return;
            }

            Usuario nuevoUsuario = new Usuario(id, nombre, correo, clave);
            nuevoUsuario.setPreguntaSecreta(pregunta);
            nuevoUsuario.setRespuestaSecreta(respuesta);

            usuarioService.registrarUsuario(nuevoUsuario);
            mostrarAlerta("Éxito", "Usuario registrado correctamente.");

            Stage stage = (Stage) txtId.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/LoginView.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setMostrarMensajeRegistro(true);

            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Bancario - Login");
            stage.show();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cédula debe ser un número.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void volverLogin() {
        ((Stage) txtId.getScene().getWindow()).close();
    }
}
