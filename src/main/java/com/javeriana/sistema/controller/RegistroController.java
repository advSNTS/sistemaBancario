package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    public void setContenedorPrincipal(BorderPane contenedorPrincipal) {
        this.contenedorPrincipal = contenedorPrincipal;
    }

    public void registrar() {
        String cedula = txtcedula.getText();
        String nombre = txtnombre.getText();
        String apellido = txtapellido.getText();
        String contrasenia = txtcontrasena.getText();
        limpiarCampos();
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
