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

import static dictionary.Main.dict;

public class FindingController {
    @FXML
    private TilePane map;
    @FXML
    private Label remainingTime;

    private FindingMainWindow findingMainWindow;
    private FindingFunction findingFunction = new FindingFunction();
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, -1, 0, 1};
    private int curX;
    private int curY;
    private int changeToStone;
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
            }
        }
        initMap();
        curX = 0;
        curY = 0;
        FindingEndGame.setQuit();
        FindingEndGame.setRestart();
        findingMainWindow.startCountdown();
        changeToStone = 0; //-1 if wrong ans.
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
                stackPane.getChildren().clear();
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
        System.out.println();
        System.out.println(event.getCode());
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
            ArrayList<String> quest = dict.getQuestion(2);
            try {
                QuestionWindow questionWindow = new QuestionWindow();
                questionWindow.setFindingController(this);
                questionWindow.display(quest);

                StackPane stackPane = (StackPane) map.getChildren().get(destY * 8 + destX);
                stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                if (changeToStone == 1) {
                    mapIds[destY][destX] = 2;
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
                    moveToNewTile(destX, destY);
                    changeToStone = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mapIds[destY][destX] == 3) {
            //win
            try {
                FindingEndGame.setFindingController(this);
                findingMainWindow.pauseCountdown();
                FindingEndGame.displayEndWindow(true, "Congratulations");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            if (FindingEndGame.isRestart()) {
//                restart();
//            }
//            if (FindingEndGame.isQuit()) {
//                findingMainWindow.quit();
//            }
        }
        //System.out.println(curX + " " + curY);
    }
    public void setFindingMainWindow(FindingMainWindow findingMainWindow) {
        this.findingMainWindow = findingMainWindow;
    }

    public void restart() {
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

    //    private boolean checkWin() {
//
//    }
}
