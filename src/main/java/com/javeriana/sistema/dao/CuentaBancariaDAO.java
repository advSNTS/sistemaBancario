package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.CuentaBancaria;
import java.util.List;

public interface CuentaBancariaDAO {
    void guardar(CuentaBancaria cuenta);
    CuentaBancaria buscarPorId(int id);
    List<CuentaBancaria> listarPorUsuario(int usuarioId);
    void actualizar(CuentaBancaria cuenta);
    void eliminar(int id);
}
