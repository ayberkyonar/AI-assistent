module com.example.aiassistent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.aiassistent to javafx.fxml;
    exports com.example.aiassistent;
    exports com.example.aiassistent.utils;
    opens com.example.aiassistent.utils to javafx.fxml;
}