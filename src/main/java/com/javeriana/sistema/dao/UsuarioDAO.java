package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    Usuario buscarPorCorreo(String correo); // ✅ este debe estar
    void actualizarClavePorCorreo(String correo, String nuevaClave); // ✅ también este
    List<Usuario> listarTodos();
    void actualizar(Usuario usuario);
    void eliminar(int id);
}
