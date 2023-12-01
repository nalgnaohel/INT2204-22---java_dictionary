package dictionary.ui.game.finding;

import dictionary.ui.game.GameEndWindow;

public class FindingEndGame extends GameEndWindow {
    private FindingController findingController;

    public FindingController getFindingController() {
        return findingController;
    }

    public void setFindingController(FindingController fr) {
        this.findingController = fr;
    }
}
