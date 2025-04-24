package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuariosController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioService.obtenerTodosLosUsuarios());
        tablaUsuarios.setItems(usuarios);
    }
}
