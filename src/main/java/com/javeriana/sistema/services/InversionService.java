package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.InversionDAO;
import com.javeriana.sistema.dao.InversionDAOImpl;
import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Inversion;
import com.javeriana.sistema.model.Movimiento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InversionService {

    private final InversionDAO inversionDAO = new InversionDAOImpl();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();
    private final MovimientoService movimientoService = new MovimientoService();

    public void crearInversion(int cuentaId, double monto, int plazoMeses) {
        double interes = plazoMeses == 6 ? 5.0 : (plazoMeses == 12 ? 10.0 : 15.0);
        LocalDate hoy = LocalDate.now();

        Inversion inversion = new Inversion(
                0,
                cuentaId,
                monto,
                plazoMeses,
                interes,
                hoy,
                false
        );

        inversionDAO.guardar(inversion);

        // Descontar el monto de la cuenta
        CuentaBancaria cuenta = cuentaService.obtenerCuentaPorId(cuentaId);
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        cuentaService.actualizarCuenta(cuenta);

        // Registrar movimiento de inversión
        Movimiento movimiento = new Movimiento(
                0,
                cuentaId,
                null,
                "Apertura Inversión",
                monto,
                LocalDateTime.now()
        );
        movimientoService.registrarMovimiento(movimiento);
    }

    public void simularFinalizacionConFecha(LocalDate fechaSimulada) {
        List<Inversion> inversiones = inversionDAO.listarFinalizables();

        for (Inversion inversion : inversiones) {
            if (!inversion.isFinalizada()) {
                LocalDate fechaFinalizacion = inversion.getFechaInicio().plusMonths(inversion.getPlazoMeses());

                if (!fechaFinalizacion.isAfter(fechaSimulada)) {
                    // Calcular valor total
                    double total = inversion.calcularValorFinal();

                    // Actualizar cuenta
                    CuentaBancaria cuenta = cuentaService.obtenerCuentaPorId(inversion.getCuentaId());
                    cuenta.setSaldo(cuenta.getSaldo() + total);
                    cuentaService.actualizarCuenta(cuenta);

                    // Registrar movimiento
                    Movimiento movimiento = new Movimiento(
                            0,
                            null,
                            cuenta.getId(),
                            "Inversión Finalizada",
                            total,
                            LocalDateTime.now()
                    );
                    movimientoService.registrarMovimiento(movimiento);

                    // Marcar como finalizada
                    inversion.setFinalizada(true);
                    inversionDAO.actualizar(inversion);
                }
            }
        }
    }
}
