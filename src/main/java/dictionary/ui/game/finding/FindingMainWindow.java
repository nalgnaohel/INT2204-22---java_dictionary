package dictionary.ui.game.finding;

import dictionary.ui.game.GameMainWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FindingMainWindow extends GameMainWindow {
    private Timeline countdownTimeline;
    private FindingHelpWindow findingHelpWindow;
    private FindingStatsWindow findingStatsWindow;
    private QuestionWindow questionWindow;

    public FindingMainWindow() {
        this.fxmlPath = "fxml/findingMain.fxml";
    }

    public void startCountdown() {
        FindingController findingController = (FindingController) this.gameController;
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
                findingController.getGameFunction().updateLoseCase();
                findingController.getGameFunction().updateToFiles();
                FindingEndGame findingEndGame = new FindingEndGame();
                findingEndGame.setFindingController(findingController);
                findingEndGame.displayEndWindow(false, "");
                findingEndGame.setColor("red");
                this.setEffect();
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

    public Timeline getCountdownTimeline() {
        return countdownTimeline;
    }
}
