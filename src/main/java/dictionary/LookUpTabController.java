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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LookUpTabController {
    TxtDictionary dict = new TxtDictionary();
    private String currentWord;
    ArrayList<String> wordsList = new ArrayList<>();

    @FXML
    private TextField searchbox;
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
        dict.importDataFromFile("src/main/resources/data/dictionaries.txt");

        //add 50 words randomly (demo)
//        String[] words = {
//                "apple", "banana", "carrot", "dog", "elephant",
//                "flower", "giraffe", "house", "ice cream", "jungle",
//                "kangaroo", "lemon", "mountain", "noodle", "octopus",
//                "penguin", "quilt", "rainbow", "sunset", "tiger",
//                "umbrella", "volcano", "waterfall", "xylophone", "zebra",
//                "basketball", "computer", "dolphin", "guitar", "helicopter",
//                "island", "koala", "lighthouse", "moon", "narwhal",
//                "ocean", "parrot", "rocket", "sunflower", "turtle",
//                "vampire", "whale", "xylophone", "yacht", "zeppelin",
//                "astronaut", "butterfly", "dinosaur", "fireworks", "globe", "open"
//        };
//        wordsList.addAll(List.of(words));

        ShowList("ani");
        ShowWord();
    }
    @FXML
    public void search() {
        String tmp = searchbox.getText();
        ShowList(tmp);
    }

    public void ShowList(String target) {
        wordsList.clear();
        wordsList.addAll(Trie.search(target));
        ObservableList<String> items = FXCollections.observableArrayList(wordsList);
        //show listview
        listview.setItems(items);
    }

    public void ShowWord() {
        //show word
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Đặt currentWord là từ được chọn
            currentWord = newValue;

            // Hiện word title
            Text word = new Text(currentWord);
            wordTitle.getChildren().clear();
            wordTitle.getChildren().add(word);

            //Hiện word meaning
            Text meaning = new Text(dict.lookUpWord(currentWord));
//            Text meaning = new Text("the round fruit of a tree of the rose family, which typically has thin red or green skin and crisp flesh. Many varieties have been developed" +
//                    "as dessert or cooking fruit or for making cider.\n" +
//                    "the tree which bears apples.\n");
            wordMeaning.getChildren().clear();
            wordMeaning.getChildren().add(meaning);

            //Hiện từ đồng nghĩa và trái nghĩa
            Text info = new Text(dict.getInfoFromAPI(currentWord));
            wordMeaning.getChildren().add(info);
        });
    }

    @FXML
    public void pronounce(ActionEvent event) throws IOException {
        dict.playEngWordSound(currentWord);
    }
}
