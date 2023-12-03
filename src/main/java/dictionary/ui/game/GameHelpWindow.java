package dictionary.ui.game;

import dictionary.Main;
import dictionary.ui.game.finding.FindingHelpWindow;
import dictionary.ui.game.wordles.WordleHelpWindow;
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

public class GameHelpWindow {
    private Stage stage;
    protected String instruct;
    public void display() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("HOW TO PLAY");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));

        Label helpParagraph = new Label(instruct);
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().add("instruct-text");

        Button quitButton = new Button("Got it...");
        quitButton.getStyleClass().add("instruct-button");
        quitButton.setOnMouseClicked(ev ->{
            if (this instanceof FindingHelpWindow) {
                ((FindingHelpWindow) this).getFindingController().getGameMainWindow().getRoot().requestFocus();
            }
            stage.close();
        } );

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(helpParagraph);
        if (this instanceof WordleHelpWindow) {
            ((WordleHelpWindow) this).addWordleInstruct(root);
        }
        root.getChildren().add(quitButton);
        Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/wordle.css"))
                .toExternalForm());

        stage.setScene(scene);
        stage.showAndWait();
        if (this instanceof FindingHelpWindow) {
            stage.setOnCloseRequest(windowEvent -> {
                ((FindingHelpWindow) this).getFindingController().getFindingMainWindow().setFindingHelpWindow(null);
            });
        }
    }

    public void quit() {
        stage.close();
    }
}
