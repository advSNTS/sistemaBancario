package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private final Connection conexion = DBConnection.getInstance();

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, clave, pregunta_secreta, respuesta_secreta, cedula) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getPreguntaSecreta());
            stmt.setString(5, usuario.getRespuestaSecreta());
            stmt.setString(6, usuario.getCedula());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("pregunta_secreta"),
                        rs.getString("respuesta_secreta"),
                        rs.getString("cedula")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("pregunta_secreta"),
                        rs.getString("respuesta_secreta"),
                        rs.getString("cedula")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        String sql = "SELECT * FROM usuarios WHERE cedula = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("pregunta_secreta"),
                        rs.getString("respuesta_secreta"),
                        rs.getString("cedula")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizarClavePorCorreo(String correo, String nuevaClave) {
        String sql = "UPDATE usuarios SET clave = ? WHERE correo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nuevaClave);
            stmt.setString(2, correo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("pregunta_secreta"),
                        rs.getString("respuesta_secreta"),
                        rs.getString("cedula")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, clave = ?, pregunta_secreta = ?, respuesta_secreta = ?, cedula = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getPreguntaSecreta());
            stmt.setString(5, usuario.getRespuestaSecreta());
            stmt.setString(6, usuario.getCedula());
            stmt.setInt(7, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
