package dictionary.ui;

import dictionary.Main;
import dictionary.backend.History;
import dictionary.backend.Trie;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.util.ArrayList;

import static dictionary.Main.dict;

public class LookUpTabController {
    //private final TxtDictionary dict = new TxtDictionary();
    private String currentWord;
    private String currentMeaning;
    private final ArrayList<String> wordsList = new ArrayList<>();

    @FXML
    private TextField SearchBar;
    @FXML
    private ListView<String> listview = new ListView<String>();
    @FXML
    private TextFlow wordTitle;
    @FXML
    private TextFlow wordMeaning;

    @FXML
    private Button pronounceButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    public void initialize() {
        ShowList("");
    }

//    @FXML
//    public void search() {
//        String tmp = searchbox.getText();
//        ShowList(tmp);
//    }

    @FXML
    public void SearchInput(KeyEvent event) throws IOException {
        if (event.getSource() == SearchBar) {
            String searchText = SearchBar.getText();
            ShowList(searchText);
            if (event.getCode() == KeyCode.ENTER) {
                ShowWord(searchText);
            }
        }
    }

    @FXML
    public void SelectItem(MouseEvent event) throws IOException {
        String searchText = listview.getSelectionModel().getSelectedItem();
        ShowWord(searchText);
    }

    public void ShowList(String target) {
        if (target.isEmpty()) {
            wordsList.clear();
            for (int i = dict.getHistory().getWordTargetHistory().size() - 1; i >= 0; i--) {
                wordsList.add(dict.getHistory().getWordTargetHistory().get(i));
            }
            ObservableList<String> items = FXCollections.observableArrayList(wordsList);
            //show listview
            listview.setItems(items);
        } else {
            wordsList.clear();
            wordsList.addAll(Trie.search(target));
            ObservableList<String> items = FXCollections.observableArrayList(wordsList);
            //show listview
            listview.setItems(items);
        }
    }

    public void ShowWord(String newValue) {
        currentWord = newValue;

        // Hiện word title
        Text word = new Text(currentWord);
        wordTitle.getChildren().clear();
        wordTitle.getChildren().add(word);

        //Hiện word meaning và info (antonyms, synonyms)
        currentMeaning = dict.lookUpWord(currentWord);
        Text meaning = new Text(currentMeaning);
        wordMeaning.getChildren().clear();
        wordMeaning.getChildren().add(meaning);
        try {
            Text info = new Text(dict.getInfoFromAPI(currentWord));
            System.out.println(dict.getInfoFromAPI(currentWord));
            wordMeaning.getChildren().add(info);
        } catch (Exception e) {
            System.out.println("No synonyms or antonyms");
        }
        showSaveButton(currentWord);
    }

    @FXML
    public void pronounce(ActionEvent event) throws IOException {
        dict.playEngWordSound(currentWord);
    }

    public void setFavorite(ActionEvent event) {
        changeSaveButton(currentWord);
    }

    public void changeSaveButton(String currentWord) {
        System.out.print(currentWord + " ");
        if (dict.getFavorites().isFavorited(currentWord)) {
            System.out.println("YES!");
            dict.getFavorites().remove(currentWord);
            Image image = new Image(Main.class.getResourceAsStream("icon/Bookmark.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(18);
            imageView.setFitHeight(18);
            saveButton.setGraphic(imageView);
        } else {
            System.out.println("NO!");
            Word word = new Word(currentWord, currentMeaning);
            dict.getFavorites().addTo(word);
            Image image = new Image(Main.class.getResourceAsStream("icon/faved_bookmark.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(18);
            imageView.setFitHeight(18);
            saveButton.setGraphic(imageView);
        }
    }

    public void showSaveButton(String currentWord) {
        if (dict.getFavorites().isFavorited(currentWord)) {
            Image image = new Image(Main.class.getResourceAsStream("icon/faved_bookmark.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(18);
            imageView.setFitHeight(18);
            saveButton.setGraphic(imageView);
        } else {
            Image image = new Image(Main.class.getResourceAsStream("icon/Bookmark.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(18);
            imageView.setFitHeight(18);
            saveButton.setGraphic(imageView);
        }
    }
}
