package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button lookupButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    private Button currentButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void onMouseEntered(MouseEvent event) {
        // Xử lý sự kiện khi di chuột vào đối tượng
        Button b = (Button) event.getSource();
        if (!b.equals(currentButton)) {
            b.getParent().getStyleClass().clear();
            b.getParent().getStyleClass().add("tab-dragged");

            // Tạo đối tượng ColorAdjust, Sét đặt giá trị cho brightness (Độ sáng)
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.39);
            // Áp dụng ColorAdjust
            b.getParent().getChildrenUnmodifiable().get(0).setEffect(colorAdjust);
            b.getParent().getChildrenUnmodifiable().get(1).setEffect(colorAdjust);
        }
    }

    @FXML
    protected void onMouseExited(MouseEvent event) {
        Button b = (Button) event.getSource();
        if (!b.equals(currentButton)) {
            b.getParent().getStyleClass().clear();
            b.getParent().getStyleClass().add("tab-menu");

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0);
            b.getParent().getChildrenUnmodifiable().get(0).setEffect(colorAdjust);
            b.getParent().getChildrenUnmodifiable().get(1).setEffect(colorAdjust);
        }
    }

    @FXML
    protected void ButtonClick(ActionEvent event) {
        if (currentButton != null) {
            currentButton.getParent().getStyleClass().clear();
            currentButton.getParent().getChildrenUnmodifiable().get(0).setEffect(null);
            currentButton.getParent().getChildrenUnmodifiable().get(1).setEffect(null);
            currentButton.getParent().getStyleClass().add("tab-menu");
        }

        currentButton = (Button) event.getSource();
        currentButton.getParent().getStyleClass().clear();
        currentButton.getParent().getChildrenUnmodifiable().get(0).setEffect(null);
        currentButton.getParent().getChildrenUnmodifiable().get(1).setEffect(null);
        currentButton.getParent().getStyleClass().add("tab-selected");
    }




    // error ?? idk
//    @FXML
//    protected void ButtonDragIn(ActionEvent event) {
//        Button b = (Button) event.getSource();
//        b.getParent().getStyleClass().clear();
//        b.getParent().getStyleClass().add("tab-dragged");
//    }
//
//    @FXML
//    protected void ButtonDragOut(ActionEvent event) {
//        Button b = (Button) event.getSource();
//        b.getParent().getStyleClass().clear();
//        b.getParent().getStyleClass().remove("tab-menu");
    }

//    @FXML
//    private Button translateTab;
//
//    @FXML
//    private Button gameTab;
//
//    @FXML
//    private Button importTab;
//
//    @FXML
//    private Button exportTab;

//    @FXML
//    private AnchorPane ClientArea;
//
//    private AnchorPane LookUpTabArea;

//    private LookUpTabController lookupTabController;

//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LookUpTab.fxml"));
//            LookUpTabArea = fxmlLoader.load();
//            lookupTabController = fxmlLoader.getController();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void handleAction(ActionEvent event) {
//        if (event.getSource() == lookupTab) {
//            this.ClientArea.getChildren().setAll(LookUpTabArea);
//        }
//    }