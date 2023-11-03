package ui;

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
        //ArrayList<Word> wordsList = new ArrayList<Word>();
        //launch();
        System.out.println();
    }
}