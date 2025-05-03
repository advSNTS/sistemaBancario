package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.PagoProgramadoDAO;
import com.javeriana.sistema.dao.PagoProgramadoDAOImpl;
import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.PagoProgramado;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagoProgramadoService {

    private final PagoProgramadoDAO dao = new PagoProgramadoDAOImpl();
    private final CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public void guardar(PagoProgramado pago) {
        dao.guardar(pago);
    }

    public List<PagoProgramado> obtenerPagosPorUsuario(int usuarioId) {
        List<PagoProgramado> resultado = new ArrayList<>();
        List<CuentaBancaria> cuentas = cuentaDAO.listarPorUsuario(usuarioId);

        for (CuentaBancaria cuenta : cuentas) {
            resultado.addAll(dao.obtenerPorCuenta(cuenta.getId()));
        }
        return resultado;
    }

    public List<PagoProgramado> obtenerPagosPorEjecutar() {
        return dao.obtenerNoEjecutadosHasta(LocalDateTime.now());
    }

    public List<PagoProgramado> obtenerPagosDeCuenta(int cuentaId) {
        return dao.obtenerPorCuenta(cuentaId);
    }

    public void marcarComoEjecutado(int id) {
        dao.marcarComoEjecutado(id);
    }

    public List<PagoProgramado> listarTodos() {
        return dao.listarTodos();
    }
}
