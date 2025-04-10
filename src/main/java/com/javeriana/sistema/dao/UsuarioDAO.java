package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public UsuarioDAO() {
        DBConnection.crearTablaUsuarios();
    }

    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (cedula, nombre, apellido, contrasena) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCedula());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getContrasena());
            stmt.executeUpdate();
            System.out.println("Usuario insertado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Usuario buscarPorCedulaYContrasena(String cedula, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE cedula = ? AND contrasena = ?";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No encontrado
    }
    public boolean existeUsuarioPorCedula(String cedula) {
        String sql = "SELECT 1 FROM usuarios WHERE cedula = ?";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true si hay algún resultado
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}