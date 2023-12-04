package dictionary.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static dictionary.Main.dict;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateTabController implements Initializable {
    @FXML
    private TextArea fromLangText;
    @FXML
    private TextFlow toLangText;
    @FXML
    private Button InputListen;
    @FXML
    private Button OutputListen;
    @FXML
    private Button Copy;
    @FXML
    private Button Paste;
    @FXML
    private Button OpenFile;

    ObservableList<String> langFromList = FXCollections.observableArrayList("English to Vietnamese",
            "Vietnamese to English");

    @FXML
    private ChoiceBox chooseLang;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLang.setItems(langFromList);
        chooseLang.setValue("English to Vietnamese");
    }

    public void update() {
        fromLangText.clear();
        toLangText.getChildren().clear();
    }

    public void translate(ActionEvent event) {
        String apiFrom = "en";
        String apiTo = "vi";
        if (chooseLang.getValue() == "Vietnamese to English") {
            apiFrom = "vi";
            apiTo = "en";
        }
        String target = fromLangText.getText();
        String meaningS = dict.translateSentence(apiFrom, apiTo, target);
        Text meaning = new Text(meaningS);
        toLangText.getChildren().clear();
        toLangText.getChildren().add(meaning);
    }

    public String getOutputText() {
        StringBuilder sb = new StringBuilder();
        for (Node node : toLangText.getChildren()) {
            if (node instanceof Text) {
                sb.append(((Text) node).getText());
            }
        }
        String fullText = sb.toString();
        return fullText;
    }

    @FXML
    public void listen (ActionEvent event) {
        String[] f = chooseLang.getValue().toString().split(" ");
        if (event.getSource() == InputListen) {
            String lang = f[0].substring(0, 2).toLowerCase();
            dict.playTextSound(fromLangText.getText(), lang);
        }
        else if (event.getSource() == OutputListen) {
            String lang = f[2].substring(0, 2).toLowerCase();
            dict.playTextSound(getOutputText(), lang);
        }
    }

    @FXML
    private void CopytoClipboard(ActionEvent event) {
        String myString = getOutputText();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    private void PasteFromClipboard(ActionEvent event) {
        String myString = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            myString = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        fromLangText.setText(myString);
    }

    // Mở file text
    @FXML
    private void OpenFile(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter Filter = new FileChooser.ExtensionFilter("Text file", "*.txt");
        fc.getExtensionFilters().add(Filter);
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String result = br.readLine();
            fromLangText.clear();
            while (result != null) {
                fromLangText.appendText(result + "\n");
                result = br.readLine();
            }
        }
    }

}
