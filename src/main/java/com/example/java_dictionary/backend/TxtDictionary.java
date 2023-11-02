package com.example.java_dictionary.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtDictionary {
    private ArrayList<Word> wordsList = new ArrayList<Word>();

    /**
     * Import data
     * @param path - file path
     */
    public void importDataFromFile(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String engWord = br.readLine();
            engWord = engWord.replace("|", "");
            engWord = engWord.trim();
            String line;
            String meaning = "";
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("|")) {
                    meaning += line.trim() + "\n";
                } else {
                    //an E word
                    Word word = new Word(engWord, meaning);
                    wordsList.add(word);
                    meaning = "";
                    engWord = line.replace("|", "");
                    engWord = engWord.trim();
                }
            }
            Word word = new Word(engWord, meaning);
            wordsList.add(word);
        } catch (IOException e) {
            System.out.println("Cannot find the file!\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    /**
     * Export words.
     */

    /**
     * Return all the words in our list.
     */
    public ArrayList<Word> getWordsList() {
        return wordsList;
    }

    /**
     *
     */
}
