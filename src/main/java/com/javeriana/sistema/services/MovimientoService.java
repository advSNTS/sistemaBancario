package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.MovimientoDAO;
import com.javeriana.sistema.dao.MovimientoDAOImpl;
import com.javeriana.sistema.model.Movimiento;

import java.util.List;

public class MovimientoService {

    private final MovimientoDAO movimientoDAO = new MovimientoDAOImpl();

    public void registrarMovimiento(Movimiento movimiento) {

        System.out.println("Registrando movimiento: " + movimiento.getTipo() +
                " | Monto: $" + movimiento.getMonto() +
                " | Origen: " + movimiento.getCuentaIdOrigen() +
                " | Destino: " + movimiento.getCuentaIdDestino() +
                " | Fecha: " + movimiento.getFecha());

        movimientoDAO.guardar(movimiento);
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(int cuentaId) {
        return movimientoDAO.listarPorCuenta(cuentaId);
    }

    public List<Movimiento> obtenerTodos() {
        return movimientoDAO.listarTodos();
    }

    public List<Movimiento> obtenerMovimientosDeUsuario(int usuarioId) {
        return movimientoDAO.listarPorUsuario(usuarioId);
    }
}
