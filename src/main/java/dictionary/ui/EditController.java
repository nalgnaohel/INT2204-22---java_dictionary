package dictionary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.Optional;

import static dictionary.Main.dict;

public class EditController {
    @FXML
    private TextField wordTarget;

    @FXML
    private TextArea wordMeaning;

    @FXML
    private Button cfButton;

    private EditWindow editWindow;

//    public void initialize() {
//
//    }

//    public void setWordTarget(String currentWord) {
//        wordTarget.setText(currentWord);
//        wordTarget.setEditable(false);
//    }

    public void setEditWindow(EditWindow editWindow) {
        this.editWindow = editWindow;
    }

    public void checkEdit(MouseEvent event) {
        String target = wordTarget.getText();
        String meaning = wordMeaning.getText();
        if (target == null || target.isEmpty() || meaning == null || meaning.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Chưa nhập từ để xóa");
            alert.showAndWait();
        } else {
            if (dict.lookUpWord(target).equals("Not found!\n")) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Thông báo");
                al.setHeaderText("Từ " + target + " không có trong từ điển. \nBạn có muốn thêm từ này vào từ điển không?");
                ButtonType dtb = new ButtonType("Có");
                ButtonType noDtb = new ButtonType("Không");
                al.getButtonTypes().clear();
                al.getButtonTypes().addAll(dtb, noDtb);
                Optional<ButtonType> opt = al.showAndWait();
                if (opt.get() == dtb) {
                    if (dict.insertWord(target, meaning)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText("Thêm từ " + target + " thành công!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText("Không thêm được từ " + target + "!");
                        alert.showAndWait();
                    }
                }
            } else {
                if (dict.updateWordMeaning(target, meaning)) {
                    editWindow.getController().editSuccess = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Sửa từ " + target + " thành công!");
                    alert.showAndWait();
                    if (editWindow.getController().currentWord.equals(target)) {
                        editWindow.getController().wordMeaning.getChildren().clear();
                        editWindow.getController().wordMeaning.getChildren().add(new Text(meaning));
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Không sửa được từ " + target + "!");
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