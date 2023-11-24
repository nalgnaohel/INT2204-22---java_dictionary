package dictionary.ui;

import dictionary.FindingMainWindow;
import dictionary.Main;
import dictionary.backend.FindingFunction;
import dictionary.backend.WordleFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FindingStatsWindow {
    private Stage stage;
    private FindingStatsController findingStatsController;
    private FindingFunction findingFunction;
    public void display() throws IOException {
        stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/findingStats.fxml"));
        Parent root = fxmlLoader.load();
        findingStatsController = fxmlLoader.getController();
        findingStatsController.setFindingStatsWindow(this);
        findingStatsController.init();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(450);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void quit() {
        stage.close();
    }

//    public FindingMainWindow getFindingMainWindow() {
//        return findingMainWindow;
//    }
//
//    public void setFindingMainWindow(FindingMainWindow findingMainWindow) {
//        this.findingMainWindow = findingMainWindow;
//    }

    public FindingFunction getFindingFunction() {
        return findingFunction;
    }

    public void setFindingFunction(FindingFunction findingFunction) {
        this.findingFunction = findingFunction;
    }
}
