package dictionary.backend;

import java.io.*;
import java.util.ArrayList;

import static dictionary.Main.dict;

public class TxtDictionary extends Dictionary{
    private final ArrayList<Word> wordsList = new ArrayList<Word>();

    /**
     * Export words.
     */
    public void exportToFiles(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Word word: wordsList) {
                bw.write("| " + word.getWordTarget() + "\n" + word.getWordMeaning());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }

    }

    /**
     * Return all the words in our list.
     */
    public ArrayList<Word> getAllWords() {
        return wordsList;
    }

    /**
     * insert
     */
    @Override
    public String lookUpWord(String target) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                dict.getHistory().addTo(word);
                return word.getWordMeaning();
            }
        }
        return "Not found!\n";
    }

    @Override
    public boolean insertWord(final String target, String meaning) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                return false;
            }
        }
        Word w = new Word(target, meaning);
        wordsList.add(w);
        Trie.insert(target);
        return true;
    }

    @Override
    public boolean deleteWord(String target) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                wordsList.remove(word);
                Trie.delete(target);
                history.remove(target);
                favorites.remove(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateWordMeaning(String target, String meaning) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                word.setWordMeaning(meaning);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getQuestion(int id) {
        return new ArrayList<>();
    }
}
