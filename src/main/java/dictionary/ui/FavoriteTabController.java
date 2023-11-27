package dictionary.ui;

import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class FavoriteTabController implements Initializable {
    private String currentWord;
    private final ArrayList<String> favList = new ArrayList<>();
    @FXML
    private ListView<String> listview = new ListView<>();
    @FXML
    private TextFlow wordTitle;
    @FXML
    private TextFlow wordMeaning;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Word word : dict.getFavorites().getAllWords()) {
            favList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(favList);
        listview.setItems(favItems);
    }

    public void update() {
        favList.clear();
        for (Word word : dict.getFavorites().getAllWords()) {
            favList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(favList);
        listview.setItems(favItems);
    }

    public void SelectItem(MouseEvent event) throws IOException {
        String selected = listview.getSelectionModel().getSelectedItem();
        showWord(selected);
    }

    public void showWord(String target) throws IOException {
        currentWord = target;

        Text word = new Text(currentWord);
        wordTitle.getChildren().clear();
        wordTitle.getChildren().add(word);

        //Hiện word meaning và info (antonyms, synonyms)
        Text meaning = new Text(dict.lookUpWord(currentWord));
        wordMeaning.getChildren().clear();
        wordMeaning.getChildren().add(meaning);
        try {
            Text info = new Text(dict.getInfoFromAPI(currentWord));
            System.out.println(dict.getInfoFromAPI(currentWord));
            wordMeaning.getChildren().add(info);
        } catch (Exception e) {
            System.out.println("No synonyms or antonyms");
        }
    }

}
