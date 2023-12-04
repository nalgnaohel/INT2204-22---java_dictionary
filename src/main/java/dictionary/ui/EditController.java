package dictionary.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static dictionary.Main.dict;

public class EditController {
    @FXML
    private TextField wordTarget;

    @FXML
    private TextArea wordMeaning;

    @FXML
    private Button cfButton;

    private EditWindow editWindow;

    public void initialize(boolean canEdit, String currentWord) {
        if (!canEdit) {
            setWordTarget(currentWord);
        }
    }

    public void setWordTarget(String currentWord) {
        wordTarget.setText(currentWord);
        wordTarget.setEditable(false);
    }

    public void setEditWindow(EditWindow editWindow) {
        this.editWindow = editWindow;
    }

    public void editOrInsert(MouseEvent event) {
        String target = wordTarget.getText();
        if (target == null || target.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loi");
            alert.setHeaderText("Chua nhap tu de sua hoac them");
            alert.showAndWait();
        } else {
            if (dict.lookUpWord(target).equals("Not found!\n")) {
                String meaning = wordMeaning.getText();
                if (dict.insertWord(target, meaning)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thong bao");
                    alert.setHeaderText("Them tu " + target + " thanh cong!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Loi");
                    alert.setHeaderText("Khong them duoc tu " + target + "!");
                    alert.showAndWait();
                }
            } else {
                String meaning = wordMeaning.getText();
                if (dict.updateWordMeaning(target, meaning)) {
                    editWindow.getController().editSuccess = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thong bao");
                    alert.setHeaderText("Sua tu " + target + " thanh cong!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Loi");
                    alert.setHeaderText("Khong sua duoc tu " + target + "!");
                    alert.showAndWait();
                }
            }
        }
        editWindow.quit();
    }

    public TextField getWordTarget() {
        return wordTarget;
    }
}