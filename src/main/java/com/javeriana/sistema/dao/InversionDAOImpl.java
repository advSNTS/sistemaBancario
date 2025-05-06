package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Inversion;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InversionDAOImpl implements InversionDAO {

    private Connection conexion;

    public InversionDAOImpl() {
        try {
            this.conexion = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Inversion inversion) {
        String sql = "INSERT INTO inversiones (cuenta_id, monto, plazo_meses, interes, fecha_inicio, finalizada) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, inversion.getCuentaId());
            stmt.setDouble(2, inversion.getMonto());
            stmt.setInt(3, inversion.getPlazoMeses());
            stmt.setDouble(4, inversion.getInteres());
            stmt.setDate(5, Date.valueOf(inversion.getFechaInicio()));
            stmt.setBoolean(6, inversion.isFinalizada());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Inversion inversion) {
        String sql = "UPDATE inversiones SET finalizada = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, inversion.isFinalizada());
            stmt.setInt(2, inversion.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Inversion> listarPorUsuario(int usuarioId) {
        String sql = "SELECT i.* FROM inversiones i " +
                "JOIN cuentas_bancarias c ON i.cuenta_id = c.id " +
                "WHERE c.usuario_id = ?";
        List<Inversion> lista = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
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
    public List<Inversion> listarFinalizables() {
        String sql = "SELECT * FROM inversiones WHERE finalizada = FALSE";
        List<Inversion> lista = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Inversion mapear(ResultSet rs) throws SQLException {
        return new Inversion(
                rs.getInt("id"),
                rs.getInt("cuenta_id"),
                rs.getDouble("monto"),
                rs.getInt("plazo_meses"),
                rs.getDouble("interes"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getBoolean("finalizada")
        );
    }
}
