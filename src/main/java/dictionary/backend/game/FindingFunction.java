package dictionary.backend.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FindingFunction extends GameFunction{
    @Override
    public void init() {
        try {
            this.maxTries = 10;
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
                if (id <= 1) {
                    switch (id) {
                        case 0: gamesPlayed = val;
                        case 1: gamesWon = val;
                    }
                } else {
                    numOfGuess.put(id - 1, val);
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
