package dictionary.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtDictionary extends Dictionary{
    private final ArrayList<Word> wordsList = new ArrayList<Word>();
    private final DictAPI dictAPI = new DictAPI();
    private final GoogleAPI ggAPI = new GoogleAPI();
    /**
     * Import data.
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
            int id = 0;
            System.out.println("Importing " + path + "... Please wait!");
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("|")) {
                    meaning += line.trim() + "\n";
                } else {
                    //an E word
                    if (insertWord(engWord, meaning)) {
                        id++;
                        if (id % 1000 == 10) {
                            System.out.println("*");
                        } else if (id % 10 == 0) {
                            System.out.print("*");
                        }
                    }
                    meaning = "";
                    engWord = line.replace("|", "");
                    engWord = engWord.trim();
                }
            }
            if(insertWord(engWord, meaning)) {
                id++;
            }
            System.out.println("Done importing!");
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
     * insert
     */
    @Override
    public String lookUpWord(String target) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                return word.getWordMeaning();
            }
        }
        return "Not found!";
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
    public boolean deleteWord(final String target) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                wordsList.remove(word);
                Trie.delete(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateWordMeaning(final String target, String meaning) {
        for (Word word : wordsList) {
            if (word.getWordTarget().equals(target)) {
                word.setWordMeaning(meaning);
                return true;
            }
        }
        return false;
    }

    //Get extra information from API.
    public String getInfoFromAPI(String target) {
        try {
            return dictAPI.getNeededInfo(target);
        } catch (IOException e) {
            System.out.println("Failed to use API!");
            throw new RuntimeException(e);
        }
    }

    public void playEngWordSound(String target) {
        try {
            ggAPI.playWordSound(target);
        } catch (Exception e) {
            System.out.println("Failed to use API to play sound!");
            throw new RuntimeException(e);
        }
    }
}
