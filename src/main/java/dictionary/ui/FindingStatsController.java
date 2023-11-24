package dictionary.ui;

import dictionary.backend.FindingFunction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;

public class FindingStatsController {
    @FXML
    private Label gamesPlayed;

    @FXML
    private Label winRate;

    @FXML
    private BarChart<Number, String> barchart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;
    private FindingStatsWindow findingStatsWindow;
    private FindingFunction findingFunction;

    public void init() {
        gamesPlayed.setText(Integer.toString(findingFunction.getGamesPlayed()));
        winRate.setText(Double.toString(findingFunction.getWinRate()));
        barchart.setTitle("Number of Right Answer to Win Distribution");
        ArrayList<String> al = new ArrayList<>();
        for (int i = 1; i <= findingFunction.getMaxTries(); i++) {
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
                findingFunction.getNumOfGuess().entrySet()) {
            series.getData().add(new XYChart.Data<>(curSet.getValue(), Integer.toString(curSet.getKey())));
        }
        barchart.getData().add(series);
    }
    public void setFindingStatsWindow(FindingStatsWindow findingStatsWindow) {
        this.findingStatsWindow = findingStatsWindow;
        this.findingFunction = findingStatsWindow.getFindingFunction();
    }

    public void close() {
        findingStatsWindow.quit();
    }
}
