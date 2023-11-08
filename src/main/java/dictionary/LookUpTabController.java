package dictionary;

import dictionary.backend.TxtDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LookUpTabController {
    TxtDictionary dict = new TxtDictionary();

    @FXML
    private TextField searchbox;
    @FXML
    private ListView<String> listview = new ListView<String>();

    public void initialize() {
        ArrayList<String> wordsList = new ArrayList<>();
        wordsList.add("Interest");
        wordsList.add("Interesting");
        wordsList.add("Interested");
        wordsList.add("International");
        wordsList.add("Internet");
        wordsList.add("Introduce");
        wordsList.add("Introduction");
        wordsList.add("Invent");
        wordsList.add("Invention");
        wordsList.add("Inventor");
        wordsList.add("Invest");
        wordsList.add("Investigate");
        wordsList.add("Investigation");
        wordsList.add("Investment");
        wordsList.add("Invite");
        wordsList.add("Involve");
        wordsList.add("Iron");
        ObservableList<String> items = FXCollections.observableArrayList(wordsList);
        //show listview
        listview.setItems(items);
    }
    @FXML
    public void search() {

    }
}
