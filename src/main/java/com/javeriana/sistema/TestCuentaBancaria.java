package com.javeriana.sistema;

import com.javeriana.sistema.services.CuentaBancariaService;

public class TestCuentaBancaria {
    public static void main(String[] args) {
        CuentaBancariaService cuentaService = new CuentaBancariaService();

        // Crear una cuenta para el usuario con ID 1
        cuentaService.crearCuenta(1, "Ahorro", 5000.0);

        // Listar cuentas del usuario 1
        System.out.println(cuentaService.obtenerCuentasDeUsuario(1));

        // Actualizar saldo de la cuenta con ID 1
        cuentaService.actualizarSaldo(1, 7000.0);

        // Ver cuentas nuevamente
        System.out.println(cuentaService.obtenerCuentasDeUsuario(1));
    }
}
