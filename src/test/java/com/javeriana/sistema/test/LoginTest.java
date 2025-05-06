package com.javeriana.sistema.test;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.DBConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp(){
        DBConnection.activarModoPrueba();
        DBConnection.getInstance();
        usuarioService = new UsuarioService();

        Usuario usuario = new Usuario(1, "Juan", "juan@mail.com", "fundamentos123");
        usuario.setCedula("12345678");
        usuario.setPreguntaSecreta("¿Ciudad?");
        usuario.setRespuestaSecreta("Bogotá");

        usuarioService.registrarUsuario(usuario);
    }
    
}
