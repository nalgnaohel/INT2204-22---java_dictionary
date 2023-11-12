package dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/dictionary.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // logo dùng tạm =)) khi nào rảnh làm logo khác
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon/logo.png")));
        stage.setTitle("Dictionary App");

        stage.setResizable(false); // không cho phóng to, thu nhỏ cửa sổ
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
//        ArrayList<Word> wordsList = new ArrayList<Word>();
        //TxtDictionary dict = new TxtDictionary();
        /*DtbDictionary dtbDict = new DtbDictionary();
        try {
            dtbDict.init();
            System.out.println(dtbDict.lookUpWord("cat"));
            dtbDict.deleteWord("cat");
            dtbDict.insertWord("cat", "meo");
            System.out.println(dtbDict.lookUpWord("cat"));
            dtbDict.updateWordMeaning("cat", "/kæt/\n" +
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
            System.out.println(dtbDict.lookUpWord("cat"));
            System.out.println(dtbDict.getInfoFromAPI("cat"));
            System.out.println(dtbDict.translateSentence("en", "vi", "I love cats!"));
            dtbDict.playEngWordSound("There was a crooked man. He lived in a crooked house, " +
                    "with a crooked cat and a crooked mouse");
        } catch (SQLException e) {
            System.out.println("Cannot connect to the dtb!");
            e.printStackTrace();
        }*/
        launch();

    }
}