package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.PagoProgramado;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoProgramadoDAO {
    void guardar(PagoProgramado pago);
    List<PagoProgramado> obtenerNoEjecutadosHasta(LocalDateTime hasta);
    List<PagoProgramado> obtenerPorCuenta(int cuentaId);
    void marcarComoEjecutado(int id);
    List<PagoProgramado> listarTodos();
}
