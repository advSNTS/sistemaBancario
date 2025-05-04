package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Tarjeta;

import java.util.List;

public interface TarjetaDAO {
    void guardar(Tarjeta tarjeta);
    void actualizar(Tarjeta tarjeta);
    void bloquear(int tarjetaId);
    void desbloquear(int tarjetaId);

    List<Tarjeta> listarPorUsuario(int usuarioId);
    Tarjeta buscarPorId(int id);
}
