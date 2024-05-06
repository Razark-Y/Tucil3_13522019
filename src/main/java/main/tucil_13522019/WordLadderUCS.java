package main.tucil_13522019;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class WordLadderUCS extends WordLadder {

    public WordLadderUCS(Set<String> dictFile) throws IOException {
        super(dictFile);
    }

public SimpleEntry<List<String>, Integer> findLadder(String start, String end) {
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            return new SimpleEntry<>(Collections.emptyList(), 0);
        }
        
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, Integer> visited = new HashMap<>();
        int nodesVisited = 0;

        frontier.add(new Node(start, null, 0));

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            nodesVisited++;

            if (current.word.equals(end)) {
                return new SimpleEntry<>(buildPath(current), nodesVisited);
            }
            visited.put(current.word, current.cost);

            for (String neighbor : getNeighbors(current.word)) {
                if (!visited.containsKey(neighbor) || current.cost + 1 < visited.get(neighbor)) {
                    visited.put(neighbor, current.cost + 1);
                    frontier.add(new Node(neighbor, current, current.cost + 1));
                }
            }
        }

        return new SimpleEntry<>(Collections.emptyList(), nodesVisited); 
    }
    private List<String> buildPath(Node endNode) {
        LinkedList<String> path = new LinkedList<>();
        Node current = endNode;
        while (current != null) {
            path.addFirst(current.word);
            current = current.parent;
        }
        return path;
    }
    private Set<String> getNeighbors(String word) {
        Set<String> neighbors = new HashSet<>();
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
        System.out.println(neighbors);
        return neighbors;
    }
    private static class Node {
        String word;
        Node parent;
        int cost;
        Node(String word, Node parent, int cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }
    }
}
