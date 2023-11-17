package dictionary.backend;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

public class WordleFunction {
    public static final int MAX_COLUMN = 5;
    public static final int MAX_ROW = 5;

    private final String[] firstRowLetters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] secondRowLetters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] thirdRowLetters = {"↵", "Z", "X", "C", "V", "B", "N", "M", "←"};

    public void initKeyboard(FlowPane virtualKeyboard) {
        for (int i = 0; i < firstRowLetters.length; i++) {
            Label cur = new Label();
            cur.setText(firstRowLetters[i]);
            virtualKeyboard.getChildren().add(cur);
        }
        for (int i = 0; i < secondRowLetters.length; i++) {
            Label cur = new Label();
            cur.setText(secondRowLetters[i]);
            virtualKeyboard.getChildren().add(cur);
        }
        for (int i = 0; i < thirdRowLetters.length; i++) {
            Label cur = new Label();
            cur.setText(thirdRowLetters[i]);
            virtualKeyboard.getChildren().add(cur);
        }
    }
}
