package main.tucil_13522019;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class DictionaryLoader {
    private Set<String> dictionary;
    private String start;
    private String end;

    public static Set<String> loadDictionary(String dictFile) throws IOException {
        Set<String> dict = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dictFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dict.add(line.trim().toLowerCase());
            }
        }
        return dict;
    }
}