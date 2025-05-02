package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Transferencia;
import java.util.List;

public interface TransferenciaDAO {
    void registrar(Transferencia transferencia);
    List<Transferencia> listarPorCuenta(int cuentaId);
    List<Transferencia> listarPorUsuario(int usuarioId);
}
