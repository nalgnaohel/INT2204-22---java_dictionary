package dictionary.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;

public class QuestionController {
    private QuestionWindow questionWindow;

    @FXML
    private TextArea questArea;
    @FXML
    private Button A;
    @FXML
    private Button B;
    @FXML
    private Button C;
    @FXML
    private Button D;
    private ArrayList<String> questionData = new ArrayList<>();

    public void init(ArrayList<String> question) {
        questionData = question;
        questArea.setText(question.get(1));
        questArea.setEditable(false);
        A.setText("A."+question.get(2));
        B.setText("B."+question.get(3));
        C.setText("C."+question.get(4));
        D.setText("D."+question.get(5));
    }

    public void answerClicked(ActionEvent event) {
        Button curClicked = (Button) event.getSource();
        String clickedAnswer = String.valueOf(curClicked.getText().charAt(0));
        if (clickedAnswer.equals(questionData.get(6))) {
            System.out.println("Correct");
            questionWindow.getFindingController().setChangeToStone(-1);
            questionWindow.quit();
        } else {
            System.out.println("Wrong!");
            questionWindow.getFindingController().setChangeToStone(1);
            questionWindow.quit();
        }
    }

    public void setQuestionWindow(QuestionWindow questionWindow) {
        this.questionWindow = questionWindow;
    }
}
