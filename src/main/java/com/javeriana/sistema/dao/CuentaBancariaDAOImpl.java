package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDAOImpl implements CuentaBancariaDAO {
    private Connection conexion = DBConnection.getInstance();

    @Override
    public void guardar(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas_bancarias (usuario_id, tipo, saldo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cuenta.getUsuarioId());
            stmt.setString(2, cuenta.getTipo());
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CuentaBancaria buscarPorId(int id) {
        String sql = "SELECT * FROM cuentas_bancarias WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CuentaBancaria(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo")
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
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cuentas.add(new CuentaBancaria(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }

    @Override
    public void actualizar(CuentaBancaria cuenta) {
        String sql = "UPDATE cuentas_bancarias SET saldo=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDouble(1, cuenta.getSaldo());
            stmt.setInt(2, cuenta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cuentas_bancarias WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
