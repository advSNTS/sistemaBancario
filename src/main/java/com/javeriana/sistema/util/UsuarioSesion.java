package com.javeriana.sistema.util;

import com.javeriana.sistema.model.Usuario;

public class UsuarioSesion {
    private static UsuarioSesion instancia;
    private Usuario usuario;

    private UsuarioSesion() {}

    public static UsuarioSesion getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioSesion();
        }
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void limpiar() {
        usuario = null;
    }

    public static Usuario getUsuarioActual() {
        return getInstancia().getUsuario();
    }

    public static void setUsuarioActual(Usuario usuario) {
        getInstancia().setUsuario(usuario);
    }
}
