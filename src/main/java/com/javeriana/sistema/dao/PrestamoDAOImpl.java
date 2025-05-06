package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Prestamo;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {
    private Connection conexion;

    public PrestamoDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (usuario_id, monto, tasa_interes, plazo_meses, saldo_pendiente, fecha_aprobacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setDouble(2, prestamo.getMonto());
            stmt.setDouble(3, prestamo.getTasaInteres());
            stmt.setInt(4, prestamo.getPlazoMeses());
            stmt.setDouble(5, prestamo.getSaldoPendiente());
            stmt.setDate(6, Date.valueOf(prestamo.getFechaAprobacion()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prestamo buscarPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getDouble("monto"),
                        rs.getDouble("tasa_interes"),
                        rs.getInt("plazo_meses"),
                        rs.getDouble("saldo_pendiente"),
                        rs.getDate("fecha_aprobacion").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prestamo> listarPorUsuario(int usuarioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getDouble("monto"),
                        rs.getDouble("tasa_interes"),
                        rs.getInt("plazo_meses"),
                        rs.getDouble("saldo_pendiente"),
                        rs.getDate("fecha_aprobacion").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET monto = ?, tasa_interes = ?, plazo_meses = ?, saldo_pendiente = ?, fecha_aprobacion = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDouble(1, prestamo.getMonto());
            stmt.setDouble(2, prestamo.getTasaInteres());
            stmt.setInt(3, prestamo.getPlazoMeses());
            stmt.setDouble(4, prestamo.getSaldoPendiente());
            stmt.setDate(5, Date.valueOf(prestamo.getFechaAprobacion()));
            stmt.setInt(6, prestamo.getId());
            stmt.executeUpdate();
            System.out.println("Préstamo actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el préstamo.");
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Préstamo eliminado correctamente.");
            } else {
                System.out.println("No se encontró el préstamo con ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el préstamo.");
        }
    }


}
