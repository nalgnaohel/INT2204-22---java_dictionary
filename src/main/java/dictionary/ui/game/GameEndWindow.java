package dictionary.ui.game;

import dictionary.Main;
import dictionary.ui.game.finding.FindingEndGame;
import dictionary.ui.game.wordles.WordleEndWindow;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameEndWindow {
    GameEndController gameEndController;
    public void displayEndWindow(boolean win, String winningWord) throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/endGame.fxml"));
        VBox root = fxmlLoader.load();
        gameEndController = fxmlLoader.getController();

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
        label3.setText(winningWord.toUpperCase());
        if (winningWord.length() > 10) {
            label3.getStyleClass().add("winning-word-long");
        }

        Button restartButton = gameEndController.getRestartButton();
        restartButton.setOnMouseClicked(e -> {
            if (this instanceof FindingEndGame) {
                ((FindingEndGame) this).getFindingController().restart();
            } else if (this instanceof WordleEndWindow) {
                ((WordleEndWindow) this).getWordleController().restart();
            }
            stage.close();
        });

        Button quitButton = gameEndController.getQuitButton();
        quitButton.setOnMouseClicked(e -> {
            if (this instanceof FindingEndGame) {
                ((FindingEndGame) this).getFindingController().getFindingMainWindow().quit();
            } else if (this instanceof WordleEndWindow) {
                ((WordleEndWindow) this).getWordleController().quit();
            }
            stage.close();
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void setColor(String color) {
        gameEndController.setColor(color);
    }
}
