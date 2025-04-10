package com.javeriana.sistema;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HelloController {
    @FXML
    private BorderPane contenedor;

    @FXML
    protected void salir(){
        System.exit(0);
    }
}