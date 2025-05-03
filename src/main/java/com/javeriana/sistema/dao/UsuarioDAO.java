package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    Usuario buscarPorCorreo(String correo);
    Usuario buscarPorCedula(String cedula);
    void actualizarClavePorCorreo(String correo, String nuevaClave);
    List<Usuario> listarTodos();
    void actualizar(Usuario usuario);
    void eliminar(int id);
}
