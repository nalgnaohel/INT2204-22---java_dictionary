package dictionary.ui.game.wordles;

import dictionary.ui.game.GameEndWindow;

public class WordleEndWindow extends GameEndWindow {
    private WordleController wordleController;

    public WordleController getWordleController() {
        return wordleController;
    }

    public void setWordleController(WordleController wordleController) {
        this.wordleController = wordleController;
    }
}
