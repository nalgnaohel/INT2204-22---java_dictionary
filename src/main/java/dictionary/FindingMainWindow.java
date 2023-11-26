package dictionary;

import dictionary.ui.*;
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
    private FindingHelpWindow findingHelpWindow;
    private FindingStatsWindow findingStatsWindow;
    private QuestionWindow questionWindow;
    public void display() throws IOException {
        gameStage = new Stage();
        gameStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/findingMain.fxml"));
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
        gameStage.setOnCloseRequest(windowEvent -> {
            countdownTimeline.stop();
        });
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
        if (findingHelpWindow != null) {
            findingHelpWindow.quit();
        }
        if (findingStatsWindow != null) {
            findingStatsWindow.quit();
        }
        if (questionWindow != null) {
            questionWindow.quit();
        }
        gameStage.close();
    }

    public void startCountdown() {
        ObjectProperty<Duration> remainingDuration = new SimpleObjectProperty<>(Duration.ofSeconds(20));
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

    public FindingHelpWindow getFindingHelpWindow() {
        return findingHelpWindow;
    }

    public void setFindingHelpWindow(FindingHelpWindow findingHelpWindow) {
        this.findingHelpWindow = findingHelpWindow;
    }

    public FindingStatsWindow getFindingStatsWindow() {
        return findingStatsWindow;
    }

    public void setFindingStatsWindow(FindingStatsWindow findingStatsWindow) {
        this.findingStatsWindow = findingStatsWindow;
    }

    public QuestionWindow getQuestionWindow() {
        return questionWindow;
    }

    public void setQuestionWindow(QuestionWindow questionWindow) {
        this.questionWindow = questionWindow;
    }

    public void continueCountdown() {
        countdownTimeline.play();
    }

    public void pauseCountdown() {
        countdownTimeline.pause();
    }
}
