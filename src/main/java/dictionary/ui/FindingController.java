package dictionary.ui;

import dictionary.FindingMainWindow;
import dictionary.Main;
import dictionary.backend.FindingFunction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import static dictionary.Main.dict;

public class FindingController {
    @FXML
    private TilePane map;
    @FXML
    private Label remainingTime;

    private FindingMainWindow findingMainWindow;
    private FindingFunction findingFunction = new FindingFunction();
    private int numofQuest;
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, -1, 0, 1};
    private int curX;
    private int curY;
    private int changeToStone;
    private ArrayList<Integer> availableQuestId = new ArrayList<>();
    private int[][] mapIds =  {{0, 0, 1, 0, 0, 0, 1, 2},
            {0, 2, 0, 2, 2, 0, 0, 2},
            {1, 0, 1, 0, 2, 2, 1, 0},
            {0, 2, 0, 0, 1, 0, 0, 2},
            {1, 0, 2, 2, 0, 0, 1, 0},
            {0, 0, 2, 0, 0, 0, 0, 0},
            {2, 0, 1, 0, 1, 0, 0, 2},
            {2, 2, 0, 0, 2, 0, 0, 3}};
    private final int[][] levelmaps = {{0, 0, 1, 0, 0, 0, 1, 2},
            {0, 2, 0, 2, 2, 0, 0, 2},
            {1, 0, 1, 0, 2, 2, 1, 0},
            {0, 2, 0, 0, 1, 0, 0, 2},
            {1, 0, 2, 2, 0, 0, 1, 0},
            {0, 0, 2, 0, 0, 0, 0, 0},
            {2, 0, 1, 0, 1, 0, 0, 2},
            {2, 2, 0, 0, 2, 0, 0, 3}};

    public void init() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mapIds[i][j] = levelmaps[i][j];
                System.out.print(mapIds[i][j] + " ");
            }
            System.out.println("");
        }
        curX = 0;
        curY = 0;
        findingMainWindow.startCountdown();
        findingFunction.setHistoryPath("src/main/resources/data/finding_history.txt");
        findingFunction.init();
        numofQuest = 0;
        changeToStone = 0; //-1 if wrong ans.
        for (int i = 1; i <= 10; i++) {
            availableQuestId.add(i);
        }
        initMap();
    }

    public void initMap() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setMaxWidth(64);
                stackPane.setMaxHeight(64);
                stackPane.setMinWidth(64);
                stackPane.setMinHeight(64);
                stackPane.setPrefHeight(64);
                stackPane.setPrefWidth(64);

                InputStream inputStream = Main.class.getResourceAsStream("icon/grass.png");
                Image image = new Image(inputStream);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                stackPane.getChildren().add(imageView);
                if (i == 0 && j == 0) {
                    InputStream ip = Main.class.getResourceAsStream("icon/character.png");
                    Image im = new Image(ip);
                    ImageView iv = new ImageView(im);
                    iv.setFitHeight(64);
                    iv.setFitWidth(64);
                    stackPane.getChildren().add(iv);
                }
                if (mapIds[i][j] == 2) {
                    InputStream ip = Main.class.getResourceAsStream("icon/stone.png");
                    Image im = new Image(ip);
                    ImageView iv = new ImageView(im);
                    iv.setFitHeight(64);
                    iv.setFitWidth(64);
                    stackPane.getChildren().add(iv);
                } else if (mapIds[i][j] == 1) {
                    InputStream ip = Main.class.getResourceAsStream("icon/question.png");
                    Image im = new Image(ip);
                    ImageView iv = new ImageView(im);
                    iv.setFitHeight(64);
                    iv.setFitWidth(64);
                    stackPane.getChildren().add(iv);
                    System.out.println(i + " " + j + ": " + stackPane.getChildren().size());
                } else if (mapIds[i][j] == 3) {
                    InputStream ip = Main.class.getResourceAsStream("icon/treasure.png");
                    Image im = new Image(ip);
                    ImageView iv = new ImageView(im);
                    iv.setFitHeight(64);
                    iv.setFitWidth(64);
                    stackPane.getChildren().add(iv);
                }
                map.getChildren().add(stackPane);
            }
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        //LURD
        if (event.getCode() == KeyCode.LEFT) {
            handleMove(0);
        } else if (event.getCode() == KeyCode.UP) {
            handleMove(1);
        } else if (event.getCode() == KeyCode.RIGHT) {
            handleMove(2);
        } else if (event.getCode() == KeyCode.DOWN) {
            handleMove(3);
        } else {
            System.out.println("Something else...");
        }
    }

    public void moveToNewTile(int destX, int destY) {
        StackPane curSP = (StackPane) map.getChildren().get(curY * 8 + curX);
        ImageView imageView = (ImageView) curSP.getChildren().get(curSP.getChildren().size() - 1);
        curSP.getChildren().remove(curSP.getChildren().size() - 1);
        StackPane destSP = (StackPane) map.getChildren().get(destY * 8 + destX);
        destSP.getChildren().add(imageView);
        curX = destX; curY = destY;
    }
    public void handleMove(int id) {
        int destX = curX + dx[id];
        int destY = curY + dy[id];
        System.out.println("Destination: " + destX + " " + destY + " " + mapIds[destY][destX]);
        if (destX < 0 || destX >= 8 || destY < 0 || destY >= 8) {
            System.out.println("Invalid move...");
            return;
        }
        if (mapIds[destY][destX] == 0) {
            //move
            System.out.println("Grass...");
            moveToNewTile(destX, destY);
        } else if (mapIds[destY][destX] == 1) {
            //launch question
            int idQuest = ThreadLocalRandom.current().nextInt(0, availableQuestId.size());
            ArrayList<String> quest = dict.getQuestion(availableQuestId.get(idQuest));
            availableQuestId.remove(idQuest);
            try {
                QuestionWindow questionWindow = new QuestionWindow();
                questionWindow.setFindingController(this);
                findingMainWindow.setQuestionWindow(questionWindow);
                questionWindow.display(quest);
                numofQuest++;
                if (changeToStone == 1) {
                    mapIds[destY][destX] = 2;
                    StackPane stackPane = (StackPane) map.getChildren().get(destY * 8 + destX);
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                    InputStream ip = Main.class.getResourceAsStream("icon/stone.png");
                    Image im = new Image(ip);
                    ImageView iv = new ImageView(im);
                    iv.setFitHeight(64);
                    iv.setFitWidth(64);
                    stackPane.getChildren().add(iv);
                    changeToStone = 0;
                } else if (changeToStone == -1) {
                    System.out.println("Turning " + destX + " - " + destY + "to grass...");
                    mapIds[destY][destX] = 0;
                    StackPane stackPane = (StackPane) map.getChildren().get(destY * 8 + destX);
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                    moveToNewTile(destX, destY);
                    changeToStone = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mapIds[destY][destX] == 3) {
            //win
            findingFunction.setGamesPlayed(findingFunction.getGamesPlayed() + 1);
            findingFunction.setGamesWon(findingFunction.getGamesWon() + 1);
            int cur = findingFunction.getNumOfGuess().get(numofQuest);
            findingFunction.getNumOfGuess().put(numofQuest, cur + 1);
            findingFunction.update();
            try {
                FindingEndGame findingEndGame = new FindingEndGame();
                findingEndGame.setFindingController(this);
                findingMainWindow.pauseCountdown();
                findingEndGame.displayEndWindow(true, "Congratulations");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //System.out.println(curX + " " + curY);
    }
    public void setFindingMainWindow(FindingMainWindow findingMainWindow) {
        this.findingMainWindow = findingMainWindow;
    }

    public void restart() {
        if (findingMainWindow.getQuestionWindow() != null) {
            findingMainWindow.getQuestionWindow().quit();
        }
        if (findingMainWindow.getFindingHelpWindow() != null) {
            findingMainWindow.getFindingHelpWindow().quit();
        }
        if (findingMainWindow.getFindingStatsWindow() != null) {
            findingMainWindow.getFindingStatsWindow().quit();
        }
        for (int i = 0; i < map.getChildren().size(); i++) {
            StackPane sp = (StackPane) map.getChildren().get(i);
            //System.out.println((i / 8) + " " + (i % 8)  + ": " + sp.getChildren().size());
            sp.getChildren().clear();
        }
        map.getChildren().clear();
        init();
    }

    public void setChangeToStone(int changeToStone) {
        this.changeToStone = changeToStone;
    }

    public Label getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Label remainingTime) {
        this.remainingTime = remainingTime;
    }

    public FindingMainWindow getFindingMainWindow() {
        return findingMainWindow;
    }

    public void showStats() throws IOException {
        FindingStatsWindow findingStatsWindow = new FindingStatsWindow();
        findingStatsWindow.setFindingFunction(findingFunction);
        findingStatsWindow.setFindingController(this);
        findingMainWindow.setFindingStatsWindow(findingStatsWindow);
        findingMainWindow.pauseCountdown();
        findingStatsWindow.display();
        findingMainWindow.getRoot().requestFocus();
        findingMainWindow.continueCountdown();
    }

    public void showHelp() {
        FindingHelpWindow findingHelpWindow = new FindingHelpWindow();
        findingHelpWindow.setFindingController(this);
        findingMainWindow.setFindingHelpWindow(findingHelpWindow);
        findingMainWindow.pauseCountdown();
        findingHelpWindow.display();
        findingMainWindow.getRoot().requestFocus();
        findingMainWindow.continueCountdown();
    }

    public FindingFunction getFindingFunction() {
        return findingFunction;
    }

    public void setFindingFunction(FindingFunction findingFunction) {
        this.findingFunction = findingFunction;
    }
}
