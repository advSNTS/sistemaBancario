package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public UsuarioDAO() {
        // Crear tabla si no existe
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
}