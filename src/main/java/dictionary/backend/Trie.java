package dictionary.backend;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    private static final TrieNode root = new TrieNode();
    private static final ArrayList<String> trieWords = new ArrayList<String>();
    /**
     * A node of Trie
     */
    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        TrieNode() {
            children = new TreeMap<Character, TrieNode>();
            isEndOfWord = false;
        }
    }

    /**
     * Insert a word.
     */
    public static void insert(String word) {
        int length = word.length();
        TrieNode curNode = root;

        for (int level = 0; level < length; level++) {
            char cur = word.charAt(level);

            //there has not been any prefix like this
            if (curNode.children.get(cur) == null) {
                curNode.children.put(cur, new TrieNode());
            }

            //move to the next node
            curNode = curNode.children.get(cur);
        }

        //the current node is the end of a word.
        curNode.isEndOfWord = true;
    }

    /**
     * All words in Trie.
     */
    public ArrayList<String> getAllTrieWords() {
        return trieWords;
    }

    /**
     * dfs to get all words that have prefix target
     * @param target - String
     * @param curNode - current Trie Node.
     */
    private void getAllPrefixes(String target, TrieNode curNode) {
        if (curNode.isEndOfWord) {
            trieWords.add(target);
            return;
        }
        for (char index : curNode.children.keySet()) {
            if (curNode.children.get(index) != null) {
                getAllPrefixes(target + index, curNode.children.get(index));
            }
        }
    }

    /**
     * Search all prefixes.
     */
    public ArrayList<String> search(String target) {
        trieWords.clear();
        if (target.isEmpty()) {
            return trieWords;
        }

        TrieNode curNode = root;
        int length = target.length();
        for (int level = 0; level < length; level++) {
            char cur = target.charAt(level);
            if (curNode.children.get(cur) == null) {
                //no words have this prefix
                return trieWords;
            }
            curNode = curNode.children.get(cur);
        }
        //get all the words with prefix target.
        getAllPrefixes(target, curNode);
        return trieWords;
    }

    /**
     * Delete word target from the Trie.
     */
    public static void delete(String target) {
        TrieNode curNode = root;
        int length = target.length();

        for (int level = 0; level < length; level++) {
            char cur = target.charAt(level);
            if (curNode.children.get(cur) == null) {
                System.out.println("Cannot find this word!");
                return;
            }
            curNode = curNode.children.get(cur);
        }

        if (!curNode.isEndOfWord) {
            System.out.println("Cannot find this word!");
            return;
        }
        curNode.isEndOfWord = false;
    }
}
