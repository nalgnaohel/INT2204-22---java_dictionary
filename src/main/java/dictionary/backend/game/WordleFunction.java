package dictionary.backend.game;

import java.io.*;

public class WordleFunction extends GameFunction {
    private int currentStreak;
    private int longestStreak;
    public void init() {
        try {
            this.maxTries = 5;
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
                } else {
                    numOfGuess.put(id - 3, val);
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public int getCurrentStreak() {
        return this.currentStreak;
    }

    public int getLongestStreak() {
        //System.out.println(longestStreak);
        return this.longestStreak;
    }
}