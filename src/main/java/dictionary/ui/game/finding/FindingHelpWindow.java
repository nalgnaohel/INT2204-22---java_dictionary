package dictionary.ui.game.finding;

import dictionary.Main;
import dictionary.ui.game.GameHelpWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class FindingHelpWindow extends GameHelpWindow {
    private FindingController findingController;

    public FindingHelpWindow() {
        this.instruct = "You have to take our ninja to the treasure by\n" +
                "choosing a suitable route\n";
        instruct += "Use arrow keys to move.\n";
        instruct += "When reaching a (?) sign, you have to answer the\n" +
                "question behind it.\n";
        instruct += "A correct answer allows you to continue your route.\n" +
                "A wrong answer will turn the square turns into rock;\n" +
                "you cannot move forward and have to find another route.\n";
        instruct += "You win if you reach the treasure.\n" +
                "You will lose if the time is up.\n";
    }

    public void setFindingController(FindingController fc) {
        findingController = fc;
    }

    public FindingController getFindingController() {
        return findingController;
    }
}
