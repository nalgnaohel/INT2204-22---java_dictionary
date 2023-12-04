package dictionary.ui;

import dictionary.Main;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class FavoriteTabController extends MutualController {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadListView();
        prepareSaveButton();
    }

    @Override
    public void update() {
        super.update();

        wordsList.clear();
        listView.getItems().clear();
        ThesaurusInfo.getChildren().clear();
        wordTitle.getChildren().clear();
        wordMeaning.getChildren().clear();

        DefinitionLabel.setVisible(false);
        ThesaurusLabel.setVisible(false);
        saved.setVisible(false);
        loading.setVisible(false);

        loadListView();
        saveButton.setGraphic(bookmarkView);
    }

    public void loadListView() {
        for (Word word : dict.getFavorites().getAllWords()) {
            wordsList.add(word.getWordTarget());
        }
        ObservableList<String> favItems = FXCollections.observableArrayList(wordsList);
        favItems.sort(String::compareToIgnoreCase);
        listView.setItems(favItems);
    }

    @FXML
    public void Remove(ActionEvent event) throws IOException {
        if (currentWord ==  null || currentWord.isEmpty()) {
            return;
        }
        showRemoveAlert(currentWord);
    }

}
