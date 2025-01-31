module appli.lprsjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens appli.lprsjavafx to javafx.fxml;
    exports appli.lprsjavafx;

    exports Controller;
    opens Controller to javafx.fxml;
}