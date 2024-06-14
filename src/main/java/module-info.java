module com.example.aiassistent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.json;

    opens com.example.aiassistent to javafx.fxml;
    exports com.example.aiassistent;
    exports com.example.aiassistent.utils;
    exports com.example.aiassistent.model;
    opens com.example.aiassistent.utils to javafx.fxml;
}