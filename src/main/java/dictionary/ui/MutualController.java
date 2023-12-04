package dictionary.ui;

import dictionary.Main;
import dictionary.backend.Trie;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

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
    @FXML
    protected Label loading;
    @FXML
    protected Label saved;
    @FXML
    protected Label DefinitionLabel;
    @FXML
    protected Label ThesaurusLabel;
    @FXML
    protected TextFlow ThesaurusInfo;

    protected ImageView bookmarkView;
    protected ImageView favedBookmarkView;
    protected Thread loadingThesaurus;

    public abstract void initialize(URL url, ResourceBundle resourceBundle);
    public void update() {
        stopLoadingThesaurus();
    };

    @FXML
    public void SelectItem(MouseEvent event) throws IOException {
        stopLoadingThesaurus();
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
            ThesaurusInfo.getChildren().clear();
            ThesaurusInfo.getChildren().add(new Text(""));
            saveButton.setGraphic(bookmarkView);
            DefinitionLabel.setVisible(false);
            ThesaurusLabel.setVisible(false);
            return;
        }

        //Hiện word meaning
        currentMeaning = dict.lookUpWord(currentWord);
//        if (this instanceof FavoriteTabController) {
//            currentMeaning = dict.getFavorites().getAllFav().get(currentWord);
//        } else {
//            currentMeaning = dict.lookUpWord(currentWord);
//        }
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
        DefinitionLabel.setVisible(true);

        // Hiện word thesaurus
        ThesaurusInfo.getChildren().clear();
        Task<String> getThesaurus = new Task<String>() {
            @Override
            public String call() {
                String info = "";
                try {
                    info = dict.getInfoFromAPI(currentWord);
                    if (info.equals("antonyms: \nsynonyms: \n") || info.isEmpty()) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("No synonyms or antonyms");
                    info = "Không tìm thấy kết quả";
                }
                return info;
            }
        };

        // loading animation
        getThesaurus.setOnRunning(e -> {
            ThesaurusLabel.setVisible(false);
            loading.setVisible(true);
        });
        getThesaurus.setOnSucceeded(e -> {
            ThesaurusLabel.setVisible(true);
            loading.setVisible(false);
            String value = getThesaurus.getValue();
            System.out.println(value);
            Text info = new Text(value);
            ThesaurusInfo.getChildren().add(info);
        });
        loadingThesaurus = new Thread(getThesaurus);
        loadingThesaurus.start();

        showSaveButton(currentWord);
        showSevedLabel(currentWord);
    }

    private void stopLoadingThesaurus() {
        if (loadingThesaurus != null) {
            loadingThesaurus.stop();
            ThesaurusLabel.setVisible(false);
            ThesaurusInfo.getChildren().clear();
            System.out.println("Loading thesaurus stopped");
        }
    }

    // note: code deleted here tmp.txt

    @FXML
    public void pronounce(ActionEvent event) throws IOException {
        if (currentWord == null || currentWord.isEmpty()) {
            System.out.println("No word to pronounce");
            return;
        }
        dict.playTextSound(currentWord, "en");
    }

    public void setFavorite(ActionEvent event) {
        changeSaveButton(currentWord);
        changeSavedLabel(currentWord);
    }

    public void prepareSaveButton() {
        Image bookmark = new Image(Main.class.getResourceAsStream("icon/Bookmark.png"));
        bookmarkView = new ImageView(bookmark);
        bookmarkView.setFitWidth(18);
        bookmarkView.setFitHeight(18);

        Image fav = new Image(Main.class.getResourceAsStream("icon/faved_bookmark.png"));
        favedBookmarkView = new ImageView(fav);
        favedBookmarkView.setFitWidth(18);
        favedBookmarkView.setFitHeight(18);
    }

    public void changeSaveButton(String currentWord) {
//        prepareSaveButton();
        if (currentWord ==  null || currentWord.isEmpty()) {
            System.out.println("No word to save");
            saveButton.setGraphic(bookmarkView);
            return;
        }
        System.out.print(currentWord + " ");
        if (dict.getFavorites().isFavorited(currentWord)) {
            System.out.println("YES!");
            showRemoveAlert(currentWord);
        } else {
            System.out.println("NO!");
            Word word = new Word(currentWord, currentMeaning);
            dict.getFavorites().addTo(word);
            saveButton.setGraphic(favedBookmarkView);
        }
    }

    public void showRemoveAlert(String currentWord) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa từ " + currentWord + " khỏi danh sách yêu thích không?");

        // set icon for the alert dialog
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon/warning.png")));

        ButtonType Yes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType Cancel = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(Yes, Cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == Yes) {
            dict.getFavorites().remove(currentWord);
            if (this instanceof FavoriteTabController) {
                currentWord = "";
                wordTitle.getChildren().clear();
                wordMeaning.getChildren().clear();
                ThesaurusInfo.getChildren().clear();
                ThesaurusLabel.setVisible(false);
                DefinitionLabel.setVisible(false);
                loading.setVisible(false);
                saved.setVisible(false);
                update();
            } else {
                saveButton.setGraphic(bookmarkView);
            }
            System.out.println("Successfully removed from favorite");
        } else {
            System.out.println("CANCEL");
        }
    }

    public void showSaveButton(String currentWord) {
//        prepareSaveButton();
        if (currentWord == null || currentWord.isEmpty()) {
            System.out.println("Can't show save button because no word is selected");
            saveButton.setGraphic(bookmarkView);
            return;
        }
        if (dict.getFavorites().isFavorited(currentWord)) {
            saveButton.setGraphic(favedBookmarkView);
        } else {
            saveButton.setGraphic(bookmarkView);
        }
    }

    public void showSevedLabel(String currentWord) {
        if (currentWord == null || currentWord.isEmpty()) {
            System.out.println("Can't show saved label because no word is selected");
            saved.setVisible(false);
            return;
        }
        if (dict.getFavorites().isFavorited(currentWord)) {
            saved.setVisible(true);
        } else {
            saved.setVisible(false);
        }
    }

    public void changeSavedLabel(String currentWord) {
        if (currentWord == null || currentWord.isEmpty()) {
            System.out.println("Can't change saved label because no word is selected");
            saved.setVisible(false);
            return;
        }
        if (dict.getFavorites().isFavorited(currentWord)) {
            saved.setVisible(true);
        } else {
            saved.setVisible(false);
        }
    }

    public void delete(ActionEvent event) {
        if (currentWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Bạn chưa chọn từ để xóa!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa từ");
            alert.setHeaderText("Bạn có chắc chắn muốn xóa từ " + currentWord +  "?");
            ButtonType cf = new ButtonType("Có");
            ButtonType canc = new ButtonType("Không");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(cf, canc);
            Optional<ButtonType> opt = alert.showAndWait();

            if (opt.get() == cf) {
                if (dict.deleteWord(currentWord)) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xóa từ thành công");
                    successAlert.setHeaderText("Xóa từ " + currentWord + " thành công!");
                    successAlert.showAndWait();
                } else {
                    Alert unsuccessAlert = new Alert(Alert.AlertType.ERROR);
                    unsuccessAlert.setTitle("Xóa từ không thành công");
                    unsuccessAlert.setHeaderText("Xóa từ " + currentWord + " không thành công!");
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
