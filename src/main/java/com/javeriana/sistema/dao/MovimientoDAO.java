package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Movimiento;

import java.util.List;

public interface MovimientoDAO {
    void guardar(Movimiento movimiento);
    List<Movimiento> listarPorCuenta(int cuentaId);
    List<Movimiento> listarPorUsuario(int usuarioId);
    List<Movimiento> listarTodos();
}
