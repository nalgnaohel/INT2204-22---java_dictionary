package dictionary.ui;

import dictionary.backend.WordleFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class WordleStatsController {
    @FXML
    private Label gamesPlayed;

    @FXML
    private Label winRate;

    @FXML
    protected Label curStreak;

    @FXML
    protected Label longestStreak;

    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;
    private WordleStatsWindow wordleStatsWindow;
    private WordleFunction wordleFunction;

    public void init() {
        gamesPlayed.setText(Integer.toString(wordleFunction.getGamesPlayed()));
        winRate.setText(Double.toString(wordleFunction.getWinRate()));
        curStreak.setText(Integer.toString(wordleFunction.getCurrentStreak()));
        longestStreak.setText(Integer.toString(wordleFunction.getLongestStreak()));
        barchart.setTitle("Guesses Distribution");
        ArrayList<String> al = new ArrayList<>();
        for (int i = 1; i <= wordleFunction.getMaxTries(); i++) {
            al.add(Integer.toString(i));
        }
        yAxis.setCategories(FXCollections.observableArrayList(al));
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<Integer, Integer> curSet :
                wordleFunction.getNumOfGuess().entrySet()) {
            series.getData().add(new XYChart.Data<>(Integer.toString(curSet.getKey()), curSet.getValue()));
        }
        barchart.getData().add(series);
    }
    public void setWordleStatsWindow(WordleStatsWindow wordleStatsWindow) {
        this.wordleStatsWindow = wordleStatsWindow;
        this.wordleFunction = wordleStatsWindow.getWordleFunction();
    }

    public void close() {
        wordleStatsWindow.quit();
    }
}
