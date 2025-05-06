package com.javeriana.sistema;

import com.javeriana.sistema.util.DBConnection;
import com.javeriana.sistema.util.EjecutorPagosProgramados;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Ejecutar script SQL una única vez al iniciar la aplicación
        DBConnection.ejecutarScript();

        // Iniciar ejecución periódica de pagos programados
        new EjecutorPagosProgramados().iniciar();

        // Cargar vista principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/WelcomeView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Sistema Bancario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
