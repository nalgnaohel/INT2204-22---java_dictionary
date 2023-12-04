package dictionary.ui.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameEndController {
    @FXML
    private VBox background;
    @FXML
    private Button restartButton;
    @FXML
    private Button quitButton;

    public void setColor(String color) {
        background.getStyleClass().clear();
        background.getStyleClass().add(color);
        restartButton.setTextFill(Color.web(color));
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }
}


