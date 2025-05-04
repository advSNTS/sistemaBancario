package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.dao.UsuarioDAO;
import com.javeriana.sistema.dao.UsuarioDAOImpl;


import java.util.List;

public class CuentaBancariaService {

    private CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    // Metodo adicional que recibe el objeto CuentaBancaria directamente
    public void crearCuenta(CuentaBancaria cuenta) {
        cuentaDAO.guardar(cuenta);
        System.out.println("Cuenta creada correctamente.");
    }

    public List<CuentaBancaria> obtenerCuentasDeUsuario(int usuarioId) {
        return cuentaDAO.listarPorUsuario(usuarioId);
    }

    public void actualizarSaldo(int cuentaId, double nuevoSaldo) {
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);
        if (cuenta != null) {
            cuenta.setSaldo(nuevoSaldo);
            cuentaDAO.actualizar(cuenta);
            System.out.println("Saldo actualizado correctamente.");
        }
    }

    public void depositar(int cuentaId, double monto) {
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);
        if (cuenta != null) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaDAO.actualizar(cuenta);
        }
    }

    public void retirar(int cuentaId, double monto) {
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);
        if (cuenta != null) {
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            cuentaDAO.actualizar(cuenta);
        }
    }

    public void actualizarCuenta(CuentaBancaria cuenta) {
        cuentaDAO.actualizar(cuenta);
    }

    public CuentaBancaria buscarPorId(int id) {
        return cuentaDAO.buscarPorId(id);
    }

    public Usuario buscarUsuarioPorCedula(String cedula) {
        UsuarioDAO dao = new UsuarioDAOImpl();
        return dao.buscarPorCedula(cedula);
    }

    public void transferirEntreCuentas(int cuentaOrigenId, int cuentaDestinoId, double monto) throws Exception {
        CuentaBancaria cuentaOrigen = cuentaDAO.buscarPorId(cuentaOrigenId);
        CuentaBancaria cuentaDestino = cuentaDAO.buscarPorId(cuentaDestinoId);

        if (cuentaOrigen == null) throw new Exception("Cuenta origen no encontrada");
        if (cuentaDestino == null) throw new Exception("Cuenta destino no encontrada");

        if (cuentaOrigen.getSaldo() < monto)
            throw new Exception("Fondos insuficientes en cuenta origen");

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        cuentaDAO.actualizar(cuentaOrigen);
        cuentaDAO.actualizar(cuentaDestino);
    }
}
