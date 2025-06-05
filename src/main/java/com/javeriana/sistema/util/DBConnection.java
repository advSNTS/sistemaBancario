package com.javeriana.sistema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class DBConnection {

    private static final String PRODUCTION_URL = "jdbc:h2:tcp://localhost/~/sistema_db";
    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static boolean esModoPrueba = false;
    private static boolean yaMostroMensaje = false;


    public static void activarModoPrueba() {
        esModoPrueba = true;
        yaMostroMensaje = false;
        System.out.println("Modo de prueba activado.");
    }


    public static Connection getInstance() throws SQLException {
        String currentUrl;
        String modeDescription;


        if (esModoPrueba) {
            currentUrl = TEST_URL;
            modeDescription = "de prueba (en memoria)";
        } else {
            currentUrl = PRODUCTION_URL;
            modeDescription = "real (archivo)";
        }

        try {
            Class.forName("org.h2.Driver");


            Connection conn = DriverManager.getConnection(currentUrl, USER, PASSWORD);


            if (!yaMostroMensaje) {
                System.out.println("Conexión a la BD " + modeDescription + " establecida en " + currentUrl);
                yaMostroMensaje = true; // Mark message as shown for this mode
            }

            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: H2 database driver not found. Make sure the H2 JAR is in your classpath.");

            throw new SQLException("Database driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al conectar a la BD " + modeDescription + ".");
            throw e;
        }
    }

    public static void ejecutarScript() {
        String scriptPath;
        String modeDescription;
        if (esModoPrueba) {
            scriptPath = "test-schema.sql";
            modeDescription = "de prueba";
        } else {
            scriptPath = "schema.sql";
            modeDescription = "real";
        }
        try (Connection conn = getInstance()) {
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream(scriptPath);

            if (inputStream == null) {
                System.err.println("Error: No se encontró el archivo " + scriptPath + " en el classpath.");
                System.out.println("Skipping script execution.");
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
            stmt.close();

            System.out.println("Script " + scriptPath + " (" + modeDescription + ") ejecutado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error de SQL al ejecutar el script " + scriptPath + ".");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error general al ejecutar el script " + scriptPath + ".");
        }
    }
}