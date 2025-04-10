package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RegistroController {
    @FXML
    private TextField txtcedula;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtapellido;
    @FXML
    private TextField txtcontrasena;
    @FXML
    private AnchorPane contenedor;
    @FXML
    private BorderPane contenedorPrincipal;

    @FXML
    private Label texto;

    private UsuarioService usuarioService = new UsuarioService();

    public void setContenedorPrincipal(BorderPane contenedorPrincipal) {
        this.contenedorPrincipal = contenedorPrincipal;
    }

    public void registrar() {
        String cedula = txtcedula.getText();
        String nombre = txtnombre.getText();
        String apellido = txtapellido.getText();
        String contrasenia = txtcontrasena.getText();
        if (usuarioService.existeUsuario(cedula)) {
            texto.setText("Ya existe un usuario con esa cédula.");
            return;
        }
        limpiarCampos();
        usuarioService.registrarUsuario(cedula, nombre, apellido, contrasenia);
        System.out.println(cedula + nombre + apellido + contrasenia);
    }

    public void limpiarCampos() {
        txtcedula.setText("");
        txtapellido.setText("");
        txtnombre.setText("");
        txtcontrasena.setText("");
    }

    public void regresar() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/javeriana/sistema/hello-content.fxml"));
            contenedorPrincipal.setCenter(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
