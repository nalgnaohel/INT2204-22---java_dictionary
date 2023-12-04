package dictionary.ui;

import dictionary.Main;
import dictionary.backend.History;
import dictionary.backend.Trie;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class LookUpTabController extends MutualController {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowList("");
    }

    public void init() {
        ShowWord("");
        ShowList("");
        currentWord = "";
        SearchBar.setText(currentWord);
    }

    public void SearchInput(KeyEvent event) throws IOException {
        if (event.getSource() == SearchBar) {
            String searchText = SearchBar.getText();
            ShowList(searchText);
            if (event.getCode() == KeyCode.ENTER) {
                ShowWord(searchText);
            }
        }
    }

    public void updateHistory() {
        wordsList.clear();
        for (Word word : dict.getFavorites().getAllWords()) {
            wordsList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(wordsList);
        listView.setItems(favItems);
    }
}
