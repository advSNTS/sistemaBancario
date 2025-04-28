package com.javeriana.sistema.services;

import com.javeriana.sistema.dao.PrestamoDAO;
import com.javeriana.sistema.dao.PrestamoDAOImpl;
import com.javeriana.sistema.model.Prestamo;

import java.time.LocalDate;
import java.util.List;

public class PrestamoService {
    private PrestamoDAO prestamoDAO = new PrestamoDAOImpl();

    public void solicitarPrestamo(int usuarioId, double monto, double tasaInteres, int plazoMeses) {
        Prestamo prestamo = new Prestamo(0, usuarioId, monto, tasaInteres, plazoMeses, monto, LocalDate.now());
        prestamoDAO.guardar(prestamo);
        System.out.println("Préstamo registrado correctamente.");
    }

    public List<Prestamo> obtenerPrestamosDeUsuario(int usuarioId) {
        return prestamoDAO.listarPorUsuario(usuarioId);
    }

    public boolean puedeSolicitarPrestamo(int usuarioId) {
        List<Prestamo> prestamos = obtenerPrestamosDeUsuario(usuarioId);
        if (prestamos.size() >= 2) {
            return false; // No puede tener más de 2 préstamos
        }
        for (Prestamo p : prestamos) {
            if (p.getSaldoPendiente() > 0) {
                return false; // Si debe algo, no puede pedir otro préstamo
            }
        }
        return true;
    }

    public void actualizarPrestamo(Prestamo prestamo) {
        prestamoDAO.actualizar(prestamo);
    }
}
