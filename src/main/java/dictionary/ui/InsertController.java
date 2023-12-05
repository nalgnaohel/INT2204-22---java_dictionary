package dictionary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.Optional;

import static dictionary.Main.dict;

public class InsertController {
    @FXML
    private TextField wordTarget;

    @FXML
    private TextArea wordMeaning;

    @FXML
    private Button cfButton;

    private InsertWindow insertWindow;

//    public void initialize() {
//
//    }

//    public void setWordTarget(String currentWord) {
//        wordTarget.setText(currentWord);
//        wordTarget.setEditable(false);
//    }

    public void setInsertWindow(InsertWindow insertWindow) {
        this.insertWindow = insertWindow;
    }

    public void checkInsert(MouseEvent event) {
        String target = wordTarget.getText();
        String meaning = wordMeaning.getText();
        if (target == null || target.isEmpty() || meaning == null || meaning.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Chưa nhập từ để thêm");
            alert.showAndWait();
        } else {
            if (!dict.lookUpWord(target).equals("Not found!\n")) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Thông báo");
                al.setHeaderText("Từ " + target + " đã có trong từ điển. \nBạn có muốn sửa nghĩa từ này không?");
                ButtonType dtb = new ButtonType("Có");
                ButtonType noDtb = new ButtonType("Không");
                al.getButtonTypes().clear();
                al.getButtonTypes().addAll(dtb, noDtb);
                Optional<ButtonType> opt = al.showAndWait();
                if (opt.get() == dtb) {
                    if (dict.updateWordMeaning(target, meaning)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText("Sửa nghĩa của từ " + target + " thành công!");
                        alert.showAndWait();
                        if (insertWindow.getController().currentWord.equals(target)) {
                            insertWindow.getController().wordMeaning.getChildren().clear();
                            insertWindow.getController().wordMeaning.getChildren().add(new Text(meaning));
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText("Không sửa được nghĩa của từ " + target + "!");
                        alert.showAndWait();
                    }
                }
            } else {
                if (dict.insertWord(target, meaning)) {
                    insertWindow.getController().editSuccess = true;
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
        }
        insertWindow.quit();
    }

    public TextField getWordTarget() {
        return wordTarget;
    }
}
