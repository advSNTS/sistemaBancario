package com.javeriana.sistema.util;

import com.javeriana.sistema.model.PagoProgramado;
import com.javeriana.sistema.services.PagoProgramadoService;
import com.javeriana.sistema.services.CuentaBancariaService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EjecutorPagosProgramados {

    private final PagoProgramadoService pagoService = new PagoProgramadoService();
    private final CuentaBancariaService cuentaService = new CuentaBancariaService();

    public void iniciar() {
        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<PagoProgramado> pendientes = pagoService.obtenerPagosPorEjecutar();
                for (PagoProgramado pago : pendientes) {
                    try {
                        cuentaService.transferirEntreCuentas(
                                pago.getCuentaOrigenId(),
                                pago.getCuentaDestinoId(),
                                pago.getMonto()
                        );
                        pagoService.marcarComoEjecutado(pago.getId());
                        System.out.println("Pago programado ejecutado: ID " + pago.getId());
                    } catch (Exception e) {
                        System.err.println("Error al ejecutar pago programado: ID " + pago.getId());
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 60000); // Ejecuta cada 60 segundos
    }
}
