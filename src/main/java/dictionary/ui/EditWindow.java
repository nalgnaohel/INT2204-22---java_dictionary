package dictionary.ui;

import dictionary.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditWindow {
    private Stage stage;
    private EditController editController;
    private MutualController controller;
    protected boolean editSuccess = false;
    public void display() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/edit.fxml"));
        AnchorPane root = fxmlLoader.load();
        editController = fxmlLoader.getController();
        boolean canEdit = true;
        if (controller instanceof FavoriteTabController) {
            canEdit = false;
        }
        editController.initialize(canEdit, controller.getCurrentWord());
        editController.setEditWindow(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public EditController getEditController() {
        return this.editController;
    }

    public MutualController getController() {
        return controller;
    }

    public void setController(MutualController controller) {
        this.controller = controller;
    }

    public void quit() {
        stage.close();
    }
}
