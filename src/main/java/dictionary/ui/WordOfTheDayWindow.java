package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WordOfTheDayWindow {
    private Stage stage;
    private WordOfTheDayController wordOfTheDayController;
    public void display() throws IOException {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/wordoftheday.fxml"));
        Parent root = fxmlLoader.load();
        wordOfTheDayController = fxmlLoader.getController();
        wordOfTheDayController.setWordOfTheDayWindow(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void quit() {
        stage.close();
    }
}
