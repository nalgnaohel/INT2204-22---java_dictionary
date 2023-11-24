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
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, target);
            try  {
                ResultSet rs = prs.executeQuery();
                try {
                    if (rs.next()) {
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

    public ArrayList<String> getQuestion(int id) {
        final String sqlQuery = "SELECT * FROM Multiplechoices WHERE ID = ?";
        try {
            PreparedStatement prs = connection.prepareStatement(sqlQuery);
            prs.setString(1, Integer.toString(id));
            System.out.println(Integer.toString(id));
            try {
                ResultSet rs = prs.executeQuery();
                try {
                    ArrayList<String> res = new ArrayList<>();
                    if (rs.next()) {
                        for (int i = 1; i <= 8; i++) {
                            //System.out.println(rs.getString(i));
                            res.add(rs.getString(i));
                        }
                        return res;
                    } else {
                        System.out.println("Cannot find the question");
                        return new ArrayList<>();
                    }
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
}
