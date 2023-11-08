package dictionary;

import dictionary.backend.Trie;
import dictionary.backend.TxtDictionary;
import dictionary.backend.Word;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
//        TxtDictionary dict = new TxtDictionary();
//        dict.importDataFromFile("src/main/resources/data/dictionaries.txt");
        //dict.insertWord("cat", "meo");
        //System.out.println(dict.lookUpWord("cat"));
        //dict.deleteWord("cat");
        //dict.lookUpWord("cat");
//        for (int i = 0; i < Trie.search("in").size(); i++) {
//            System.out.println(Trie.search("in").get(i));
//        }

        launch();
    }
}