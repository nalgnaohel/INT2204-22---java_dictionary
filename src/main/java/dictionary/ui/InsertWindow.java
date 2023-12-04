package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InsertWindow {
    private Stage stage;

    private InsertController insertController;
    private MutualController controller;
    public void display() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/insert.fxml"));
        AnchorPane root = fxmlLoader.load();
        insertController = fxmlLoader.getController();
        //insertController.initialize();
        insertController.setInsertWindow(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public InsertController getInsertController() {
        return this.insertController;
    }

    public MutualController getController() {
        return controller;
    }

    public void setController(MutualController controller) {
        this.controller = controller;
    }

    public void quit() {
        stage.close();
    }
}
