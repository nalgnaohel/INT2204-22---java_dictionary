package dictionary.backend;

import java.io.*;
import java.util.ArrayList;

public class History {
    private static final int maxHisLen = 20;
    private static final ArrayList<Word> wordHistory = new ArrayList<>();
    public static void addToHistory(Word w) {
        int id = 0;
        while (id < wordHistory.size()) {
            if (wordHistory.get(id).getWordTarget().equals(w.getWordTarget())) {
                wordHistory.remove(id);
            } else {
                id++;
            }
        }
        if (wordHistory.size() == maxHisLen) {
            wordHistory.remove(0);
        }
        wordHistory.add(w);
    }

    public static void loadHistory() {
        File file = new File("src/main/resources/data/history.txt");
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create history file!");
                }
            } catch (Exception e) {
                //System.out.println("Failed to create history file!");
                e.printStackTrace();
            }
        }

        //Read history file
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            //a word is started with '('.
            String line = br.readLine();
            String engWord = "";
            String meaning = "";
            if (line != null) {
                engWord = line.replace("|", "").trim();
                meaning = "";
            }
            System.out.println("Loading history... Please wait!");
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("|")) {
                    meaning += line.trim() + "\n";
                } else {
                    //an E word
                    Word word = new Word(engWord, meaning);
                    wordHistory.add(word);
                    meaning = "";
                    engWord = line.replace("|", "");
                    engWord = engWord.trim();
                }
            }
            if (engWord != "" && meaning != "") {
                Word word = new Word(engWord, meaning);
                wordHistory.add(word);
            }
            System.out.println("Done loading history!");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find history file!");
        } catch (IOException e) {
            System.out.println("Cannot read history!");
            e.printStackTrace();
        }
    }

    //update history
    public static void exportHistory() {
        try {
            FileWriter fw = new FileWriter("src/main/resources/data/history.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Word word: wordHistory) {
                bw.write("| " + word.getWordTarget() + "\n" + word.getWordMeaning());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }
    }

    //get all history word.
    public static ArrayList<Word> getWordHistory() {
        return wordHistory;
    }

    public static ArrayList<String> getWordTargetHistory() {
        ArrayList<String> res = new ArrayList<>();
        for (Word word : wordHistory) {
            res.add(word.getWordTarget());
        }
        return res;
    }
}
