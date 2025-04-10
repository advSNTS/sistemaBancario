package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.UsuarioDAO;
import com.javeriana.sistema.model.Usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(String cedula, String nombre, String apellido, String contrasena) {
        Usuario usuario = new Usuario(cedula, nombre, apellido, contrasena);
        usuarioDAO.guardar(usuario);
        System.out.println("✅ Usuario registrado correctamente.");
    }
    public Usuario iniciarSesion(String cedula, String contrasena) {
        return usuarioDAO.buscarPorCedulaYContrasena(cedula, contrasena);
    }
    public boolean existeUsuario(String cedula) {
        return usuarioDAO.existeUsuarioPorCedula(cedula);
    }
}