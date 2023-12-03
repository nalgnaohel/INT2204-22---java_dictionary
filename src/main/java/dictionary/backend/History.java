package dictionary.backend;

import java.io.*;
import java.util.ArrayList;

public class History extends WordFileUtils {
    private static final int maxHisLen = 20;
    public void addTo(Word w) {
        int id = 0;
        while (id < allWords.size()) {
            if (allWords.get(id).getWordTarget().equals(w.getWordTarget())) {
                allWords.remove(id); break;
            } else {
                id++;
            }
        }
        while (allWords.size() >= maxHisLen) {
            allWords.remove(0);
        }
        allWords.add(w);
    }

    public void remove(String target) {
        for (Word word : allWords) {
            if (word.getWordTarget().equals(target)) {
                allWords.remove(word);
                break;
            }
        }
    }

    //get all history word.
    public ArrayList<Word> getAllWords() {
        return allWords;
    }

    public ArrayList<String> getWordTargetHistory() {
        ArrayList<String> res = new ArrayList<>();
        for (Word word : allWords) {
            res.add(word.getWordTarget());
        }
        return res;
    }
}
