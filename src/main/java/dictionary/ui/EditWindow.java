package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditWindow {
    private Stage stage;
    private EditController editController;
    public void display() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/edit.fxml"));
        AnchorPane root = fxmlLoader.load();
        editController = fxmlLoader.getController();
        editController.setEditWindow(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quit() {
        stage.close();
    }
}
