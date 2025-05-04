package com.javeriana.sistema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:h2:tcp://localhost/~/sistema_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection conexion;

    public static Connection getInstance() {
        if (conexion == null) {
            try {
                Class.forName("org.h2.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a la BD establecida en " + URL);
                ejecutarScriptSQL(conexion);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Error al conectar a la BD.");
            }
        }
        return conexion;
    }

    private static void ejecutarScriptSQL(Connection conexion) {
        try {
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("schema.sql");
            if (inputStream == null) {
                System.out.println("No se encontró el archivo schema.sql");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sql = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                sql.append(linea).append("\n");
            }
            reader.close();

            Statement stmt = conexion.createStatement();
            stmt.execute(sql.toString());
            System.out.println("Tablas creadas exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el script SQL.");
        }
    }
}
