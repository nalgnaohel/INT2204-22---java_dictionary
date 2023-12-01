package dictionary.ui.game;

import dictionary.backend.GameFunction;
import dictionary.backend.WordleFunction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;

public class GameStatsController {
    @FXML
    private Label gamesPlayed;

    @FXML
    private Label winRate;

    @FXML
    private Label curStreak;

    @FXML
    private Label longestStreak;

    @FXML
    private BarChart<Number, String> barchart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;

    @FXML
    private Label curStreakText;

    @FXML
    private Label longestStreakText;

    private GameStatsWindow gameStatsWindow;
    private GameFunction gameFunction;

    public void initialize(GameStatsWindow gameStatsWindow) {
        setGameStatsWindow(gameStatsWindow);
        gamesPlayed.setText(Integer.toString(gameFunction.getGamesPlayed()));
        winRate.setText(Double.toString(gameFunction.getWinRate()));

        if (gameFunction instanceof WordleFunction) {
            longestStreak.setText(Integer.toString(((WordleFunction) gameFunction).getLongestStreak()));
            curStreak.setText(Integer.toString(((WordleFunction) gameFunction).getCurrentStreak()));
        } else {
            longestStreak.setText("");
            longestStreakText.setText("");
            curStreak.setText("");
            curStreakText.setText("");
        }

        barchart.setTitle("Number of Right Answer to Win Distribution");
        ArrayList<String> al = new ArrayList<>();
        for (int i = 1; i <= gameFunction.getMaxTries(); i++) {
            //System.out.println(i + ": " + findingFunction.getNumOfGuess().get(i));
            al.add(Integer.toString(i));
        }
        yAxis.setCategories(FXCollections.observableArrayList(al));
        for (String s : yAxis.getCategories()) {
            System.out.print(s + " ");
        }
        System.out.println("");
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Map.Entry<Integer, Integer> curSet :
                gameFunction.getNumOfGuess().entrySet()) {
            series.getData().add(new XYChart.Data<>(curSet.getValue(), Integer.toString(curSet.getKey())));
        }
        barchart.getData().add(series);
    }

    public void setGameStatsWindow(GameStatsWindow gameStatsWindow) {
        System.out.println(gameStatsWindow);
        System.out.println(gameStatsWindow.getGameFunction());
        this.gameStatsWindow = gameStatsWindow;
        this.gameFunction = gameStatsWindow.getGameFunction();
    }

    public void close() {
        gameStatsWindow.quit();
    }
}
