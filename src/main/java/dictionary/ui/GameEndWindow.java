package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameEndWindow {
    public void displayEndWindow(boolean win, String winningWord) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/endGame.fxml"));
        VBox root = fxmlLoader.load();
        Label label1 =(Label) root.getChildren().get(0);
        if (win) {
            label1.setText("YOU WON!");
        } else {
            label1.setText("YOU LOSE!");
        }
        if (this instanceof FindingEndGame) {
            Label label2 = (Label) root.getChildren().get(1);
            label2.setText("");
        }
        Label label3 = (Label) root.getChildren().get(2);
        label3.setText(winningWord);

        Button restartButton = new Button("PLAY AGAIN");
        restartButton.getStyleClass().add("restart-button");
        restartButton.setOnMouseClicked(e -> {
            if (this instanceof FindingEndGame) {
                ((FindingEndGame) this).getFindingController().restart();
            } else if (this instanceof WordleEndWindow) {
                ((WordleEndWindow) this).getWordleController().restart();
            }
            stage.close();
        });

        VBox buttonsVBox = new VBox(5);

        buttonsVBox.setAlignment(Pos.CENTER);

        Button quitButton = new Button("  QUIT  ");
        quitButton.getStyleClass().add("quit-button");
        quitButton.setOnMouseClicked(e -> {
            if (this instanceof FindingEndGame) {
                ((FindingEndGame) this).getFindingController().getFindingMainWindow().quit();
            } else if (this instanceof WordleEndWindow) {
                ((WordleEndWindow) this).getWordleController().quit();
            }
            stage.close();
        });

        buttonsVBox.getChildren().addAll(restartButton, quitButton);
        root.getChildren().add(buttonsVBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
