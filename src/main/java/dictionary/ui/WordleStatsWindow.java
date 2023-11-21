package dictionary.ui;

import dictionary.Main;
import dictionary.WordleMainWindow;
import dictionary.backend.WordleFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleStatsWindow {
    private Stage stage;
    private WordleStatsController wordleStatsController;
    private WordleFunction wordleFunction;
    public void display() throws IOException {
        System.out.println(wordleFunction.getLongestStreak());
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/wordleStatsfxml.fxml"));
        Parent root = fxmlLoader.load();
        wordleStatsController = fxmlLoader.getController();
        wordleStatsController.setWordleStatsWindow(this);
        wordleStatsController.init();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(900);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void quit() {
        stage.close();
    }

    public WordleFunction getWordleFunction() {
        return wordleFunction;
    }

    public void setWordleFunction(WordleFunction wordleFunction) {
        this.wordleFunction = wordleFunction;
    }
}
