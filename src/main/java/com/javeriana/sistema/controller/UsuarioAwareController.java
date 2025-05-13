package com.javeriana.sistema.controller;

import com.javeriana.sistema.model.Usuario;

public interface UsuarioAwareController {
    void setUsuarioAutenticado(Usuario usuario);
}