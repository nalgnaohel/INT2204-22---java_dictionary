package dictionary.ui;

import dictionary.Main;
import dictionary.WordleMainWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChooseGameController {
    public void initialize() {

    }

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private AnchorPane gameArea;

    private WordleController wordleController;
    private boolean first = true;

    public void switchToWordle(ActionEvent event) throws IOException {
        WordleMainWindow wordleMainWindow = new WordleMainWindow();
        wordleMainWindow.display();
    }


}