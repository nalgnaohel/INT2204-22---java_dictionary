package dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import static dictionary.Main.dict;

public class TranslateTabController {
    @FXML
    private TextArea fromLangText;
    @FXML
    private TextFlow toLangText;

    ObservableList<String> langFromList = FXCollections.observableArrayList("English", "Vietnamese");

    @FXML
    private ChoiceBox langFrom;
    @FXML
    private ChoiceBox langTo;

    public void initialize() {
        langFrom.setItems(langFromList);
        langTo.setItems(langFromList);
        langFrom.setValue("English");
        langTo.setValue("Vietnamese");
    }

    public void switchLanguage(ActionEvent event) {
        if (langFrom.getValue() == "English") {
            langFrom.setValue("Vietnamese");
            langTo.setValue("English");
        } else {
            langFrom.setValue("English");
            langTo.setValue("Vietnamese");
        }
    }

    public void switchFromLang(ActionEvent event) {
        if (langTo.getValue() == "Vietnamese") {
            langFrom.setValue("English");
        } else {
            langFrom.setValue("Vietnamese");
        }
    }

    public void switchToLang(ActionEvent event) {
        if (langFrom.getValue() == "Vietnamese") {
            langTo.setValue("English");
        } else {
            langTo.setValue("Vietnamese");
        }
    }

    public void translate(ActionEvent event) {
        String apiFrom = "en";
        String apiTo = "vi";
        if (langFrom.getValue() == "Vietnamese" && langTo.getValue() == "English") {
            apiFrom = "vi";
            apiTo = "en";
        }
        String target = fromLangText.getText();
        String meaningS = dict.translateSentence(apiFrom, apiTo, target);
        Text meaning = new Text(meaningS);
        toLangText.getChildren().clear();
        toLangText.getChildren().add(meaning);
    }
}
