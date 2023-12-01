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

public class LookUpTabController implements Initializable {
    private String currentWord = "";
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        if (currentWord == null || currentWord.isEmpty()) {
            return;
        }

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
        dict.playTextSound(currentWord, "en");
    }

    public void setFavorite(ActionEvent event) {
        changeSaveButton(currentWord);
    }

    public void changeSaveButton(String currentWord) {
        if (currentWord ==  null || currentWord.isEmpty()) {
            return;
        }
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

    public void delete(ActionEvent event) {
        if (currentWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thong bao");
            alert.setHeaderText("Chua chon tu de xoa!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xac nhan xoa tu");
            alert.setHeaderText("Ban co chac chan muon xoa tu " + currentWord +  "?");
            ButtonType cf = new ButtonType("Co");
            ButtonType canc = new ButtonType("Khong");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(cf, canc);
            Optional<ButtonType> opt = alert.showAndWait();

            if (opt.get() == cf) {
                if (dict.deleteWord(currentWord)) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xoa tu thanh cong");
                    successAlert.setHeaderText("Xoa tu " + currentWord + " thanh cong!");
                    successAlert.showAndWait();
                } else {
                    Alert unsuccessAlert = new Alert(Alert.AlertType.ERROR);
                    unsuccessAlert.setTitle("Xoa tu khong thanh cong");
                    unsuccessAlert.setHeaderText("Xoa tu " + currentWord + " khong thanh cong!");
                    unsuccessAlert.showAndWait();
                }
                ShowList(currentWord);
                currentWord = "";
                wordTitle.getChildren().clear();
                wordMeaning.getChildren().clear();
            }
        }
    }

    //edit + insert.
    public void edit(ActionEvent event) {
        EditWindow editWindow = new EditWindow();
        try {
            editWindow.display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
