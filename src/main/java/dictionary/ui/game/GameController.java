package dictionary.ui.game;

import dictionary.backend.game.GameFunction;
import dictionary.ui.game.finding.FindingController;
import dictionary.ui.game.finding.FindingHelpWindow;
import dictionary.ui.game.finding.FindingMainWindow;
import dictionary.ui.game.finding.FindingStatsWindow;
import dictionary.ui.game.wordles.WordleController;
import dictionary.ui.game.wordles.WordleHelpWindow;
import dictionary.ui.game.wordles.WordleStatsWindow;

import java.io.IOException;

public abstract class GameController {
    protected GameFunction gameFunction;
    protected GameMainWindow gameMainWindow;
    public void quit() {
        gameMainWindow.quit();
    }

    public void setGameMainWindow(GameMainWindow gameMainWindow) {
        this.gameMainWindow = gameMainWindow;
    }

    public GameMainWindow getGameMainWindow() {
        return gameMainWindow;
    }

    public GameFunction getGameFunction() {
        return gameFunction;
    }

    public abstract void init();
    public abstract void restart();
    public void showStats() throws IOException {
        GameStatsWindow gameStatsWindow;
        if (this instanceof WordleController) {
            gameStatsWindow = new WordleStatsWindow();
        } else {
            gameStatsWindow = new FindingStatsWindow();
        }
        gameStatsWindow.setGameFunction(gameFunction);
        if (this instanceof FindingController) {
            ((FindingStatsWindow) gameStatsWindow).setFindingController((FindingController) this);
            ((FindingStatsWindow) gameStatsWindow).setFindingController((FindingController) this);
            ((FindingMainWindow)gameMainWindow).setFindingStatsWindow((FindingStatsWindow) gameStatsWindow);
            gameMainWindow.pauseCountdown();
        }
        gameStatsWindow.display();
        if (this instanceof FindingController) {
            ((FindingController) this).getFindingMainWindow().getRoot().requestFocus();
        }
    }

    public void showHelp() {
        GameHelpWindow gameHelpWindow;
        if (this instanceof FindingController) {
            gameHelpWindow = new FindingHelpWindow();
        } else {
            gameHelpWindow = new WordleHelpWindow();
        }
        if (this instanceof FindingController) {
            ((FindingHelpWindow)gameHelpWindow).setFindingController((FindingController) this);
            ((FindingMainWindow)gameMainWindow).setFindingHelpWindow((FindingHelpWindow) gameHelpWindow);
            gameMainWindow.pauseCountdown();
        }

        gameHelpWindow.display();
        if (this instanceof FindingController) {
            gameMainWindow.getRoot().requestFocus();
            gameMainWindow.continueCountdown();
        }
    }
}
