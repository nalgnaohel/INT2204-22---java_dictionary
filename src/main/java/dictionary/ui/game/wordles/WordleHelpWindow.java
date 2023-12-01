package dictionary.ui.game.wordles;

import dictionary.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Objects;

public class WordleHelpWindow {
    public static void display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("HOW TO PLAY");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));
        String instruct = "Guess the WORDLE in five tries. ";
        instruct += "Press Enter to submit.\n";
        instruct += "After each guess, the color of the tiles will change to \n show how close your guess was to the word.\n";
        Label helpParagraph = new Label(instruct);
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().add("instruct-text");

        Line line = new Line();
        line.setStroke(Paint.valueOf("b8b8b8"));
        line.setEndX(900);

        Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3");
        labelExample.setTextAlignment(TextAlignment.LEFT);
        labelExample.getStyleClass().add("instruct-text");

        ArrayList<Label> firstWord = new ArrayList<>();
        Label l1 = new Label("W");
        l1.getStyleClass().setAll("correct-letter");
        firstWord.add(l1);
        for (String letter : new String[]{"E", "A", "R", "Y"}) {
            Label l = new Label(letter);
            l.getStyleClass().setAll("default-tile");
            firstWord.add(l);
        }
        HBox firstWordVBox = new HBox(5);
        for (Label label : firstWord) {
            firstWordVBox.getChildren().add(label);
        }

        Label firstWordLabel = new Label("The letter W is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().add("instruct-text");

        ArrayList<Label> secondWord = new ArrayList<>();
        Label p = new Label("P");
        p.getStyleClass().setAll("default-tile");
        Label i = new Label("I");
        i.getStyleClass().setAll("existed-letter");
        secondWord.add(p);
        secondWord.add(i);
        for (String letter : new String[]{"L", "O", "T"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-tile");
            secondWord.add(label);
        }
        HBox secondWordVBox = new HBox(5);
        for (Label label : secondWord) {
            secondWordVBox.getChildren().add(label);
        }

        Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().add("instruct-text");

        ArrayList<Label> thirdWord = new ArrayList<>();
        for (String letter : new String[]{"V", "A", "G"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-tile");
            thirdWord.add(label);
        }
        Label u = new Label("U");
        u.getStyleClass().setAll("wrong-letter");
        Label e = new Label("E");
        e.getStyleClass().setAll("default-tile");
        thirdWord.add(u);
        thirdWord.add(e);
        HBox thirdWordVBox = new HBox(5);
        for (Label label : thirdWord) {
            thirdWordVBox.getChildren().add(label);
        }

        Label thirdWordLabel = new Label("The letter U is not in the word in any spot.");
        thirdWordLabel.getStyleClass().add("instruct-text");

        Button quitButton = new Button("Got it...");
        quitButton.getStyleClass().add("instruct-button");
        quitButton.setOnMouseClicked(ev ->{
            stage.close();
        } );

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(helpParagraph, line, labelExample, firstWordVBox,
                firstWordLabel, secondWordVBox, secondWordLabel, thirdWordVBox, thirdWordLabel, quitButton);
        Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/wordle.css"))
                        .toExternalForm());

        stage.setScene(scene);
        stage.showAndWait();
    }
}
