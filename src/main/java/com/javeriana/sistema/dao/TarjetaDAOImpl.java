package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Tarjeta;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarjetaDAOImpl implements TarjetaDAO {

    private Connection conexion;

    public TarjetaDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Tarjeta tarjeta) {
        String sql = "INSERT INTO tarjetas (usuario_id, tipo, estado, cupo_total, cupo_disponible, deuda_actual, activa, bloqueada, numero, fecha_vencimiento, cvv, cuenta_asociada_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tarjeta.getUsuarioId());
            stmt.setString(2, tarjeta.getTipo());
            stmt.setString(3, tarjeta.getEstado());
            stmt.setDouble(4, tarjeta.getCupoTotal());
            stmt.setDouble(5, tarjeta.getCupoDisponible());
            stmt.setDouble(6, tarjeta.getDeuda());
            stmt.setBoolean(7, tarjeta.isActiva());
            stmt.setBoolean(8, tarjeta.isBloqueada());
            stmt.setString(9, tarjeta.getNumero());
            stmt.setDate(10, Date.valueOf(tarjeta.getFechaVencimiento()));
            stmt.setString(11, tarjeta.getCvv());

            if (tarjeta.getCuentaAsociadaId() != null) {
                stmt.setInt(12, tarjeta.getCuentaAsociadaId());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Tarjeta tarjeta) {
        String sql = "UPDATE tarjetas SET tipo = ?, estado = ?, cupo_total = ?, cupo_disponible = ?, deuda_actual = ?, activa = ?, bloqueada = ?, numero = ?, fecha_vencimiento = ?, cvv = ?, cuenta_asociada_id = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tarjeta.getTipo());
            stmt.setString(2, tarjeta.getEstado());
            stmt.setDouble(3, tarjeta.getCupoTotal());
            stmt.setDouble(4, tarjeta.getCupoDisponible());
            stmt.setDouble(5, tarjeta.getDeuda());
            stmt.setBoolean(6, tarjeta.isActiva());
            stmt.setBoolean(7, tarjeta.isBloqueada());
            stmt.setString(8, tarjeta.getNumero());
            stmt.setDate(9, Date.valueOf(tarjeta.getFechaVencimiento()));
            stmt.setString(10, tarjeta.getCvv());

            if (tarjeta.getCuentaAsociadaId() != null) {
                stmt.setInt(11, tarjeta.getCuentaAsociadaId());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setInt(12, tarjeta.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bloquear(int tarjetaId) {
        String sql = "UPDATE tarjetas SET bloqueada = true, estado = 'Bloqueada' WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tarjetaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desbloquear(int tarjetaId) {
        String sql = "UPDATE tarjetas SET bloqueada = false, activa = true, estado = 'Activa' WHERE id = ?";
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
        Integer cuentaId = (rs.getObject("cuenta_asociada_id") != null) ? rs.getInt("cuenta_asociada_id") : null;

        return new Tarjeta(
                rs.getInt("id"),
                rs.getInt("usuario_id"),
                rs.getString("tipo"),
                rs.getString("estado"),
                rs.getDouble("cupo_total"),
                rs.getDouble("cupo_disponible"),
                rs.getDouble("deuda_actual"),
                rs.getBoolean("activa"),
                rs.getBoolean("bloqueada"),
                rs.getString("numero"),
                rs.getDate("fecha_vencimiento").toLocalDate(),
                rs.getString("cvv"),
                cuentaId
        );
    }
}
