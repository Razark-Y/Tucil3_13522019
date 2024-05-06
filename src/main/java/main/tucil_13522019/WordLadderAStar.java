package main.tucil_13522019;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class WordLadderAStar extends WordLadder {
    public WordLadderAStar(Set<String> dictFile) throws IOException {
        super(dictFile);  
    }

    private class Node {
        String word;
        Node parent;
        int g;
        int h; 
        int f;

        public Node(String word, Node parent, int g, int h) {
            this.word = word;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }

    private int calculateHeuristic(String word, String end) {
        int greens = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == end.charAt(i)) {
                greens++;
            }
        }
        return word.length() - greens;
    }

    private List<String> generateNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != original) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = original;
        }
        return neighbors;
    }

    public SimpleEntry<List<String>, Integer> findLadder(String start, String end) {
        if (start.equals(end)) {
            return new SimpleEntry<>(Collections.singletonList(start), 0);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<String, Integer> bestCosts = new HashMap<>();
        int nodesVisited = 0;

        Node startNode = new Node(start, null, 0, calculateHeuristic(start, end));
        queue.add(startNode);
        bestCosts.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            nodesVisited++;

            if (current.word.equals(end)) {
                return new SimpleEntry<>(buildPath(current), nodesVisited);
            }

            List<String> neighbors = generateNeighbors(current.word);
            for (String neighbor : neighbors) {
                int tentativeG = current.g + 1;
                if (!bestCosts.containsKey(neighbor) || tentativeG < bestCosts.get(neighbor)) {
                    bestCosts.put(neighbor, tentativeG);
                    Node nextNode = new Node(neighbor, current, tentativeG, calculateHeuristic(neighbor, end));
                    queue.add(nextNode);
                }
            }
        }

        return new SimpleEntry<>(new ArrayList<>(), nodesVisited);
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
}
