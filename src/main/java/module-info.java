module com.example.lprsjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;

    opens Model to javafx.base;
    exports Model;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens appli to javafx.fxml;
    exports appli;
    exports controller;
    opens controller to javafx.fxml;

}