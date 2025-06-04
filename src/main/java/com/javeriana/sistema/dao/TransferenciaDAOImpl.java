package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaDAOImpl implements TransferenciaDAO {
    private Connection conexion;

    public TransferenciaDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrar(Transferencia t) {
        String sql = "INSERT INTO transferencias (cuenta_origen_id, cuenta_destino_id, monto, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, t.getCuentaOrigenId());
            stmt.setInt(2, t.getCuentaDestinoId());
            stmt.setDouble(3, t.getMonto());
            stmt.setTimestamp(4, Timestamp.valueOf(t.getFecha()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transferencia> listarPorCuenta(int cuentaId) {
        List<Transferencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM transferencias WHERE cuenta_origen_id = ? OR cuenta_destino_id = ? ORDER BY fecha DESC";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cuentaId);
            stmt.setInt(2, cuentaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Transferencia(
                        rs.getInt("id"),
                        rs.getInt("cuenta_origen_id"),
                        rs.getInt("cuenta_destino_id"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Transferencia> listarPorUsuario(int usuarioId) {
        List<Transferencia> lista = new ArrayList<>();
        String sql = """
        SELECT t.* FROM transferencias t
        JOIN cuentas_bancarias c ON t.cuenta_origen_id = c.id
        WHERE c.usuario_id = ?
        ORDER BY t.fecha DESC
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Transferencia(
                        rs.getInt("id"),
                        rs.getInt("cuenta_origen_id"),
                        rs.getInt("cuenta_destino_id"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
