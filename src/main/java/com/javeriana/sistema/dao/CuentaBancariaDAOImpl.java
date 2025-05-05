package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDAOImpl implements CuentaBancariaDAO {

    @Override
    public void guardar(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas_bancarias (usuario_id, tipo, saldo, limite_alerta) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuenta.getUsuarioId());
            stmt.setString(2, cuenta.getTipo());
            stmt.setDouble(3, cuenta.getSaldo());
            if (cuenta.getLimiteAlerta() != null) {
                stmt.setDouble(4, cuenta.getLimiteAlerta());
            } else {
                stmt.setNull(4, Types.DOUBLE);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CuentaBancaria buscarPorId(int id) {
        String sql = "SELECT * FROM cuentas_bancarias WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Double limite = rs.getObject("limite_alerta") != null ? rs.getDouble("limite_alerta") : null;
                return new CuentaBancaria(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        limite
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CuentaBancaria> listarPorUsuario(int usuarioId) {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_bancarias WHERE usuario_id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Double limite = rs.getObject("limite_alerta") != null ? rs.getDouble("limite_alerta") : null;
                CuentaBancaria cuenta = new CuentaBancaria(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        limite
                );
                cuentas.add(cuenta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuentas;
    }

    @Override
    public void actualizar(CuentaBancaria cuenta) {
        String sql = "UPDATE cuentas_bancarias SET tipo = ?, saldo = ?, limite_alerta = ? WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cuenta.getTipo());
            stmt.setDouble(2, cuenta.getSaldo());
            if (cuenta.getLimiteAlerta() != null) {
                stmt.setDouble(3, cuenta.getLimiteAlerta());
            } else {
                stmt.setNull(3, Types.DOUBLE);
            }
            stmt.setInt(4, cuenta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cuentas_bancarias WHERE id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
