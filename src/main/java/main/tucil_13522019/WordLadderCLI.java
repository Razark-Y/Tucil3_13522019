package main.tucil_13522019;
import java.util.AbstractMap.SimpleEntry;
import java.io.IOException;
import java.util.*;

public class WordLadderCLI {
    private static WordLadder wordLadder;
    private static WordLadderGreedy wordLadderGreedy;
    private static WordLadderUCS wordLadderUCS;
    private static WordLadderAStar wordLadderAStar;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Set<String> dict = DictionaryLoader.loadDictionary("../resources/main/tucil_13522019/Dict.txt");
            wordLadderGreedy = new WordLadderGreedy(dict);
            wordLadderUCS = new WordLadderUCS(dict);
            wordLadderAStar = new WordLadderAStar(dict);
        } catch (IOException e) {
            System.out.println("Dictionary not set properly: " + e.getMessage());
            return;
        }

        System.out.println("Welcome to the Word Ladder Solver CLI!");
        System.out.println("Please enter the start word:");
        String startWord = scanner.nextLine().trim().toLowerCase();

        System.out.println("Please enter the end word:");
        String endWord = scanner.nextLine().trim().toLowerCase();

        if (startWord.isEmpty() || endWord.isEmpty()) {
            System.out.println("Error: Both fields must be filled.");
            return;
        }

        if (startWord.length() != endWord.length()) {
            System.out.println("Error: Words must be of the same length.");
            return;
        }

        System.out.println("Select algorithm (UCS, GBFS, A*):");
        String selectedAlgorithm = scanner.nextLine().trim();

        SimpleEntry<List<String>, Integer> result = findPath(selectedAlgorithm, startWord, endWord);
        if (result.getKey().isEmpty()) {
            System.out.println("No ladder found.");
        } else {
            System.out.println("Found ladder: " + result.getKey());
            System.out.println("Nodes visited: " + result.getValue());
        }
    }

    private static SimpleEntry<List<String>, Integer> findPath(String algorithm, String start, String end) {
        long startTime = System.nanoTime();
        SimpleEntry<List<String>, Integer> result;

        switch (algorithm.toUpperCase()) {
            case "UCS":
                result = wordLadderUCS.findLadder(start, end);
                break;
            case "GBFS":
                result = wordLadderGreedy.findLadder(start, end);
                break;
            case "A*":
                result = wordLadderAStar.findLadder(start, end);
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm selection");
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert nanoseconds to milliseconds
        System.out.println("Time taken: " + duration + " ms");
        return result;
    }
}
