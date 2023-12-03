package dictionary.ui;

import dictionary.Main;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class FavoriteTabController extends MutualController {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Word word : dict.getFavorites().getAllWords()) {
            wordsList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(wordsList);
        listView.setItems(favItems);
    }

    public void update() {
        wordsList.clear();
        for (Word word : dict.getFavorites().getAllWords()) {
            wordsList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(wordsList);
        listView.setItems(favItems);
        wordTitle.getChildren().clear();
        wordMeaning.getChildren().clear();
        Image image = new Image(Main.class.getResourceAsStream("icon/Bookmark.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        saveButton.setGraphic(imageView);
    }

}
