package dictionary.backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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

    public ArrayList<Word> getAllWords() {
        return wordsList;
    }

    @Override
    public String lookUpWord(String target) {
        Collections.sort(wordsList, new SortByTarget());
        int l = 0;
        int r = wordsList.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (wordsList.get(m).getWordTarget().equals(target)) {
                return wordsList.get(m).getWordMeaning();
            }
            if (wordsList.get(m).getWordTarget().compareTo(target) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
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
