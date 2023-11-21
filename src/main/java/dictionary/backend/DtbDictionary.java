package dictionary.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DtbDictionary extends Dictionary{
    private static final String dtbName = "Dictionary";
    private static final String dtbTable = "DictWord";
    private static final String hostName = "localhost";
    private static final String port = "3307";
    private static final String userName = "root";
    private static final String password = ""; //No password as default.
    private static final String dtbURL = "jdbc:mysql://"
            + hostName + ":" + port + "/" + dtbName;

    private static Connection connection = null;

    private final DictAPI dictAPI = new DictAPI();
    private final GoogleAPI ggAPI = new GoogleAPI();
    private final TranslatorAPI translatorAPI = new TranslatorAPI();

    //Connect to dtb + Dealing with SQLException
    public void connectToDtb() throws SQLException {
        try {
            System.out.println("Connecting to the dtb...");
            connection = DriverManager.getConnection(dtbURL, userName, password);
            System.out.println("Succesfully connect to the dtb");
        } catch (SQLException e) {
            throw new SQLException("SQLException - Cannot connect to the dtb");
        }
    }

    /**
     * Initialize dtb.
     */
    public void init() throws SQLException {
        connectToDtb();
        ArrayList<Word> wordArrayList = getAllWords();
        for (Word word : wordArrayList) {
            String wordTarget = word.getWordTarget();
            Trie.insert(wordTarget);
        }
    }

    //close Connection to our dtb.
    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Dtb Disconnected!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close disconnection!");
            e.printStackTrace();
        }
    }

    //get all words in our SQL.
    @Override
    public ArrayList<Word> getAllWords() {
        final String sqlQuery = "SELECT * FROM " + dtbTable;
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            try {
                ResultSet rs = prs.executeQuery();
                try {
                    ArrayList<Word> wordArrayList = new ArrayList<>();
                    //System.out.println("Inserting words...");
                    while (rs.next()) {
                        //System.out.println(rs.getString(1));
                        Word word = new Word(rs.getString(1), rs.getString(2));
                        wordArrayList.add(word);
                    }
                    //System.out.println("Done inserting words...");
                    return wordArrayList;
                } finally {
                    rs.close();
                }
            } finally {
                prs.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem occured while getting data!");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String lookUpWord(final String target) {
        final String sqlQuery = "SELECT Vietnamese FROM " + dtbTable + " WHERE English = ?";
        //System.out.println("Currently looking up for " + target);
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, target);
            try  {
                ResultSet rs = prs.executeQuery();
                try {
                    if (rs.next()) {
                        //System.out.println("Find the word!");
                        //System.out.println(rs.getString("Vietnamese"));
                        Word word = new Word(target, rs.getString("Vietnamese"));
                        History.addToHistory(word);
                        return rs.getString("Vietnamese");
                    } else {
                        System.out.println("Cannot find the word");
                        return "Not found!";
                    }
                } finally {
                    rs.close();
                }
            } finally {
                prs.close();
            }
        } catch (SQLException e) {
            System.out.println("Cannot find the word!");
            e.printStackTrace();
        }
        return "Not found!";
    }

    /**
     * Insert.
     */
    public boolean insertWord(final String target, String meaning) {
        final String sqlQuery = "INSERT INTO " + dtbTable + " (English, Vietnamese) VALUES (?, ?)";
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, target);
            prs.setString(2, meaning);
            try {
                int inserted = prs.executeUpdate();
                if (inserted < 0) {
                    return false;
                }
            } finally {
                prs.close();
            }
            Trie.insert(target);
            return true;
        } catch (SQLException e){
            System.out.println("Cannot insert!");
            e.printStackTrace();
        }
        return false;
    }

    //delete

    @Override
    public boolean deleteWord(final String target) {
        final String sqlQuery = "DELETE FROM " + dtbTable + " WHERE English = ?";
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, target);
            try {
                int deleted = prs.executeUpdate();
                if (deleted == 0) {
                    return false;
                }
            } finally {
                prs.close();
            }
            Trie.delete(target);
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot delete the word");
            e.printStackTrace();
        }
        return false;
    }

    //update.

    @Override
    public boolean updateWordMeaning(final String target, String meaning) {
        final String sqlQuery = "UPDATE " + dtbTable + " SET Vietnamese = ? WHERE English = ?";
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, meaning);
            prs.setString(2, target);
            try {
                int updated = prs.executeUpdate();
                if (updated == 0) {
                    return false;
                }
            } finally {
                prs.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to update the word meaning");
            return false;
        }
    }

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

    //cai nay phuc vu wordle thoi, k can quan tam
    public void export5Words() {
        ArrayList<Word> wordArrayList = getAllWords();
        ArrayList<String> wordleWords = new ArrayList<>();
        for (Word word : wordArrayList) {
            boolean ok = true;
            if (word.getWordTarget().length() != 5) {
                ok = false; continue;
            }
            for (int i = 0; i < 5; i++) {
                char c = word.getWordTarget().charAt(i);
                if (!Character.isLetter(c)) {
                    ok = false; break;
                }
            }
            if (ok) {
                wordleWords.add(word.getWordTarget());
            }
        }
        try {
            FileWriter fw = new FileWriter("src/main/resources/data/wordle_all.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (String word: wordleWords) {
                bw.write( word);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }
    }
}
