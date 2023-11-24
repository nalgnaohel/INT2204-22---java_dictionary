package dictionary;

import dictionary.ui.FindingController;
import dictionary.ui.FindingEndGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FindingMainWindow {
    private Stage gameStage;
    private FindingController findingController;
    private Timeline countdownTimeline;
    private Parent root;
    public void display() throws IOException {
        gameStage = new Stage();
        gameStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/findingMain.fxml"));
        //System.out.println(fxmlLoader);
        root = fxmlLoader.load();
        findingController = fxmlLoader.getController();
        findingController.setFindingMainWindow(this);
        findingController.init();

        //startCountdown();
        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.setHeight(700);
        gameStage.setWidth(800);
        gameStage.setResizable(false);
        gameStage.show();
        root.requestFocus();
    }

    public Parent getRoot(){
        return root;
    }

    public Stage getGameStage() {
        return this.gameStage;
    }

    public void quit() {
        gameStage.close();
    }

    public void startCountdown() {
        ObjectProperty<Duration> remainingDuration = new SimpleObjectProperty<>(Duration.ofSeconds(60));
        findingController.getRemainingTime().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("%02d:%02d", remainingDuration.get().toMinutesPart(), remainingDuration.get().toSecondsPart()),
                remainingDuration));

        countdownTimeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), (ActionEvent event) ->
                remainingDuration.setValue(remainingDuration.get().minus(1, ChronoUnit.SECONDS))));

        countdownTimeline.setCycleCount((int) remainingDuration.get().getSeconds());

        countdownTimeline.setOnFinished(ev -> {
            try {
                System.out.println("Lose!");
                findingController.getFindingFunction().setGamesPlayed(findingController.getFindingFunction().getGamesPlayed() + 1);
                findingController.getFindingFunction().update();
                FindingEndGame findingEndGame = new FindingEndGame();
                findingEndGame.setFindingController(findingController);
                findingEndGame.displayEndWindow(false, "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        countdownTimeline.play();
    }

    public void continueCountdown() {
        countdownTimeline.play();
    }

    public void pauseCountdown() {
        countdownTimeline.pause();
    }
}
