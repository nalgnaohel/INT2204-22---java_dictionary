package dictionary.ui;

import dictionary.Main;
import dictionary.backend.Trie;
import dictionary.backend.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    public void mouseOnSearch(MouseEvent event) throws IOException {
        if (event.getSource() == SearchBar) {
            String searchText = SearchBar.getText();
            ShowList(searchText);
        }
    }

    //@Override
    public void update() {
        //super.update();
        listView.getItems().clear();
        changeSaveButton(null);
        saved.setVisible(false);
        currentWord = "";
        SearchBar.setText(currentWord);
        ShowList("");
        ShowWord("");
    }

    public void edit(ActionEvent event) {
        EditWindow editWindow = new EditWindow();
        editWindow.setController(this);
        try {
            editWindow.display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(ActionEvent event) {
        InsertWindow insertWindow = new InsertWindow();
        insertWindow.setController(this);
        try {
            insertWindow.display();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                SearchBar.setText("");
                ShowList(currentWord);
                currentWord = "";
                wordTitle.getChildren().clear();
                wordMeaning.getChildren().clear();
                ThesaurusInfo.getChildren().clear();
                ThesaurusLabel.setVisible(false);
                DefinitionLabel.setVisible(false);
            }
        }
    }

    @FXML
    void addWord(ActionEvent event) throws IOException {
        Alert formatAlert = new Alert(Alert.AlertType.WARNING);
        formatAlert.setTitle("LƯU Ý");
        formatAlert.setHeaderText("Hãy chắc chắn rằng luôn có kí tự | trước mỗi từ bạn muốn thêm vào từ điển. Ví dụ:\n" +
                "| Hello\n*Xin chào\n| Goodbye\n*Tạm biệt");
        formatAlert.showAndWait();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter Filter = new FileChooser.ExtensionFilter("Text file", "*.txt");
        fc.getExtensionFilters().add(Filter);
        File file = fc.showOpenDialog(stage);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        if (file == null) {
            return;
        } else if (!file.exists()) {
            successAlert.setTitle("Thêm từ không thành công");
            successAlert.setHeaderText("Đường dẫn đến file không đúng hoặc không tồn tại");
        } else if (file.length() == 0) {
            successAlert.setTitle("Thêm từ không thành công");
            successAlert.setHeaderText("File " + file.getName() + " rỗng");
        } else {
            dict.importDataFromFile(file.getPath());
            successAlert.setTitle("Thêm từ thành công");
            successAlert.setHeaderText("Đã thêm " + dict.getNumOfInsertedWords() + " từ từ file: " + file.getName()
                    + ".\n Nếu có thiếu từ nào, xin hãy kiểm tra xem các từ đã ở đúng định dạng.");
        }
        successAlert.showAndWait();
        ShowList("");
        ShowList(currentWord);
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
