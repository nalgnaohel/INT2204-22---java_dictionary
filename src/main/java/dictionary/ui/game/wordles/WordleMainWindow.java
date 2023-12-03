package dictionary.ui.game.wordles;

import dictionary.ui.game.GameMainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleMainWindow extends GameMainWindow {
    public WordleMainWindow() {
        this.fxmlPath = "fxml/wordleMain.fxml";
    }

}
