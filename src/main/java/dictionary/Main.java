package dictionary;

import dictionary.backend.Dictionary;
import dictionary.backend.DtbDictionary;
import dictionary.backend.TxtDictionary;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    public static Dictionary dict;
    public void start(Stage stage) throws IOException {

        selectDictionary(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/dictionary.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // logo dùng tạm =)) khi nào rảnh làm logo khác
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon/logo.png")));
        stage.setTitle("Dictionary App");

        stage.setResizable(false); // không cho phóng to, thu nhỏ cửa sổ
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            dict.close();
            dict.getHistory().export();
            dict.getFavorites().export();
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    @FXML
    private Button b1;

    @FXML
    private Button b2;
    public void selectDictionary(Stage stage) throws IOException {
        Alert selectAlert = new Alert(Alert.AlertType.CONFIRMATION);
        selectAlert.setTitle("Chon tu dien");
        selectAlert.setHeaderText("Ban co muon su dung tu dien ket noi voi " +
                "Co so du lieu MYSQL khong?\n " +
                "Neu co, hay chac chan minh da setup MySQL truoc.");

        ButtonType dtb = new ButtonType("Ket noi CSDL");
        ButtonType noDtb = new ButtonType("Khong ket noi CSDL");
        selectAlert.getButtonTypes().clear();
        selectAlert.getButtonTypes().addAll(dtb, noDtb);
        Optional<ButtonType> opt = selectAlert.showAndWait();

        if (opt.get() == dtb) {
            dict = new DtbDictionary();
            dict.initUtilsFile();
            //connect to Dtb
            try {
                dict.init();
                Alert dtbAlert = new Alert(Alert.AlertType.INFORMATION);
                dtbAlert.setTitle("Thong bao");
                dtbAlert.setHeaderText("Ket noi CSDL thanh cong!");
                dtbAlert.showAndWait();
            } catch (Exception e) {
                //Failed
                e.printStackTrace();

                Alert failedDtbAlert = new Alert(Alert.AlertType.ERROR);
                failedDtbAlert.setTitle("Thong bao");
                failedDtbAlert.setHeaderText("Khong ket noi duoc voi CSDL");
                failedDtbAlert.showAndWait();
                dict = new TxtDictionary();
                dict.initUtilsFile();
                dict.importDataFromFile("src/main/resources/data/dictionaries.txt");
            }
        } else if (opt.get() == noDtb) {
            dict = new TxtDictionary();
            dict.initUtilsFile();
            dict.importDataFromFile("src/main/resources/data/dictionaries.txt");
        }
        dict.export5Words();
    }

    public static void main(String[] args) {
        launch();
    }
}