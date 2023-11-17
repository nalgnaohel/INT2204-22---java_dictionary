package dictionary;

import dictionary.ui.WordleController;
import dictionary.ui.WordleEndController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleMainWindow {

    public void display() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(WordleMainWindow.class.getResource("fxml/wordleMain.fxml"));
        AnchorPane root = fxmlLoader.load();
        WordleController wordleController = fxmlLoader.getController();
        wordleController.init();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
