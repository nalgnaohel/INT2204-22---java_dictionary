package dictionary.ui;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShowText {
    private static final List<String> correctMessages = Arrays.asList(
            "G R E A T",
            "S P L E N D I D",
            "I M P R E S S I V E",
            "G O O D"
    );
    private static final String wrongMessage = "Not a valid word. Try again!";
    public static void display(Stage parent, boolean correct) {
        Stage child = new Stage();
        child.initOwner(parent);
        child.initStyle(StageStyle.TRANSPARENT);
        child.setResizable(false);

        Text message = new Text();
        if (correct) {
            int id = ThreadLocalRandom.current().nextInt(0, correctMessages.size());
            message.setText(correctMessages.get(id));
        } else {
            message.setText(wrongMessage);
        }

        message.getStyleClass().setAll("h4");
        message.setFill(Color.ANTIQUEWHITE);
        message.setFont(Font.font("120px Myriad Pro"));

        StackPane root = new StackPane(message);
        root.setStyle("-fx-background-radius: 10; "
                + "-fx-background-color: rgb(14,14,14);" +
                " -fx-padding: 10;");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        child.setScene(scene);
        child.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
        fadeTransition.setFromValue(0.75);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(ev -> child.close());
        fadeTransition.play();
    }
}
