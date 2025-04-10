package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    List<Usuario> listarTodos();
    void actualizar(Usuario usuario);
    void eliminar(int id);
}
