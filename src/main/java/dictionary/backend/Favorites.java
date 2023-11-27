package dictionary.backend;

import java.util.ArrayList;
import java.util.HashMap;

public class Favorites extends WordFileUtils{
    private final HashMap<String, String> allFav = new HashMap<>();
    public void addTo(Word w) {
        if (allFav.get(w.getWordTarget()) == null) {
            allFav.put(w.getWordTarget(), w.getWordMeaning());
        }
    }

    public void remove(String target) {
        if (allFav.get(target) != null) {
            allFav.remove(target);
        }
    }

    public HashMap<String, String> getAllFav() {
        return allFav;
    }

    @Override
    public ArrayList<Word> getAllWords() {
        ArrayList<Word> all = new ArrayList<>();
        for (String s : allFav.keySet()) {
            Word word = new Word(s, allFav.get(s));
            all.add(word);
        }
        return all;
    }

    public boolean isFavorited(String target) {
        return allFav.get(target) != null;
    }
}
