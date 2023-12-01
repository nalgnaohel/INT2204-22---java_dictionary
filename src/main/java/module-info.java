module com.example.java_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jlayer;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    exports dictionary;
    opens dictionary to javafx.fxml;
    exports dictionary.ui;
    opens dictionary.ui to javafx.fxml;
    exports dictionary.backend;
    opens dictionary.backend to javafx.fxml;
    exports dictionary.ui.game.finding;
    opens dictionary.ui.game.finding to javafx.fxml;
    exports dictionary.ui.game.wordles;
    opens dictionary.ui.game.wordles to javafx.fxml;
    exports dictionary.ui.game;
    opens dictionary.ui.game to javafx.fxml;
}