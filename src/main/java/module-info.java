module com.example.java_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.java_dictionary to javafx.fxml;
    exports com.example.java_dictionary;
}