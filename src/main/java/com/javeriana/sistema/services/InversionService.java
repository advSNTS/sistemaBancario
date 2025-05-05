package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.dao.InversionDAO;
import com.javeriana.sistema.dao.InversionDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Inversion;

import java.time.LocalDate;
import java.util.List;

public class InversionService {

    private final InversionDAO inversionDAO = new InversionDAOImpl();
    private final CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public void crearInversion(int cuentaId, double monto, int plazoMeses) throws Exception {
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);

        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada");
        }
        if (monto <= 0 || cuenta.getSaldo() < monto) {
            throw new Exception("Saldo insuficiente para invertir");
        }

        double interes = switch (plazoMeses) {
            case 6 -> 0.05;
            case 12 -> 0.10;
            case 24 -> 0.15;
            default -> throw new Exception("Plazo no válido");
        };

        Inversion inversion = new Inversion(
                0, cuentaId, monto, plazoMeses, interes,
                LocalDate.now(), false
        );

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        cuentaDAO.actualizar(cuenta);
        inversionDAO.guardar(inversion);
    }

    public List<Inversion> obtenerInversionesDeUsuario(int usuarioId) {
        return inversionDAO.listarPorUsuario(usuarioId);
    }

    public void simularFinalizacionInversiones() {
        List<Inversion> inversiones = inversionDAO.listarFinalizables();
        LocalDate hoy = LocalDate.now();

        for (Inversion inv : inversiones) {
            LocalDate fechaFin = inv.getFechaInicio().plusMonths(inv.getPlazoMeses());
            if (!inv.isFinalizada() && !hoy.isBefore(fechaFin)) {
                double ganancia = inv.getMonto() * (1 + inv.getInteres());

                CuentaBancaria cuenta = cuentaDAO.buscarPorId(inv.getCuentaId());
                if (cuenta != null) {
                    cuenta.setSaldo(cuenta.getSaldo() + ganancia);
                    cuentaDAO.actualizar(cuenta);
                }

                inv.setFinalizada(true);
                inversionDAO.actualizar(inv);
            }
        }
    }

    public void simularFinalizacionConFecha(LocalDate fechaSimulada) {
        List<Inversion> inversiones = inversionDAO.listarFinalizables();

        for (Inversion inv : inversiones) {
            LocalDate fechaFin = inv.getFechaInicio().plusMonths(inv.getPlazoMeses());

            if (!inv.isFinalizada() && !fechaSimulada.isBefore(fechaFin)) {
                double gananciaTotal = inv.getMonto() * (1 + inv.getInteres());

                CuentaBancaria cuenta = cuentaDAO.buscarPorId(inv.getCuentaId());
                if (cuenta != null) {
                    cuenta.setSaldo(cuenta.getSaldo() + gananciaTotal);
                    cuentaDAO.actualizar(cuenta);
                }

                inv.setFinalizada(true);
                inversionDAO.actualizar(inv);
            }
        }
    }
}
