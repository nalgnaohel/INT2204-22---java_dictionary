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
    private Stage gameStage;
    private WordleController wordleController;
    public void display() throws IOException {
        gameStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(WordleMainWindow.class.getResource("fxml/wordleMain.fxml"));
        AnchorPane root = fxmlLoader.load();
        wordleController = fxmlLoader.getController();
        wordleController.setWordleMainWindow(this);
        wordleController.init();

        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.setHeight(700);
        gameStage.setWidth(900);
        gameStage.setResizable(false);
        gameStage.show();
    }

    public Stage getGameStage() {
        return this.gameStage;
    }

    public void quit() {
        gameStage.close();
    }


}
