package dictionary;

import dictionary.backend.Dictionary;
import dictionary.backend.DtbDictionary;
import dictionary.backend.History;
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
            History.exportHistory();
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        selectDictionary(stage);
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
            //connect to Dtb
            try {
                dict.init();
                Alert dtbAlert = new Alert(Alert.AlertType.INFORMATION);
                dtbAlert.setTitle("Thong bao");
                dtbAlert.setHeaderText("Ket noi CSDL thanh cong!");
                dtbAlert.show();
            } catch (Exception e) {
                //Failed
                e.printStackTrace();
                Alert failedDtbAlert = new Alert(Alert.AlertType.ERROR);
                failedDtbAlert.setTitle("Thong bao");
                failedDtbAlert.setHeaderText("Khong ket noi duoc voi CSDL");
                failedDtbAlert.show();
                dict = new TxtDictionary();
                dict.importDataFromFile("src/main/resources/data/demo.txt");
            }
        } else if (opt.get() == noDtb) {
            dict = new TxtDictionary();
            dict.importDataFromFile("src/main/resources/data/demo.txt");
        }
    }

    public static void main(String[] args) {
        History.loadHistory();
        //TxtDictionary dict = new TxtDictionary();
        /*DtbDictionary dtbDict = new DtbDictionary();
        History.loadHistory();
        try {
            dtbDict.init();
            System.out.println(dtbDict.lookUpWord("cat"));
            dtbDict.deleteWord("cat");
            dtbDict.insertWord("cat", "meo");
            System.out.println(dtbDict.lookUpWord("cat"));
            boolean f = dtbDict.updateWordMeaning("cat", "/kæt/\n" +
                    "* danh từ\n" +
                    "- con mèo\n" +
                    "- (động vật học) thú thuộc giống mèo (sư tử, hổ, báo...)\n" +
                    "- mụ đàn bà nanh ác; đứa bé hay cào cấu\n" +
                    "- (hàng hải) đòn kéo neo ((cũng) cat head)\n" +
                    "- roi chín dài (để tra tấn) ((cũng) cat o-nine-tails)\n" +
                    "- con khăng (để chơi đanh khăng)\n" +
                    "!all cats are grey in the dark (in the night)\n" +
                    "- (tục ngữ) tắt đèn nhà ngói cũng như nhà tranh\n" +
                    "!cat in the pan (cat-in-the-pan)\n" +
                    "- kẻ trở mặt, kẻ phản bội\n" +
                    "!the cat is out the bag\n" +
                    "- điều bí mật đã bị tiết lộ rồi\n" +
                    "!fat cat\n" +
                    "- (từ Mỹ,nghĩa Mỹ),  (từ lóng) tư bản kếch xù, tài phiệt\n" +
                    "!to fight like Kilkemy cats\n" +
                    "- giết hại lẫn nhau\n" +
                    "!to let the cat out of the bag\n" +
                    "- (xem) let\n" +
                    "!it rains cats and dogs\n" +
                    "- (xem) rain\n" +
                    "!to see which way the cat jumps; to wait for the cat to jump\n" +
                    "- đợi gió xoay chiều, đợi gió chiều nào thì theo chiều ấy\n" +
                    "!to room to swing a cat\n" +
                    "- (xem) room\n" +
                    "!to turn cat in the pan\n" +
                    "- trở mặt; thay đổi ý kiến (lúc lâm nguy); phản hồi\n" +
                    "* ngoại động từ\n" +
                    "- (hàng hải) kéo (neo) lên đòn kéo neo\n" +
                    "- đánh bằng roi chín dài\n" +
                    "* nội động từ\n" +
                    "- (thông tục) nôn mửa");
            System.out.println(f);
            //System.out.println(dtbDict.lookUpWord("cat"));
            System.out.println(dtbDict.lookUpWord("cellist"));
            ArrayList<Word> allWord = dtbDict.getAllWords();
            for (int i = 0; i < 20; i++) {
                System.out.println(allWord.get(i).getWordTarget());
                System.out.println(dtbDict.lookUpWord(allWord.get(i).getWordTarget()));
            }
            System.out.println(dtbDict.lookUpWord("dog"));
            ArrayList<Word> hist = History.getWordHistory();
            System.out.println(hist.size());
            for (int i = 0; i < hist.size(); i++) {
                System.out.print(i + " " + hist.get(i).getWordTarget() + "\n");
            }
            //System.out.println(dtbDict.getInfoFromAPI("cat"));
            //System.out.println(dtbDict.translateSentence("en", "vi", "I love cats!"));
            //dtbDict.playEngWordSound("There was a crooked man. He lived in a crooked house, " +
            //        "with a crooked cat and a crooked mouse");
            History.exportHistory();
            dtbDict.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the dtb!");
            e.printStackTrace();
        }*/
        launch();

    }
}