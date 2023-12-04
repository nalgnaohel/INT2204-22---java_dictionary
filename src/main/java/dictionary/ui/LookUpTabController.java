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

    @Override
    public void ShowList(String target) {
        if (target.isEmpty()) {
            wordsList.clear();
            for (int i = dict.getHistory().getWordTargetHistory().size() - 1; i >= 0; i--) {
                wordsList.add(dict.getHistory().getWordTargetHistory().get(i));
            }
            ObservableList<String> items = FXCollections.observableArrayList(wordsList);
            //show listview
            listView.setItems(items);

            // set history & favorite icon
            listView.setCellFactory(param -> new ListCell<String>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // check if the word is in history list
                        if (dict.getHistory().getWordTargetHistory().contains(name)) {
                            imageView.setImage(new Image(Main.class.getResourceAsStream("icon/history.png")));
                            imageView.setFitWidth(18);
                            imageView.setFitHeight(18);
                            setGraphic(imageView);
                        } else {
                            setGraphic(null);
                        }
                        setText(name);
                    }
                }
            });
        } else {
            wordsList.clear();
            wordsList.addAll(Trie.search(target));
            ObservableList<String> items = FXCollections.observableArrayList(wordsList);
            //show listview
            listView.setItems(items);
        }
    }

    @Override
    public void update() {
        super.update();
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
