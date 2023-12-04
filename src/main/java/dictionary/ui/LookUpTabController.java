package dictionary.ui;

import dictionary.Main;
import dictionary.backend.Trie;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class LookUpTabController extends MutualController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareSaveButton();
        ShowList("");
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

    //@Override
    public void update() {
        //super.update();
        listView.getItems().clear();
        changeSaveButton(null);
        saved.setVisible(false);
        currentWord = "";
        SearchBar.setText(currentWord);
        ShowList("");
        ShowWord("");
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
