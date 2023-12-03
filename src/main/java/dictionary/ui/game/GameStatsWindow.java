package dictionary.ui.game;

import dictionary.Main;
import dictionary.backend.game.GameFunction;
import dictionary.ui.game.finding.FindingStatsWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStatsWindow {
    private Stage stage;
    private GameStatsController gameStatsController;
    private GameFunction gameFunction;
    protected String barChartTitle;
    private VBox root;
    public void display() throws IOException {
        stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/wordleStatsfxml.fxml"));
        root = fxmlLoader.load();
        gameStatsController = fxmlLoader.getController();
        gameStatsController.initialize(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(450);
        stage.setResizable(false);
        stage.showAndWait();
        if (this instanceof FindingStatsWindow) {
            stage.setOnCloseRequest(windowEvent -> {
                ((FindingStatsWindow) this).getFindingController().getFindingMainWindow().setFindingStatsWindow(null);
            });
        }
    }

    public GameFunction getGameFunction() {
        return gameFunction;
    }

    public void setGameFunction(GameFunction gameFunction) {
        this.gameFunction = gameFunction;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void quit() {
        stage.close();
    }

    public GameStatsController getGameStatsController() {
        return gameStatsController;
    }

    public void setGameStatsController(GameStatsController gameStatsController) {
        this.gameStatsController = gameStatsController;
    }

    public String getBarChartTitle() {
        return barChartTitle;
    }
}
