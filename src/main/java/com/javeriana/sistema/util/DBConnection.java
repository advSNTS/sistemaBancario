package com.javeriana.sistema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:h2:~/sistema_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static boolean yaMostroMensaje = false;

    // Siempre devuelve una nueva conexión para evitar errores de conexión cerrada
    public static Connection getInstance() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!yaMostroMensaje) {
            System.out.println("Conexión a la BD establecida en " + URL);
            yaMostroMensaje = true;
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Ejecutar el script solo 1 vez desde HelloApplication
    public static void ejecutarScript() {
        try (Connection conn = getInstance()) {
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

            Statement stmt = conn.createStatement();
            stmt.execute(sql.toString());
            System.out.println("Tablas creadas exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el script SQL.");
        }
    }
}