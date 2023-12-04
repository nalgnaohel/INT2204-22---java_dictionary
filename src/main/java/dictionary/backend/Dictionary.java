package dictionary.backend;

import dictionary.backend.api.DictAPI;
import dictionary.backend.api.GoogleAPI;
import dictionary.backend.api.TranslatorAPI;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {

    protected int numOfInsertedWords = 0;
    protected History history;
    protected Favorites favorites;
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

    public abstract String lookUpWord(String target);

    public abstract boolean insertWord(String target, String definition);

    public abstract boolean deleteWord(String target);

    public abstract boolean updateWordMeaning(String target, String meaning);

    public abstract ArrayList<String> getQuestion(int id);

    public String getInfoFromAPI(String target) {
        DictAPI api = new DictAPI();
        try {
            api.work(target, "", "");
            return api.getRes();
        } catch (IOException e) {
            System.out.println("Failed to use API!");
            throw new RuntimeException(e);
        }
    }

    public void playTextSound(String target, String lang) {
        GoogleAPI api = new GoogleAPI();
        try {
            api.work(target, lang, "");
        } catch (Exception e) {
            System.out.println("Failed to use API to play sound!");
            throw new RuntimeException(e);
        }
    }

    public String translateSentence(String langFrom, String langTo, String text) {
        //Tieng Anh thi String la "en", tieng Viet thi String la "vi".
        TranslatorAPI api = new TranslatorAPI();
        try {
            api.work(text, langFrom, langTo);
            return api.getRes();
        } catch (Exception e) {
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

    public void importDataFromFile(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String engWord = br.readLine();
            engWord = engWord.replace("|", "");
            engWord = engWord.trim();
            String line;
            String meaning = "";
            int id = 0;
            System.out.println("Importing " + path + "... Please wait!");
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("|")) {
                    meaning += line.trim() + "\n";
                } else {
                    //an E word
                    if (insertWord(engWord, meaning)) {
                        id++;
                        if (id % 1000 == 10) {
                            System.out.println("*");
                        } else if (id % 10 == 0) {
                            System.out.print("*");
                        }
                    }
                    meaning = "";
                    engWord = line.replace("|", "");
                    engWord = engWord.trim();
                }
            }
            if(insertWord(engWord, meaning)) {
                id++;
            }
            numOfInsertedWords = id;
            System.out.println("Done importing!");
        } catch (IOException e) {
            System.out.println("Cannot find the file!\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public int getNumOfInsertedWords() {
        return numOfInsertedWords;
    }

    public History getHistory() {
        return (History) history;
    }

    public Favorites getFavorites() {
        return favorites;
    }
}