package dictionary.ui;

import dictionary.Main;
import dictionary.WordleMainWindow;
import dictionary.backend.WordleFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static dictionary.Main.dict;

public class WordleController {

    @FXML
    private Button restartButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button statsButton;

    @FXML
    private TilePane wordsTilePane;
    private int curRow = 0;
    private int curColumn = 0;

    private final ArrayList<String> allWords = new ArrayList<>();
    private String winningWord;
    private WordleMainWindow wordleMainWindow;
    private WordleFunction wordleFunction = new WordleFunction();

    public void setWordleMainWindow(WordleMainWindow wmw) {
        this.wordleMainWindow = wmw;
    }

    public void init() {
        initGrid(wordsTilePane);
        curRow = 0;
        curColumn = 0;
        winningWord = getRandomWord();
        System.out.println(winningWord);
        WordleEndWindow.setQuit();
        WordleEndWindow.setRestart();
        wordleFunction.setHistoryPath("src/main/resources/data/wordle_history.txt");
        wordleFunction.init();
    }

    public void initGrid(TilePane wordTilesPane) {
        for (int i = 0; i < 25; i++) {
            Label cur = new Label("");
            cur.setMaxHeight(50);
            cur.setMinHeight(50);
            cur.setMinHeight(50);
            cur.setMaxHeight(50);
            cur.setPrefHeight(50);
            cur.setPrefWidth(50);
            cur.getStyleClass().add("default-tile");
            wordTilesPane.getChildren().add(cur);
        }
        //System.out.println(wordTilesPane.getChildren().size());
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
            //System.out.println("Done getting!");
        } catch (IOException e) {
            System.out.println("Cannot find the file!\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
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
            return label.getText().toLowerCase(); //de tien ghep tra tu dien
        }
        return null;
    }

    private void setLabelText(int row, int col, String target) {
        System.out.println("setLabelText call getLabel...");
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

                //update style
                label.getStyleClass().clear();
                label.getStyleClass().setAll(styleClass);
            }
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode().isLetterKey()) {
            handleLetterKey(event);
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            handleBackspaceKey();
        } else if (event.getCode() == KeyCode.ENTER) {
            handleEnterKey();
        } else {
            System.out.println("Something else...");
        }
    }

    private void handleLetterKey(KeyEvent event) {
        //check current row is full
        System.out.println(event.getCode());
        if (getLabelText(curRow, curColumn).equals("")) {
            //System.out.println("Before: " + curRow + " " +curColumn);
            setLabelText(curRow, curColumn, event.getText());
            setLabelStyleClass(curRow, curColumn, "tile-with-letter");
            if (curColumn < 4) {
                curColumn++;
            }
            //System.out.println("After: " + curRow + " " +curColumn);
        }
    }

    private void handleBackspaceKey() {
        //System.out.println(curRow + " " + curColumn);
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
        //System.out.println(curRow + " " + curColumn);
    }

    private void handleEnterKey() {
        if (curColumn != 4 || (curColumn == 4 && getLabelText(curRow, curColumn).equals(""))) {
            System.out.println("Not enough characters!");
        } else {
            String rowWord = getCurrentRowWord(curRow);
            if (rowWord.equals(winningWord)) {
                //win case
                updateCurrentRow(curRow);
                wordleFunction.setGamesWon(wordleFunction.getGamesWon() + 1);
                wordleFunction.setGamesPlayed(wordleFunction.getGamesPlayed() + 1);
                wordleFunction.setCurrentStreak(wordleFunction.getCurrentStreak() + 1);
                if (wordleFunction.getCurrentStreak() > wordleFunction.getLongestStreak()) {
                    wordleFunction.setLongestStreak(wordleFunction.getCurrentStreak());
                }
                int old = wordleFunction.getNumOfGuess().get(curRow + 1);
                wordleFunction.getNumOfGuess().put(curRow + 1, old + 1);
                wordleFunction.update();
                try {
                    WordleEndWindow.displayEndWindow(true, winningWord);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (!dict.lookUpWord(rowWord).equals("Not found!")) {
                updateCurrentRow(curRow);
                curRow++; curColumn = 0;
                if (curRow == 5) {
                    wordleFunction.setGamesPlayed(wordleFunction.getGamesPlayed() + 1);
                    wordleFunction.setCurrentStreak(0);
                    wordleFunction.update();
                    try {
                        WordleEndWindow.displayEndWindow(false, winningWord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    ShowText.display(wordleMainWindow.getGameStage(), true);
                }
            } else { //not a valid word
                System.out.println("Not a word!");
                ShowText.display(wordleMainWindow.getGameStage(), false);
            }
            if (WordleEndWindow.isRestart()) {
                restart();
            }
            if (WordleEndWindow.isQuit()) {
                wordleFunction.update();
                wordleMainWindow.quit();
            }
        }
    }
    public void restart() {
        wordsTilePane.getChildren().clear();
        init();
    }

    public void showHelp() {
        WordleHelpWindow.display();
    }

    public void showStats() throws IOException {
        WordleStatsWindow wordleStatsWindow = new WordleStatsWindow();
        wordleStatsWindow.setWordleFunction(wordleFunction);
        wordleStatsWindow.display();
    }
}
