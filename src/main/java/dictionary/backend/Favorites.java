package dictionary.backend;

import java.util.HashMap;

public class Favorites extends WordFileUtils{
    private final HashMap<String, String> allFav = new HashMap<>();
    public void addTo(Word w) {
        if (allFav.get(w.getWordTarget()) == null) {
            allWords.add(w);
            allFav.put(w.getWordTarget(), w.getWordMeaning());
        }
    }

    public void remove(String target) {
        if (allFav.get(target) != null) {
            allFav.remove(target);
            for (Word word : allWords) {
                if (word.getWordTarget().equals(target)) {
                    allWords.remove(word);
                    return;
                }
            }
        }
    }

    public HashMap<String, String> getAllFav() {
        return allFav;
    }

    public boolean isFavorited(String target) {
        return allFav.get(target) != null;
    }
}
