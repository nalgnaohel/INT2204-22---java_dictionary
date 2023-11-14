package dictionary.backend;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {

    /**
     * Initialize the dictionary when starting the application.
     */
    public void init() throws SQLException {}

    /**
     * Close.
     */
    public void close() {}

    /**
     * Get all words in the dictionary.
     */
    public abstract ArrayList<Word> getAllWords();

    /**
     * Lookup word.
     */
    public abstract String lookUpWord(final String target);

    /**
     * Insert.
     */
    public abstract boolean insertWord(final String target, final String definition);

    /**
     * Delete.
     */
    public abstract boolean deleteWord(final String target);

    /**
     * Update Vietnamese meaning.
     */
    public abstract boolean updateWordMeaning(final String target, final String meaning);

    public void importDataFromFile(String path) {}

    public abstract String getInfoFromAPI(String target);
    public abstract void playEngWordSound(String target);
    public abstract String translateSentence(String langFrom, String langTo, String text);
}