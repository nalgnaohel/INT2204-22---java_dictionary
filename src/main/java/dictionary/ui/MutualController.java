package dictionary.ui;

import dictionary.Main;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public abstract class MutualController implements Initializable {
    private String currentMeaning;
    protected boolean editSuccess = false;

    @FXML
    protected TextField SearchBar;
    @FXML
    protected ListView<String> listView = new ListView<String>();
    protected ArrayList<String> wordsList = new ArrayList<>();
    protected String currentWord = "";
    @FXML
    protected TextFlow wordTitle;
    @FXML
    protected TextFlow wordMeaning;

    @FXML
    protected Button pronounceButton;
    @FXML
    protected Button saveButton;
    @FXML
    private Button removeButton;
    @FXML
    protected Button editButton;

    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    @FXML
    public void SelectItem(MouseEvent event) throws IOException {
        String searchText = listView.getSelectionModel().getSelectedItem();
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
            listView.setItems(items);
        } else {
            wordsList.clear();
            wordsList.addAll(Trie.search(target));
            ObservableList<String> items = FXCollections.observableArrayList(wordsList);
            //show listview
            listView.setItems(items);
        }
    }

    public void ShowWord(String newValue) {
        currentWord = newValue;
        if (currentWord == null) {
            return;
        }
        if (currentWord.isEmpty()) {
            wordTitle.getChildren().clear();
            wordTitle.getChildren().add(new Text(""));
            wordMeaning.getChildren().clear();
            wordMeaning.getChildren().add(new Text(""));
            Image image = new Image(Main.class.getResourceAsStream("icon/Bookmark.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(18);
            imageView.setFitHeight(18);
            saveButton.setGraphic(imageView);
            return;
        }

        if (this instanceof FavoriteTabController) {
            currentMeaning = dict.getFavorites().getAllFav().get(currentWord);
        } else {
            currentMeaning = dict.lookUpWord(currentWord);
        }
        if (currentMeaning.equals("Not found!\n")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thong bao");
            alert.setHeaderText("Khong tim thay tu " + currentWord);
            alert.showAndWait();
            return;
        }
        if (this instanceof LookUpTabController) {
            dict.getHistory().addTo(new Word(currentWord, currentMeaning));
        }
        Text word = new Text(currentWord);
        wordTitle.getChildren().clear();
        wordTitle.getChildren().add(word);
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
            if (this instanceof FavoriteTabController) {
                ((FavoriteTabController) this).update();
                ShowWord("");
            }
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

    public void edit(ActionEvent event) {
        EditWindow editWindow = new EditWindow();
        editWindow.setController(this);
        try {
            editWindow.display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done editing!");
        if (editSuccess && this instanceof FavoriteTabController) {
            currentMeaning = dict.lookUpWord(currentWord);
            dict.getFavorites().getAllFav().put(currentWord, currentMeaning);
            System.out.println(currentMeaning);
            wordMeaning.getChildren().clear();
            wordMeaning.getChildren().add(new Text(currentMeaning));
        }

    }

    public String getCurrentWord() {
        return currentWord;
    }
}
