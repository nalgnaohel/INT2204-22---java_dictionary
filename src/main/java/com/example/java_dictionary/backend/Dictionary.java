package com.example.java_dictionary.backend;

import java.util.ArrayList;

public abstract class Dictionary {

    /**
     * Initialize the dictionary when starting the application.
     */
    //public void initialize() throws SQLException {}

    /**
     * Close.
     */
    public void close() {}

    /**
     * Get all words in the dictionary.
     */
    public abstract ArrayList<Word> getWordsList();

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
    public abstract boolean updateWordDefinition(final String target, final String definition);

}