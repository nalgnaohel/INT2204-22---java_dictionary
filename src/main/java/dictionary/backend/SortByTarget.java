package dictionary.backend;

import java.util.Comparator;

public class SortByTarget implements Comparator<Word> {
    public int compare(Word w1, Word w2) {
        return w1.getWordTarget().compareTo(w2.getWordTarget());
    }
}
