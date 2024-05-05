package main.tucil_13522019;
import java.io.IOException;
import java.util.Set;
public class WordLadder {
    protected Set<String> dictionary;
    protected String start;
    protected String end;
    public WordLadder(Set<String> dictFile) throws IOException {
        this.dictionary = dictFile;
}
}
