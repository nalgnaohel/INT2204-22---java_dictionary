package dictionary.ui.game.finding;

import dictionary.ui.game.GameStatsWindow;

public class FindingStatsWindow extends GameStatsWindow {
    private FindingController findingController;

    public FindingStatsWindow() {
        this.barChartTitle = "Number of Right Answer to Win Distribution";
    }

    public FindingController getFindingController() {
        return findingController;
    }

    public void setFindingController(FindingController findingController) {
        this.findingController = findingController;
    }

}
