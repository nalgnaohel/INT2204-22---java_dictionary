package dictionary.ui.game;

import dictionary.Main;
import dictionary.ui.game.finding.FindingMainWindow;
import dictionary.ui.game.wordles.WordleMainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class GameMainWindow {
    protected Stage gameStage;
    protected String fxmlPath;
    protected GameController gameController;
    protected AnchorPane root;
    public void display() throws IOException {
        gameStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
        root = fxmlLoader.load();
        gameController = fxmlLoader.getController();
        gameController.setGameMainWindow(this);
        gameController.init();

        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.setHeight(700);
        gameStage.setWidth(900);
        gameStage.setResizable(false);
        if (this instanceof FindingMainWindow) {
            gameStage.setOnCloseRequest(windowEvent -> {
                ((FindingMainWindow) this).getCountdownTimeline().stop();
            });
        }
        gameStage.show();
        root.requestFocus();
    }

    public Stage getGameStage() {
        return this.gameStage;
    }

    public void quit() {
        if (this instanceof FindingMainWindow) {
            if (((FindingMainWindow) this).getFindingHelpWindow() != null) {
                ((FindingMainWindow) this).getFindingHelpWindow().quit();
            }
            if (((FindingMainWindow) this).getFindingStatsWindow() != null) {
                ((FindingMainWindow) this).getFindingStatsWindow().quit();
            }
            if (((FindingMainWindow) this).getQuestionWindow() != null) {
                ((FindingMainWindow) this).getQuestionWindow().quit();
            }
        }
        gameStage.close();
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void startCountdown() {

    }

    public void continueCountdown() {

    }

    public void pauseCountdown() {

    }
}
