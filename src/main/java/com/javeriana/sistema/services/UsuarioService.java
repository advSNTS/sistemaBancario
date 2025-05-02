package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.UsuarioDAO;
import com.javeriana.sistema.dao.UsuarioDAOImpl;
import com.javeriana.sistema.model.Usuario;


import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    //Metodo actualizado para recibir directamente el objeto Usuario
    public void registrarUsuario(Usuario usuario) {
        usuarioDAO.guardar(usuario);
        System.out.println("Usuario registrado correctamente.");
    }

    public Usuario autenticarUsuario(String correo, String clave) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getClave().equals(clave)) {
                return u;
            }
        }
        return null;
    }

    // Obtener todos los usuarios (opcional para otras vistas)
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarTodos();
    }

    // Autenticación para el login
    public Usuario autenticar(String correo, String clave) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getClave().equals(clave)) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorCorreo(String correo) {
        // Metodo que busque en la BD el usuario dado un correo.
        return usuarioDAO.buscarPorCorreo(correo);
    }

    public void actualizarUsuario(Usuario usuario) {

        usuarioDAO.actualizar(usuario);
    }

    public void actualizarClavePorCorreo(String correo, String nuevaClave) {
        usuarioDAO.actualizarClavePorCorreo(correo, nuevaClave);
    }


}
