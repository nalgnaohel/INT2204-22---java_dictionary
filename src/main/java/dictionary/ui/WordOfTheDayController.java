package dictionary.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class WordOfTheDayController implements Initializable {
    @FXML
    private TextFlow wordMeaning;
    @FXML
    private Button close;
    @FXML
    private Label date;
    @FXML
    private Label wordTarget;

    private WordOfTheDayWindow wordOfTheDayWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(1);
        int idWord = (localDate.getDayOfYear() + 2010 + (localDate.getDayOfMonth() * localDate.getMonth().getValue())) % 1000;
        date.setText(localDate.toString());
        wordTarget.setText(dict.getAllWords().get(idWord).getWordTarget());
        wordMeaning.getChildren().add(new Text(dict.getAllWords().get(idWord).getWordMeaning()));
    }

    public void setWordOfTheDayWindow(WordOfTheDayWindow wordOfTheDayWindow) {
        this.wordOfTheDayWindow = wordOfTheDayWindow;
    }

    public void quit() {
        wordOfTheDayWindow.quit();
    }
}
