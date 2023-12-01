package dictionary.ui.game.finding;

import dictionary.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class FindingHelpWindow {
    private FindingController findingController;
    private Stage stage;
    public void display() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("HOW TO PLAY");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));
        String instruct = "You have to take our ninja to the treasure by\n" +
                "choosing a suitable route\n";
        instruct += "Use arrow keys to move.\n";
        instruct += "When reaching a (?) sign, you have to answer the\n" +
                "question behind it.\n";
        instruct += "A correct answer allows you to continue your route.\n" +
                "A wrong answer will turn the square turns into rock;\n" +
                "you cannot move forward and have to find another route.\n";
        instruct += "You win if you reach the treasure.\n" +
                "You will lose if the time is up.\n";
        Label helpParagraph = new Label(instruct);
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().add("instruct-text");

        Button quitButton = new Button("Got it...");
        quitButton.getStyleClass().add("instruct-button");
        quitButton.setOnMouseClicked(ev ->{
            findingController.getFindingMainWindow().getRoot().requestFocus();
            stage.close();
        } );

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(helpParagraph, quitButton);
        Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/wordle.css"))
                .toExternalForm());

        stage.setScene(scene);
        stage.showAndWait();
        stage.setOnCloseRequest(windowEvent -> {
            findingController.getFindingMainWindow().setFindingHelpWindow(null);
        });
    }

    public void quit() {
        stage.close();
    }

    public void setFindingController(FindingController fc) {
        findingController = fc;
    }

    public FindingController getFindingController() {
        return findingController;
    }
}
