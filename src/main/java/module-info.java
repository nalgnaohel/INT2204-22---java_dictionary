module com.example.java_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jlayer;
    requires java.sql;

    exports dictionary;
    opens dictionary to javafx.fxml;
}