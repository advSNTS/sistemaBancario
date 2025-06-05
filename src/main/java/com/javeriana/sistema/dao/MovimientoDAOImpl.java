package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Movimiento;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAOImpl implements MovimientoDAO {

    private final Connection conexion;

    public MovimientoDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión con la base de datos", e);
        }
    }

    @Override
    public void guardar(Movimiento movimiento) {
        String sql = "INSERT INTO movimientos (cuenta_id_origen, cuenta_id_destino, tipo, monto, fecha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setObject(1, movimiento.getCuentaIdOrigen());
            stmt.setObject(2, movimiento.getCuentaIdDestino());
            stmt.setString(3, movimiento.getTipo());
            stmt.setDouble(4, movimiento.getMonto());
            stmt.setTimestamp(5, Timestamp.valueOf(movimiento.getFecha()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movimiento> listarPorCuenta(int cuentaId) {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos WHERE cuenta_id_origen = ? OR cuenta_id_destino = ? ORDER BY fecha DESC";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cuentaId);
            stmt.setInt(2, cuentaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movimientos.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    @Override
    public List<Movimiento> listarPorUsuario(int usuarioId) {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = """
            SELECT m.* FROM movimientos m
            JOIN cuentas_bancarias c ON m.cuenta_id_origen = c.id OR m.cuenta_id_destino = c.id
            WHERE c.usuario_id = ?
            ORDER BY m.fecha DESC
        """;
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movimientos.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    private Movimiento mapear(ResultSet rs) throws SQLException {
        return new Movimiento(
                rs.getInt("id"),
                (Integer) rs.getObject("cuenta_id_origen"),
                (Integer) rs.getObject("cuenta_id_destino"),
                rs.getString("tipo"),
                rs.getDouble("monto"),
                rs.getTimestamp("fecha").toLocalDateTime()
        );
    }

    @Override
    public List<Movimiento> listarTodos() {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos ORDER BY fecha DESC";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movimientos.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }
}
