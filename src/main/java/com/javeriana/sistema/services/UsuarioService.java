package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.UsuarioDAO;
import com.javeriana.sistema.dao.UsuarioDAOImpl;
import com.javeriana.sistema.model.Usuario;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public void registrarUsuario(String nombre, String correo, String clave) {
        Usuario usuario = new Usuario(0, nombre, correo, clave);
        usuarioDAO.guardar(usuario);
        System.out.println("✅ Usuario registrado correctamente.");
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarTodos(); // 🔹 Este método debe existir en UsuarioDAO
    }
}
