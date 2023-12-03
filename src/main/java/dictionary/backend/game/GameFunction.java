package dictionary.backend.game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class GameFunction {
    protected int gamesPlayed;
    protected int gamesWon;

    protected final Map<Integer, Integer> numOfGuess = new HashMap<>();

    protected String historyPath;
    protected int maxTries;

    public abstract void init();

    public void updateWinCase(int num) {
        this.gamesWon = this.gamesWon + 1;
        this.gamesPlayed = this.gamesPlayed + 1;
        int x = this.numOfGuess.get(num);
        this.numOfGuess.put(num, x + 1);
        if (this instanceof WordleFunction) {
            ((WordleFunction)this).setCurrentStreak(((WordleFunction) this).getCurrentStreak() + 1);
            if (((WordleFunction) this).getCurrentStreak() > ((WordleFunction) this).getLongestStreak()) {
                ((WordleFunction) this).setLongestStreak(((WordleFunction) this).getCurrentStreak());
            }
        }
    }

    public void updateLoseCase() {
        this.gamesPlayed += 1;
        if (this instanceof WordleFunction) {
            ((WordleFunction) this).setCurrentStreak(0);
        }
    }

    public void updateToFiles() {
        try {
            FileWriter fw = new FileWriter(historyPath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(gamesPlayed));
            bw.newLine();
            bw.write(Integer.toString(gamesWon));
            bw.newLine();
            if (this instanceof WordleFunction) {
                WordleFunction wf = (WordleFunction) this;
                bw.write(Integer.toString(wf.getCurrentStreak()));
                bw.newLine();
                bw.write(Integer.toString(wf.getLongestStreak()));
                bw.newLine();
            }
            for (int i = 1; i <= maxTries; i++) {
                bw.write(Integer.toString(numOfGuess.get(i)));
                bw.newLine();
            }
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

    public Map<Integer, Integer> getNumOfGuess() {
        return numOfGuess;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public double getWinRate() {
        if (gamesPlayed > 0) {
            return ((double) gamesWon / gamesPlayed) * 100;
        }
        return 0;
    }
    public void setHistoryPath(String historyPath) {
        this.historyPath = historyPath;
    }

    public int getMaxTries() {
        return this.maxTries;
    }
}
