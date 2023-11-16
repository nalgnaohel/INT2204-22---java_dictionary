package dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.w3c.dom.events.MouseEvent;

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

    public void switchToWordle(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/wordleMain.fxml"));
        AnchorPane wordlePane = fxmlLoader.load();
        gameArea.getChildren().clear();
        gameArea.getChildren().add(wordlePane);
    }
}
