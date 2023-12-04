package dictionary.ui.game.finding;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class QuestionWindow {
    private Stage stage;
    private QuestionController questionController;
    private FindingController findingController;
    public void display(final ArrayList<String> question) throws IOException {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(findingController.getFindingMainWindow().getGameStage());
        stage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/question.fxml"));
        Parent root = fxmlLoader.load();
        questionController = fxmlLoader.getController();
        questionController.setQuestionWindow(this);
        questionController.init(question);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
//        stage.setX(stage.getOwner().getX());
//        stage.setY(stage.getOwner().getY() + stage.getOwner().getHeight() - 450);
//        stage.setHeight(450);
//        stage.setWidth(800);
        stage.setResizable(false);
        stage.showAndWait();
        stage.setOnCloseRequest(windowEvent -> {
            findingController.getFindingMainWindow().setQuestionWindow(null);
        });
    }

    public void setFindingController(FindingController findingController) {
        this.findingController = findingController;
    }

    public FindingController getFindingController() {
        return this.findingController;
    }

    public void quit() {
        stage.close();
    }
}
