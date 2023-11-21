package dictionary.backend;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameFunction {
    private int gamesPlayed;
    private int gamesWon;
    private int currentStreak;
    private int longestStreak;
    private int thisGameGuesses = 0;

    private final Map<Integer, Integer> numOfGuess = new HashMap<>();

    private String historyPath;

    public void init() {
        try {
            File file = new File(historyPath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int id = 0;
            while ((line = br.readLine()) != null) {
                if (line.equals("\n") || line.equals("")) {
                    continue;
                }
                String temp = line.replace("\n", "");
                int val = Integer.valueOf(temp);
                if (id <= 3) {
                    switch (id) {
                        case 0: gamesPlayed = val;
                        case 1: gamesWon = val;
                        case 2: currentStreak = val;
                        case 3: longestStreak = val; System.out.println(longestStreak);
                    }
                } else if (id >= 4 && id < 9) {
                    numOfGuess.put(id - 3, val);
                } else {
                    thisGameGuesses = val;
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            FileWriter fw = new FileWriter("src/main/resources/data/wordle_history.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(gamesPlayed));
            bw.newLine();
            bw.write(Integer.toString(gamesWon));
            bw.newLine();
            bw.write(Integer.toString(currentStreak));
            bw.newLine();
            bw.write(Integer.toString(longestStreak));
            bw.newLine();
            for (int i = 1; i <= 5; i++) {
                bw.write(Integer.toString(numOfGuess.get(i)));
                bw.newLine();
            }
            bw.write(Integer.toString(thisGameGuesses));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Errors occured while trying to export to file!");
            e.printStackTrace();
        }
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getGamesWon() {
        return this.gamesWon;
    }

    public int getCurrentStreak() {
        return this.currentStreak;
    }

    public int getLongestStreak() {
        //System.out.println(longestStreak);
        return this.longestStreak;
    }

    public int getThisGameGuesses() {
        return thisGameGuesses;
    }

    public Map<Integer, Integer> getNumOfGuess() {
        return numOfGuess;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public double getWinRate() {
        if (gamesPlayed > 0) {
            return ((double) gamesWon / gamesPlayed) * 100;
        }
        return 0;
    }

    public String getHistoryPath() {
        return historyPath;
    }

    public void setHistoryPath(String historyPath) {
        this.historyPath = historyPath;
    }
}
