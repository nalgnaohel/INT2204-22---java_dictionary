package dictionary.ui.game.finding;

import dictionary.Main;
import dictionary.backend.FindingFunction;
import dictionary.ui.game.GameStatsWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FindingStatsWindow extends GameStatsWindow {
    private FindingController findingController;

    public FindingController getFindingController() {
        return findingController;
    }

    public void setFindingController(FindingController findingController) {
        this.findingController = findingController;
    }

}
