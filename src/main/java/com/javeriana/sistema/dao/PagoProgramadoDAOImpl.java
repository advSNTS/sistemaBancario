package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.PagoProgramado;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagoProgramadoDAOImpl implements PagoProgramadoDAO {

    private Connection conexion;

    public PagoProgramadoDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(PagoProgramado pago) {
        String sql = "INSERT INTO pagos_programados (cuenta_origen_id, cuenta_destino_id, monto, fecha_ejecucion, ejecutado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pago.getCuentaOrigenId());
            stmt.setInt(2, pago.getCuentaDestinoId()); // ✅ corregido
            stmt.setDouble(3, pago.getMonto());
            stmt.setTimestamp(4, Timestamp.valueOf(pago.getFechaHoraEjecucion()));
            stmt.setBoolean(5, pago.isEjecutado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PagoProgramado> obtenerNoEjecutadosHasta(LocalDateTime hasta) {
        List<PagoProgramado> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos_programados WHERE ejecutado = false AND fecha_ejecucion <= ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(hasta));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<PagoProgramado> obtenerPorCuenta(int cuentaId) {
        List<PagoProgramado> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos_programados WHERE cuenta_origen_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cuentaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void marcarComoEjecutado(int id) {
        String sql = "UPDATE pagos_programados SET ejecutado = true WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PagoProgramado> listarTodos() {
        List<PagoProgramado> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos_programados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private PagoProgramado mapear(ResultSet rs) throws SQLException {
        return new PagoProgramado(
                rs.getInt("id"),
                rs.getInt("cuenta_origen_id"),
                rs.getInt("cuenta_destino_id"), // ✅ corregido
                rs.getDouble("monto"),
                rs.getTimestamp("fecha_ejecucion").toLocalDateTime(),
                rs.getBoolean("ejecutado")
        );
    }
}
