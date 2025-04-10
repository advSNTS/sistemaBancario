package com.javeriana.sistema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:h2:~/test"; // Crea archivo en el home
    private static final String USUARIO = "sa";
    private static final String CONTRASENA = "";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(JDBC_URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void crearTablaUsuarios() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                cedula VARCHAR(20),
                nombre VARCHAR(50),
                apellido VARCHAR(50),
                contrasena VARCHAR(100)
            );
            """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'usuarios' creada o ya existe.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
