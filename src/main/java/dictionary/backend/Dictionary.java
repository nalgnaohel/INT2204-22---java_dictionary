package dictionary.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {

    private History history;
    private Favorites favorites;
    public void init() throws SQLException {}

    public void initUtilsFile() {
        history = new History();
        history.setFname("src/main/resources/data/history.txt");
        history.load();
        favorites = new Favorites();
        favorites.setFname("src/main/resources/data/favorites.txt");
        favorites.load();
    }

    public void close() {}

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
                if (!Character.isLetter(c) || !Character.isLowerCase(c)) {
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

    public History getHistory() {
        return (History) history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Favorites getFavorites() {
        return favorites;
    }
}