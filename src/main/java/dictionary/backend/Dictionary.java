package dictionary.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {

    public void init() throws SQLException {}

    public void close() {}

    /**
     * Get all words in the dictionary.
     */
    public abstract ArrayList<Word> getAllWords();

    public abstract String lookUpWord(final String target);

    public abstract boolean insertWord(final String target, final String definition);

    public abstract boolean deleteWord(final String target);

    public abstract boolean updateWordMeaning(final String target, final String meaning);

    public void importDataFromFile(String path) {};
    public abstract ArrayList<String> getQuestion(int id);

    public String getInfoFromAPI(String target) {
        API api = new DictAPI();
        try {
            return api.getNeededInfo(target);
        } catch (IOException e) {
            System.out.println("Failed to use API!");
            throw new RuntimeException(e);
        }
    }

    public void playEngWordSound(String target) {
        API api = new GoogleAPI();
        try {
            api.playWordSound(target);
        } catch (Exception e) {
            System.out.println("Failed to use API to play sound!");
            throw new RuntimeException(e);
        }
    }

    public String translateSentence(String langFrom, String langTo, String text) {
        //Tieng Anh thi String la "en", tieng Viet thi String la "vi".
        API api = new TranslatorAPI();
        try {
            return api.translate(langFrom, langTo, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Cannot translate your text!";
    }

    public void export5Words() {
        ArrayList<Word> wordArrayList = getAllWords();
        ArrayList<String> wordleWords = new ArrayList<>();
        for (Word word : wordArrayList) {
            boolean ok = true;
            if (word.getWordTarget().length() != 5) {
                ok = false; continue;
            }
            for (int i = 0; i < 5; i++) {
                char c = word.getWordTarget().charAt(i);
                if (!Character.isLetter(c)) {
                    ok = false; break;
                }
            }
            if (ok) {
                wordleWords.add(word.getWordTarget());
            }
        }
        try {
            FileWriter fw = new FileWriter("src/main/resources/data/wordle_all.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (String word: wordleWords) {
                bw.write( word);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }
    }
}