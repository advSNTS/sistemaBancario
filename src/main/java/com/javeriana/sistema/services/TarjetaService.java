package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.TarjetaDAO;
import com.javeriana.sistema.dao.TarjetaDAOImpl;
import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.model.CuentaBancaria;

import java.util.List;

public class TarjetaService {

    private final TarjetaDAO tarjetaDAO = new TarjetaDAOImpl();
    private final CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public void solicitarTarjeta(int usuarioId, String tipo, double cupo) {
        Tarjeta tarjeta = new Tarjeta(0, usuarioId, tipo, cupo, cupo, 0.0, false, false);
        tarjetaDAO.guardar(tarjeta);
    }

    public void activarTarjeta(int tarjetaId) {
        Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
        if (tarjeta != null && !tarjeta.isBloqueada()) {
            tarjeta.setActiva(true);
            tarjetaDAO.actualizar(tarjeta);
        }
    }

    public void bloquearTarjeta(int tarjetaId) {
        tarjetaDAO.bloquear(tarjetaId);
    }

    public List<Tarjeta> obtenerTarjetasDeUsuario(int usuarioId) {
        return tarjetaDAO.listarPorUsuario(usuarioId);
    }

    public Tarjeta buscarPorId(int id) {
        return tarjetaDAO.buscarPorId(id);
    }

    public void realizarPagoConTarjeta(int tarjetaId, double monto) throws Exception {
        Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
        if (tarjeta == null || !tarjeta.isActiva() || tarjeta.isBloqueada()) {
            throw new Exception("Tarjeta inválida, inactiva o bloqueada.");
        }
        if (tarjeta.getCupoDisponible() < monto) {
            throw new Exception("Cupo insuficiente.");
        }
        tarjeta.setCupoDisponible(tarjeta.getCupoDisponible() - monto);
        tarjeta.setDeuda(tarjeta.getDeuda() + monto);
        tarjetaDAO.actualizar(tarjeta);
    }

    public void pagarDeudaConCuenta(int tarjetaId, int cuentaId, double monto) throws Exception {
        Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);

        if (tarjeta == null || cuenta == null) {
            throw new Exception("Tarjeta o cuenta no encontrada.");
        }
        if (tarjeta.getDeuda() < monto) {
            throw new Exception("El monto excede la deuda actual.");
        }
        if (cuenta.getSaldo() < monto) {
            throw new Exception("Saldo insuficiente en la cuenta.");
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        tarjeta.setDeuda(tarjeta.getDeuda() - monto);
        tarjeta.setCupoDisponible(tarjeta.getCupoDisponible() + monto);

        cuentaDAO.actualizar(cuenta);
        tarjetaDAO.actualizar(tarjeta);
    }

    // Alias para usar desde controlador
    public void usarTarjeta(int tarjetaId, double monto) throws Exception {
        realizarPagoConTarjeta(tarjetaId, monto);
    }

    public void pagarDeudaDesdeCuenta(int tarjetaId, int cuentaId, double monto) throws Exception {
        pagarDeudaConCuenta(tarjetaId, cuentaId, monto);
    }
}
