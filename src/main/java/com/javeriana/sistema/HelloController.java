package com.javeriana.sistema;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.javeriana.sistema.controller.RegistroController;

public class HelloController {
    @FXML
    private BorderPane contenedor;

    @FXML
    protected void salir(){
        System.exit(0);
    }

    @FXML
    protected void inicioSesion() throws Exception{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("inicio-sesionview.fxml"));
        contenedor.setCenter(pane);
    }

    @FXML
    protected void registro() throws Exception {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("registro-view.fxml"));
        contenedor.setCenter(pane);
    }
}