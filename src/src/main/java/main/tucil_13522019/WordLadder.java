package main.tucil_13522019;
import java.io.IOException;
import java.util.Set;
import java.util.*;
public class WordLadder {
    protected Set<String> dictionary;
    protected String start;
    protected String end;
    public WordLadder(Set<String> dictFile) throws IOException {
        this.dictionary = dictFile;
}
    public List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = originalChar; 
        }
        // System.out.println(neighbors);
        return neighbors;
    }
}
