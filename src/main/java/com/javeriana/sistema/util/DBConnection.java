package com.javeriana.sistema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class DBConnection {

    private static Connection conexion;
    private static boolean esModoPrueba = false;
    private static boolean yaMostroMensaje = false;

    public static void activarModoPrueba() {
        esModoPrueba = true;
        conexion = null;
    }

    public static Connection getInstance() {
        if (conexion == null) {
            try {
                Class.forName("org.h2.Driver");

                String url = esModoPrueba
                        ? "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
                        : "jdbc:h2:~/sistema_db;AUTO_SERVER=TRUE";

                conexion = DriverManager.getConnection(url, "sa", "");

                if (!yaMostroMensaje) {
                    System.out.println("Conexión a la BD " + (esModoPrueba ? "de prueba" : "real") + " establecida.");
                    yaMostroMensaje = true;
                }

                ejecutarScript(conexion);

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Error al conectar a la BD.");
            }
        }
        return conexion;
    }

    public static void ejecutarScript(Connection conexion) {
        try {
            String scriptPath = esModoPrueba ? "test-schema.sql" : "schema.sql";
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream(scriptPath);

            if (inputStream == null) {
                System.out.println("No se encontró el archivo " + scriptPath);
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
            stmt.close();

            System.out.println("Script " + scriptPath + " ejecutado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el script SQL.");
        }
    }
    public static void ejecutarScript() {
        ejecutarScript(getInstance());
    }

}
