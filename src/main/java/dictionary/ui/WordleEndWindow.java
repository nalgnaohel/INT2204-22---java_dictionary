package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleEndWindow extends GameEndWindow {
    private WordleController wordleController;

    public WordleController getWordleController() {
        return wordleController;
    }

    public void setWordleController(WordleController wordleController) {
        this.wordleController = wordleController;
    }
}
