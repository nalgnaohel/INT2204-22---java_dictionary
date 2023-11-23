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

public class FindingEndGame {
    private static boolean restart;
    private static boolean quit;
    private static FindingController findingController;
    public static void displayEndWindow(boolean win, String winningWord) throws IOException {
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

        Label l3 = (Label) root.getChildren().get(2);
        l3.setText(winningWord);

        Button restartButton = new Button("PLAY AGAIN");
        restartButton.getStyleClass().add("restart-button");
        restartButton.setOnMouseClicked(e -> {
            restart = true;
            findingController.restart();
            stage.close();
        });

        VBox buttonsVBox = new VBox(5);

        buttonsVBox.setAlignment(Pos.CENTER);

        Button quitButton = new Button("  QUIT");
        quitButton.getStyleClass().add("quit-button");
        quitButton.setOnMouseClicked(e -> {
            restart = false;
            quit = true;
            findingController.getFindingMainWindow().quit();
            stage.close();
        });

        buttonsVBox.getChildren().addAll(restartButton, quitButton);
        root.getChildren().add(buttonsVBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean isRestart() {
        return restart;
    }

    public static boolean isQuit() {
        return quit;
    }

    public static void setRestart() {
        restart = false;
    }

    public static void setQuit() {
        quit = false;
    }

    public static FindingController getFindingController() {
        return findingController;
    }

    public static void setFindingController(FindingController fr) {
        findingController = fr;
    }
}
