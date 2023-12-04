package dictionary.ui;

import dictionary.ui.game.finding.FindingMainWindow;
import dictionary.ui.game.wordles.WordleMainWindow;
import dictionary.backend.TxtDictionary;
import dictionary.ui.game.wordles.WordleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static dictionary.Main.dict;

public class ChooseGameController {

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private AnchorPane gameArea;

    private WordleController wordleController;

    public void switchToWordle(ActionEvent event) throws IOException {
        WordleMainWindow wordleMainWindow = new WordleMainWindow();
        wordleMainWindow.display();
    }

    public void switchToFinding(ActionEvent event) throws IOException {
        if (dict instanceof TxtDictionary) {
            Alert failedDtbAlert = new Alert(Alert.AlertType.ERROR);
            failedDtbAlert.setTitle("Thong bao");
            failedDtbAlert.setHeaderText("Khong ket noi duoc voi CSDL.\n Hay ket noi voi CSDL de choi!");
            failedDtbAlert.show();
            return;
        }
        FindingMainWindow findingMainWindow = new FindingMainWindow();
        findingMainWindow.display();
    }

}
