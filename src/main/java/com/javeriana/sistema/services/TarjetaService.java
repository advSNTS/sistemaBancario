package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.TarjetaDAO;
import com.javeriana.sistema.dao.TarjetaDAOImpl;
import com.javeriana.sistema.dao.CuentaBancariaDAO;
import com.javeriana.sistema.dao.CuentaBancariaDAOImpl;
import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.model.CuentaBancaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class TarjetaService {

    private final TarjetaDAO tarjetaDAO = new TarjetaDAOImpl();
    private final CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAOImpl();

    public Tarjeta solicitarTarjeta(int usuarioId, String tipo, double cupo) throws Exception {
        return solicitarTarjeta(usuarioId, tipo, cupo, null);
    }

    public Tarjeta solicitarTarjeta(int usuarioId, String tipo, double cupo, Integer cuentaAsociadaId) throws Exception {
        String estado = "Activa";
        String numero = generarNumeroTarjeta();
        LocalDate fechaVencimiento = generarFechaVencimiento();
        String cvv = generarCVV();

        if ("Débito".equalsIgnoreCase(tipo)) {
            if (cuentaAsociadaId == null) {
                throw new Exception("Se debe asociar una cuenta a la tarjeta débito.");
            }
            CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaAsociadaId);
            List<String> tiposValidos = List.of("Ahorro", "Ahorros", "Cuenta de Ahorro");
            if (cuenta == null || tiposValidos.stream().noneMatch(t -> t.equalsIgnoreCase(cuenta.getTipo()))) {
                throw new Exception("Cuenta asociada inválida para tarjeta débito.");
            }
            cupo = cuenta.getSaldo(); // cupo se toma del saldo de la cuenta
        }

        Tarjeta tarjeta = new Tarjeta(
                0, usuarioId, tipo, estado,
                cupo, cupo, 0.0,
                true, false,
                numero, fechaVencimiento, cvv,
                cuentaAsociadaId
        );

        tarjetaDAO.guardar(tarjeta);
        return tarjeta;
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

    public void desbloquearTarjeta(int tarjetaId) {
        Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
        if (tarjeta != null) {
            tarjeta.setBloqueada(false);
            tarjeta.setActiva(true);
            tarjeta.setEstado("Activa");
            tarjetaDAO.actualizar(tarjeta);
        }
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

        if ("Débito".equalsIgnoreCase(tarjeta.getTipo())) {
            // Sacar dinero directamente de la cuenta asociada
            if (tarjeta.getCuentaAsociadaId() == null) {
                throw new Exception("La tarjeta débito no está vinculada a ninguna cuenta.");
            }
            CuentaBancaria cuenta = cuentaDAO.buscarPorId(tarjeta.getCuentaAsociadaId());
            if (cuenta == null || cuenta.getSaldo() < monto) {
                throw new Exception("Saldo insuficiente en la cuenta asociada.");
            }

            cuenta.setSaldo(cuenta.getSaldo() - monto);
            cuentaDAO.actualizar(cuenta);
        } else if ("Crédito".equalsIgnoreCase(tarjeta.getTipo())) {
            if (tarjeta.getCupoDisponible() < monto) {
                throw new Exception("Cupo insuficiente.");
            }
            tarjeta.setCupoDisponible(tarjeta.getCupoDisponible() - monto);
            tarjeta.setDeuda(tarjeta.getDeuda() + monto);
            tarjetaDAO.actualizar(tarjeta);
        } else {
            throw new Exception("Tipo de tarjeta no reconocido.");
        }
    }

    public void pagarDeudaConCuenta(int tarjetaId, int cuentaId, double monto) throws Exception {
        Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
        CuentaBancaria cuenta = cuentaDAO.buscarPorId(cuentaId);

        if (tarjeta == null || cuenta == null) {
            throw new Exception("Tarjeta o cuenta no encontrada.");
        }

        if (!"Crédito".equalsIgnoreCase(tarjeta.getTipo())) {
            throw new Exception("Solo se puede pagar deuda de tarjetas de crédito.");
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

    public void usarTarjeta(int tarjetaId, double monto) throws Exception {
        realizarPagoConTarjeta(tarjetaId, monto);
    }

    public void pagarDeudaDesdeCuenta(int tarjetaId, int cuentaId, double monto) throws Exception {
        pagarDeudaConCuenta(tarjetaId, cuentaId, monto);
    }

    // Métodos auxiliares
    private String generarNumeroTarjeta() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
            if ((i + 1) % 4 == 0 && i < 15) sb.append(" ");
        }
        return sb.toString();
    }

    private LocalDate generarFechaVencimiento() {
        return LocalDate.now().plusYears(4);
    }

    private String generarCVV() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }
}
