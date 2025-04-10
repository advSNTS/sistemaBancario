module com.javeriana.sistema {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.javeriana.sistema to javafx.fxml;
    exports com.javeriana.sistema;
}