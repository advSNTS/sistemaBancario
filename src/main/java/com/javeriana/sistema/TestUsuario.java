package com.javeriana.sistema;

import com.javeriana.sistema.services.UsuarioService;

public class TestUsuario {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();

        // Crear un nuevo usuario
        //usuarioService.registrarUsuario("Juan Pérez", "juan@example.com", "1234");

        // Listar usuarios
        System.out.println(usuarioService.obtenerTodosLosUsuarios());
    }
}
