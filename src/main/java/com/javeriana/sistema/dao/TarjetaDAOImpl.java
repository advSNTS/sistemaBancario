package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarjetaDAOImpl implements TarjetaDAO {

    private final Connection conexion = DBConnection.getInstance();

    @Override
    public void guardar(Tarjeta tarjeta) {
        String sql = "INSERT INTO tarjetas (usuario_id, tipo, cupo_total, cupo_disponible, deuda, activa, bloqueada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tarjeta.getUsuarioId());
            stmt.setString(2, tarjeta.getTipo());
            stmt.setDouble(3, tarjeta.getCupoTotal());
            stmt.setDouble(4, tarjeta.getCupoDisponible());
            stmt.setDouble(5, tarjeta.getDeuda());
            stmt.setBoolean(6, tarjeta.isActiva());
            stmt.setBoolean(7, tarjeta.isBloqueada());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Tarjeta tarjeta) {
        String sql = "UPDATE tarjetas SET tipo = ?, cupo_total = ?, cupo_disponible = ?, deuda = ?, activa = ?, bloqueada = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tarjeta.getTipo());
            stmt.setDouble(2, tarjeta.getCupoTotal());
            stmt.setDouble(3, tarjeta.getCupoDisponible());
            stmt.setDouble(4, tarjeta.getDeuda());
            stmt.setBoolean(5, tarjeta.isActiva());
            stmt.setBoolean(6, tarjeta.isBloqueada());
            stmt.setInt(7, tarjeta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bloquear(int tarjetaId) {
        String sql = "UPDATE tarjetas SET bloqueada = true WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tarjetaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tarjeta> listarPorUsuario(int usuarioId) {
        List<Tarjeta> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM tarjetas WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tarjetas.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    @Override
    public Tarjeta buscarPorId(int id) {
        String sql = "SELECT * FROM tarjetas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Tarjeta mapear(ResultSet rs) throws SQLException {
        return new Tarjeta(
                rs.getInt("id"),
                rs.getInt("usuario_id"),
                rs.getString("tipo"),
                rs.getDouble("cupo_total"),
                rs.getDouble("cupo_disponible"),
                rs.getDouble("deuda"),
                rs.getBoolean("activa"),
                rs.getBoolean("bloqueada")
        );
    }
}
