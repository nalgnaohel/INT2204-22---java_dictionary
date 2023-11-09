package dictionary;

import dictionary.backend.Trie;
import dictionary.backend.TxtDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.util.ArrayList;

public class LookUpTabController {
    private final TxtDictionary dict = new TxtDictionary();
    private String currentWord;
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
        ShowList("t");
    }

    public void dictionaryImport() {
//        dict.importDataFromFile("src/main/resources/data/dictionaries.txt");
        dict.importDataFromFile("src/main/resources/data/demo.txt");
    }

//    @FXML
//    public void search() {
//        String tmp = searchbox.getText();
//        ShowList(tmp);
//    }

    @FXML //throws SQLException?
    public void SearchInput(KeyEvent event) throws IOException {
        if (event.getSource() == SearchBar) {
            String searchText = SearchBar.getText();
            if (!searchText.isEmpty()) {
                ShowList(searchText);
            }
        }
    }

    @FXML
    public void SelectItem(MouseEvent event) throws IOException {
        String searchText = listview.getSelectionModel().getSelectedItem();
        ShowWord(searchText);
    }

    public void ShowList(String target) {
        wordsList.clear();
        wordsList.addAll(Trie.search(target));
        ObservableList<String> items = FXCollections.observableArrayList(wordsList);
        //show listview
        listview.setItems(items);
    }

    public void ShowWord(String newValue) {
        currentWord = newValue;

        // Hiện word title
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

    @FXML
    public void pronounce(ActionEvent event) throws IOException {
        dict.playEngWordSound(currentWord);
    }
}
