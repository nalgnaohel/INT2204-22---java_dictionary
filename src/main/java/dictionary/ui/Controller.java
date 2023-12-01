package dictionary.ui;

import dictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private Button favoriteButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private AnchorPane contentArea;

    private Button currentButton;
    private AnchorPane lookupArea;
    private LookUpTabController lookupTabController;
    private AnchorPane translateArea;
    private TranslateTabController translateTabController;

    private AnchorPane gameArea;
    private ChooseGameController chooseGameController;

    private AnchorPane favoriteArea;
    private FavoriteTabController favoriteTabController;

    @FXML // Xử lý sự kiện khi di chuột vào đối tượng
    protected void onMouseEntered(MouseEvent event) {
        // Tìm đối tượng gây ra sự kiện (đối tượng được di chuột vào)
        Button b = (Button) event.getSource();
        // Kiểm tra đối tượng gây ra sự kiện có phải là Tab đang mở hay không
        if (!b.equals(currentButton)) {
            b.getParent().getStyleClass().clear();
            b.getParent().getStyleClass().add("tab-dragged");

            // Tạo đối tượng ColorAdjust, Sét đặt giá trị cho brightness (Độ sáng)
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.6);
            // Áp dụng ColorAdjust
            b.getParent().getChildrenUnmodifiable().get(0).setEffect(colorAdjust);
        }
    }

    @FXML // Xử lý sự kiện khi di chuột ra khỏi đối tượng
    protected void onMouseExited(MouseEvent event) {
        Button b = (Button) event.getSource();
        if (!b.equals(currentButton)) {
            b.getParent().getStyleClass().clear();
            b.getParent().getStyleClass().add("tab-menu");

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0);
            b.getParent().getChildrenUnmodifiable().get(0).setEffect(colorAdjust);
        }
    }

    @FXML // Xử lý sự kiện khi click vào đối tượng
    protected void ButtonClick(ActionEvent event) throws IOException {
        // Đóng tab đang mở
        if (currentButton != null) {
            currentButton.getParent().getStyleClass().clear();
            currentButton.getParent().getChildrenUnmodifiable().get(0).setEffect(null);
            currentButton.getParent().getStyleClass().add("tab-menu");
        }

        // Bật tab mới
        currentButton = (Button) event.getSource();
        currentButton.getParent().getStyleClass().clear();
        currentButton.getParent().getChildrenUnmodifiable().get(0).setEffect(null);

        if (currentButton.equals(lookupButton)) {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(lookupArea);
            currentButton.getParent().getStyleClass().add("lookUp-selected");
        } else if (currentButton.equals(translateButton)) {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(translateArea);
            currentButton.getParent().getStyleClass().add("translate-selected");
        } else if (currentButton.equals(gameButton)) {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(gameArea);
            currentButton.getParent().getStyleClass().add("game-selected");
        } else if (currentButton.equals(favoriteButton)) {
            favoriteTabController.update();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(favoriteArea);
            currentButton.getParent().getStyleClass().add("favorite-selected");
        } else if (currentButton.equals(importButton)) {
            contentArea.getChildren().clear();
            //contentArea.getChildren().add(importArea);
        } else if (currentButton.equals(exportButton)) {
            contentArea.getChildren().clear();
            //contentArea.getChildren().add(exportArea);
        }
    }

    @Override // Khởi tạo các Tab trước khi chương trình chạy
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo Tab Lookup (Tab tra từ điển)
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/lookupTab.fxml"));
            lookupArea = fxmlLoader.load();
            lookupTabController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Khởi tạo Tab Translate (Tab dịch)
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/translateTab.fxml"));
            translateArea = fxmlLoader.load();
            translateTabController = fxmlLoader.getController();
            translateTabController.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Khởi tạo Tab Game (Tab trò chơi)
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/chooseGame.fxml"));
            gameArea = fxmlLoader.load();
            chooseGameController = fxmlLoader.getController();
            chooseGameController.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Favorite Tab
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/favoriteTab.fxml"));
            favoriteArea = fxmlLoader.load();
            favoriteTabController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Khởi tạo Tab Import (Tab nhập dữ liệu)


        // Khởi tạo Tab Export (Tab xuất dữ liệu)
    }
}