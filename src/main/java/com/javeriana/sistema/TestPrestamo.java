package com.javeriana.sistema;

import com.javeriana.sistema.services.PrestamoService;

public class TestPrestamo {
    public static void main(String[] args) {
        PrestamoService prestamoService = new PrestamoService();

        // Solicitar un préstamo para el usuario con ID 1
        prestamoService.solicitarPrestamo(1, 10000.0, 5.0, 12);

        // Listar préstamos del usuario con ID 1
        System.out.println(prestamoService.obtenerPrestamosDeUsuario(1));
    }
}
