module com.javeriana.sistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.javeriana.sistema.ui to javafx.fxml;
    exports com.javeriana.sistema.ui;

    opens com.javeriana.sistema.controller to javafx.fxml;
    exports com.javeriana.sistema.controller;

    opens com.javeriana.sistema.dao to java.sql;
    exports com.javeriana.sistema.dao;
}
