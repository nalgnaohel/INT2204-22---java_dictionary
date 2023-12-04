package dictionary.ui.game.wordles;

import dictionary.backend.game.GameFunction;
import dictionary.backend.game.WordleFunction;
import dictionary.ui.game.GameController;
import dictionary.ui.game.GameHelpWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static dictionary.Main.dict;

public class WordleController extends GameController {

    @FXML
    private TilePane wordsTilePane;
    private int curRow = 0;
    private int curColumn = 0;

    private final ArrayList<String> allWords = new ArrayList<>();
    private String winningWord;
    @FXML
    private AnchorPane gameArea;

    public WordleController() {
        this.gameFunction = new WordleFunction();
    }
    public void init() {
        initGrid(wordsTilePane);
        curRow = 0;
        curColumn = 0;
        winningWord = getRandomWord();
        System.out.println(winningWord);
        gameFunction.setHistoryPath("src/main/resources/data/wordle_history.txt");
        gameFunction.init();
    }

    public void initGrid(TilePane wordTilesPane) {
        for (int i = 0; i < 25; i++) {
            Label cur = new Label("");
            cur.setMaxHeight(50);
            cur.setMinHeight(50);
            cur.setMinHeight(50);
            cur.setMaxHeight(50);
            cur.getStyleClass().add("default-tile");
            wordTilesPane.getChildren().add(cur);
        }
    }

    private void initAllWords() {
        try {
            FileReader fr = new FileReader("src/main/resources/data/wordle_all.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.replace("\n", "");
                allWords.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomWord() {
        if (allWords.size() == 0) {
            initAllWords();
        }
        return allWords.get(new Random().nextInt(allWords.size()));
    }

    private Label getLabel(int row, int col) {
        int id = 5 * row + col;
        if ((wordsTilePane.getChildren().get(id)) instanceof Label) {
            return (Label) wordsTilePane.getChildren().get(id);
        }
        return null;
    }
    private String getLabelText(int row, int col) {
        Label label = getLabel(row, col);
        if (label != null) {
            return label.getText().toLowerCase();
        }
        return null;
    }

    private void setLabelText(int row, int col, String target) {
        Label label = getLabel(row, col);
        if (label != null) {
            label.setText(target.toUpperCase());
        }
    }

    private void setLabelStyleClass(int row, int col, String styleString) {
        Label label = getLabel(row, col);
        if (label != null) {
            label.getStyleClass().add(styleString);
        }
    }

    private void clearLabelStyleClass(int row, int col) {
        Label label = getLabel(row, col);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    private String getCurrentRowWord(int curRow) {
        String res = "";
        for (int i = 0; i < 5; i++) {
            String c = getLabelText(curRow, i);
            res += c.toLowerCase();
        }
        return res;
    }

    private void updateCurrentRow(int curRow) {
        for (int col = 0; col < 5; col++) {
            Label label = getLabel(curRow, col);
            String curChar = getLabelText(curRow, col);
            String styleClass;
            if (curChar != null) {
                if (!String.valueOf(winningWord.charAt(col)).toLowerCase().equals(curChar)){
                    boolean existed = false;
                    for (int j = 0; j < 5; j++) {
                        if (String.valueOf(winningWord.charAt(j)).toLowerCase().equals(curChar)) {
                            existed = true; break;
                        }
                    }
                    if (existed) {
                        styleClass = "existed-letter";
                    } else {
                        styleClass = "wrong-letter";
                    }
                } else { //correct
                    styleClass = "correct-letter";
                }

                //updateToFiles style
                label.getStyleClass().clear();
                label.getStyleClass().setAll(styleClass);
            }
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getSource() == gameArea) {
            if (event.getCode().isLetterKey()) {
                handleLetterKey(event);
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleBackspaceKey();
            } else if (event.getCode() == KeyCode.SHIFT) {
                handleEnterKey();
            }
        }
    }

    private void handleLetterKey(KeyEvent event) {
        System.out.println(event.getCode());
        if (getLabelText(curRow, curColumn).equals("")) {
            setLabelText(curRow, curColumn, event.getText());
            setLabelStyleClass(curRow, curColumn, "tile-with-letter");
            if (curColumn < 4) {
                curColumn++;
            }
        }
    }

    private void handleBackspaceKey() {
        if ((curColumn == 0 || curColumn == 4)
                && !getLabelText(curRow, curColumn).equals("")) {
            setLabelText(curRow, curColumn, "");
            clearLabelStyleClass(curRow, curColumn);
            setLabelStyleClass(curRow, curColumn, "default-tile");
        } else if ((curColumn > 0 && curColumn < 4)
        || (curColumn == 4 && getLabelText(curRow, curColumn).equals(""))) {
            curColumn--;
            setLabelText(curRow, curColumn, "");
            clearLabelStyleClass(curRow, curColumn);
            setLabelStyleClass(curRow, curColumn, "default-tile");
        }
    }

    private void handleEnterKey() {
        if (curColumn != 4 || (curColumn == 4 && getLabelText(curRow, curColumn).equals(""))) {
            System.out.println("Not enough characters!");
        } else {
            String rowWord = getCurrentRowWord(curRow);
            System.out.println(rowWord);
            if (rowWord.equals(winningWord)) {
                updateCurrentRow(curRow);
                gameFunction.updateWinCase(curRow + 1);
                gameFunction.updateToFiles();
                try {
                    WordleEndWindow wordleEndWindow = new WordleEndWindow();
                    wordleEndWindow.setWordleController(this);
                    wordleEndWindow.displayEndWindow(true, winningWord);
                    wordleEndWindow.setColor("green");
                    getGameMainWindow().setEffect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (!dict.lookUpWord(rowWord).equals("Not found!\n")) {
                updateCurrentRow(curRow);
                curRow++; curColumn = 0;
                if (curRow == 5) {
                    gameFunction.updateLoseCase();
                    gameFunction.updateToFiles();
                    try {
                        WordleEndWindow wordleEndWindow = new WordleEndWindow();
                        wordleEndWindow.setWordleController(this);
                        wordleEndWindow.displayEndWindow(false, winningWord);
                        wordleEndWindow.setColor("red");
                        getGameMainWindow().setEffect();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    ShowText.display(gameMainWindow.getGameStage(), true);
                }
            } else {
                ShowText.display(gameMainWindow.getGameStage(), false);
            }
        }
    }
    public void restart() {
        wordsTilePane.getChildren().clear();
        getGameMainWindow().removeEffect();
        init();
    }

}
