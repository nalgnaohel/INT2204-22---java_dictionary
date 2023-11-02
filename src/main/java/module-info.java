module com.example.java_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ui to javafx.fxml;
    exports ui;
    exports ui.controller;
    opens ui.controller to javafx.fxml;
}