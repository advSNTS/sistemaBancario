package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Prestamo;
import java.util.List;

public interface PrestamoDAO {
    void guardar(Prestamo prestamo);
    Prestamo buscarPorId(int id);
    List<Prestamo> listarPorUsuario(int usuarioId);
    void actualizar(Prestamo prestamo);
    void eliminar(int id);
}
