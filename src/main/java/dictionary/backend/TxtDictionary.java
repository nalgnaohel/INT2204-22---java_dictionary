package dictionary.backend;

import java.io.*;
import java.util.ArrayList;

public class TxtDictionary extends Dictionary{
    private final ArrayList<Word> wordsList = new ArrayList<Word>();
    private final DictAPI dictAPI = new DictAPI();
    private final GoogleAPI ggAPI = new GoogleAPI();
    private final TranslatorAPI translatorAPI = new TranslatorAPI();
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
            throw new RuntimeException(e);
        }

    }

    /**
     * Export to several files to add sql.
     */
    public void exportToSQL(int numOfFiles) {
        int noWordsPerFile = wordsList.size() / numOfFiles;
        for (int i = 1; i < numOfFiles; i++) {
            String fname = "toSql_" + Integer.toString(i) + ".txt";
            try {
                FileWriter fw = new FileWriter(fname);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("INSERT INTO DictWord\nVALUES\n");
                for (int j = (i - 1) * noWordsPerFile; j < i * noWordsPerFile; j++) {
                    String wordToSQLValue = "(\"" + wordsList.get(j).getWordTarget() + "\",";
                    wordToSQLValue += "\"" + wordsList.get(j).getWordMeaning() + "\"),";
                    bw.write(wordToSQLValue);
                }
                bw.close();
            } catch (IOException e) {
                System.out.println("Errors occured while trying to export " + fname + "!\n");
                throw new RuntimeException(e);
            }
        }
        //The last file.
        String fname = "toSql_" + Integer.toString(numOfFiles) + ".txt";
        try {
            FileWriter fw = new FileWriter(fname);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("INSERT INTO DictWord\nVALUES\n");
            for (int j = (numOfFiles - 1) * noWordsPerFile; j < wordsList.size(); j++) {
                String wordToSQLValue = "(\"" + wordsList.get(j).getWordTarget() + "\",";
                wordToSQLValue += "\"" + wordsList.get(j).getWordMeaning() + "\"),";
                bw.write(wordToSQLValue);
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export " + fname + "!\n");
            throw new RuntimeException(e);
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

    public String translateSentence(String langFrom, String langTo, String text) {
        //Tieng Anh thi String la "en", tieng Viet thi String la "vi".
        try {
            return translatorAPI.translate(langFrom, langTo, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Cannot translate your text!";
    }
}
