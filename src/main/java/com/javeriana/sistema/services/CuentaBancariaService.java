package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;

import java.util.List;

public class CuentaBancariaService {
    private CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public void crearCuenta(int usuarioId, String tipo, double saldoInicial) {
        CuentaBancaria cuenta = new CuentaBancaria(0, usuarioId, tipo, saldoInicial);
        cuentaDAO.guardar(cuenta);
        System.out.println("✅ Cuenta creada correctamente.");
    }

    public List<CuentaBancaria> obtenerCuentasDeUsuario(int usuarioId) {
        return cuentaDAO.listarPorUsuario(usuarioId);
    }

    public void actualizarSaldo(int cuentaId, double nuevoSaldo) {
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);
        if (cuenta != null) {
            cuenta.setSaldo(nuevoSaldo);
            cuentaDAO.actualizar(cuenta);
            System.out.println("✅ Saldo actualizado correctamente.");
        }
    }
}
