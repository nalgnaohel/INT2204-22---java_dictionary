package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FindingEndGame extends GameEndWindow {
    private FindingController findingController;

    public FindingController getFindingController() {
        return findingController;
    }

    public void setFindingController(FindingController fr) {
        findingController = fr;
    }
}
