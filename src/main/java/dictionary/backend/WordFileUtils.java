package dictionary.backend;

import java.io.*;
import java.util.ArrayList;

public abstract class WordFileUtils {
    public abstract void addTo(Word w);
    protected String fname;
    protected ArrayList<Word> allWords = new ArrayList<>();

    public void setFname(String fname) {
        this.fname = fname;
    }
    public void load() {
        File file = new File(fname);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create history file!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Read history file
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

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
                    allWords.add(word);
                    if (this instanceof Favorites) {
                        ((Favorites) this).getAllFav().put(engWord, meaning);
                    }
                    meaning = "";
                    engWord = line.replace("|", "");
                    engWord = engWord.trim();
                }
            }
            if (engWord != "" && meaning != "") {
                Word word = new Word(engWord, meaning);
                allWords.add(word);
                if (this instanceof Favorites) {
                    ((Favorites) this).getAllFav().put(engWord, meaning);
                }
            }
            System.out.println("Done loading history!");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find history file!");
        } catch (IOException e) {
            System.out.println("Cannot read history!");
            e.printStackTrace();
        }
    }

    public void export() {
        try {
            FileWriter fw = new FileWriter(fname);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Word word : getAllWords()) {
                bw.write("| " + word.getWordTarget() + "\n" + word.getWordMeaning());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }
    }

    public abstract ArrayList<Word> getAllWords();
}
