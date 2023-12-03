package dictionary.backend;

import java.util.Comparator;

public class Word {
    private final String wordTarget;
    private String wordMeaning;
    private boolean inHistory;

    /**
     * Constructor new Word.
     */
    public Word(String wordTarget, String wordMeaning) {
        this.wordTarget = wordTarget;
        this.wordMeaning = wordMeaning;
    }

    /**
     * Get English word.
     */
    public String getWordTarget() {
        return wordTarget;
    }

    /**
     * Get word meaning.
     */
    public String getWordMeaning() {
        return wordMeaning;
    }

    /**
     * Set word meaning.
     */
    public void setWordMeaning(String wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    /**
     * Check the content of Word.
     */
    public String toString() {
        String res = "Word:{ WordTarget = " + this.wordTarget + ", ";
        res += "WordMeaning = " + this.wordMeaning + "\n";
        return res;
    }

    public boolean isInHistory() {
        return inHistory;
    }

    public void setInHistory(boolean inHistory) {
        this.inHistory = inHistory;
    }
}