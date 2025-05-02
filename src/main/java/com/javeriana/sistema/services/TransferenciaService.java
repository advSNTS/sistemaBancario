package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.dao.TransferenciaDAO;
import com.javeriana.sistema.dao.TransferenciaDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Transferencia;

import java.time.LocalDateTime;
import java.util.List;

public class TransferenciaService {

    private TransferenciaDAO transferenciaDAO = new TransferenciaDAOImpl();
    private CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public boolean realizarTransferencia(int cuentaOrigenId, int cuentaDestinoId, double monto) {
        CuentaBancaria origen = cuentaDAO.buscarPorId(cuentaOrigenId);
        CuentaBancaria destino = cuentaDAO.buscarPorId(cuentaDestinoId);

        if (origen == null || destino == null) return false;
        if (origen.getSaldo() < monto || monto <= 0) return false;

        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);

        cuentaDAO.actualizar(origen);
        cuentaDAO.actualizar(destino);

        Transferencia transferencia = new Transferencia(0, cuentaOrigenId, cuentaDestinoId, monto, LocalDateTime.now());
        transferenciaDAO.registrar(transferencia);

        return true;
    }

    public List<Transferencia> obtenerTransferenciasDeCuenta(int cuentaId) {
        return transferenciaDAO.listarPorCuenta(cuentaId);
    }

    public List<Transferencia> obtenerTransferenciasPorUsuario(int usuarioId) {
        return transferenciaDAO.listarPorUsuario(usuarioId);
    }

    public void registrar(Transferencia t) {
        transferenciaDAO.registrar(t);
    }
}
