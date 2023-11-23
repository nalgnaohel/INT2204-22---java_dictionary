package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class QuestionWindow {
    private Stage stage;
    private QuestionController questionController;
    private FindingController findingController;
    public void display(final ArrayList<String> question) throws IOException {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/question.fxml"));
        Parent root = fxmlLoader.load();
        questionController = fxmlLoader.getController();
        questionController.setQuestionWindow(this);
        questionController.init(question);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(420);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.showAndWait();
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
