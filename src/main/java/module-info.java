module com.javeriana.sistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.javeriana.sistema.controller to javafx.fxml;
    opens com.javeriana.sistema.dao to javafx.fxml;
    opens com.javeriana.sistema.model to javafx.fxml;
    opens com.javeriana.sistema.services to javafx.fxml;

    exports com.javeriana.sistema;
    exports com.javeriana.sistema.controller;
    exports com.javeriana.sistema.model;
}
