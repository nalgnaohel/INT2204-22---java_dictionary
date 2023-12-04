package dictionary.ui.game.finding;

import dictionary.Main;
import dictionary.backend.game.FindingFunction;
import dictionary.ui.game.GameController;
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
import java.util.concurrent.ThreadLocalRandom;

import static dictionary.Main.dict;

public class FindingController extends GameController {
    @FXML
    private TilePane map;
    @FXML
    private Label remainingTime;
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

    public FindingController() {
        this.gameFunction = new FindingFunction();
    }
    public void init() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mapIds[i][j] = levelmaps[i][j];
            }
        }
        curX = 0; curY = 0;
        gameMainWindow.startCountdown();
        gameFunction.setHistoryPath("src/main/resources/data/finding_history.txt");
        gameFunction.init();
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

                stackPane.getChildren().add(createTile("icon/grass.png"));
                if (i == 0 && j == 0) {
                    stackPane.getChildren().add(createTile("icon/character.png"));
                }
                if (mapIds[i][j] == 2) {
                    stackPane.getChildren().add(createTile("icon/stone.png"));
                } else if (mapIds[i][j] == 1) {
                    stackPane.getChildren().add(createTile("icon/question.gif"));
                } else if (mapIds[i][j] == 3) {
                    stackPane.getChildren().add(createTile("icon/treasure.png"));
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
        if (destX < 0 || destX >= 8 || destY < 0 || destY >= 8) {
            return;
        }
        if (mapIds[destY][destX] == 0) {
            moveToNewTile(destX, destY);
        } else if (mapIds[destY][destX] == 1) {
            int idQuest = ThreadLocalRandom.current().nextInt(0, availableQuestId.size());
            ArrayList<String> quest = dict.getQuestion(availableQuestId.get(idQuest));
            availableQuestId.remove(idQuest);
            try {
                QuestionWindow questionWindow = new QuestionWindow();
                questionWindow.setFindingController(this);
                ((FindingMainWindow)gameMainWindow).setQuestionWindow(questionWindow);
                questionWindow.display(quest);
                numofQuest++;
                if (changeToStone == 1) {
                    mapIds[destY][destX] = 2;
                    StackPane stackPane = (StackPane) map.getChildren().get(destY * 8 + destX);
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                    stackPane.getChildren().add(createTile("icon/stone.png"));
                    changeToStone = 0;
                } else if (changeToStone == -1) {
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
            gameFunction.updateWinCase(numofQuest);
            gameFunction.updateToFiles();
            try {
                FindingEndGame findingEndGame = new FindingEndGame();
                findingEndGame.setFindingController(this);
                gameMainWindow.pauseCountdown();
                findingEndGame.displayEndWindow(true, "Congratulations");
                findingEndGame.setColor("green");
                gameMainWindow.setEffect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void restart() {
        if (((FindingMainWindow)gameMainWindow).getQuestionWindow() != null) {
            ((FindingMainWindow)gameMainWindow).getQuestionWindow().quit();
        }
        if (((FindingMainWindow) gameMainWindow).getFindingHelpWindow() != null) {
            ((FindingMainWindow) gameMainWindow).getFindingHelpWindow().quit();
        }
        if (((FindingMainWindow) gameMainWindow).getFindingStatsWindow() != null) {
            ((FindingMainWindow) gameMainWindow).getFindingStatsWindow().quit();
        }
        for (int i = 0; i < map.getChildren().size(); i++) {
            StackPane sp = (StackPane) map.getChildren().get(i);
            sp.getChildren().clear();
        }
        map.getChildren().clear();
        gameMainWindow.removeEffect();
        init();
    }

    public ImageView createTile(String fname) {
        InputStream inputStream = Main.class.getResourceAsStream(fname);
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        return imageView;
    }
    public void setChangeToStone(int changeToStone) {
        this.changeToStone = changeToStone;
    }

    public Label getRemainingTime() {
        return remainingTime;
    }

    public FindingMainWindow getFindingMainWindow() {
        return (FindingMainWindow) gameMainWindow;
    }
}
