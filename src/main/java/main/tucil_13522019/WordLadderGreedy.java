package main.tucil_13522019;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
public class WordLadderGreedy extends WordLadder {
    public WordLadderGreedy(Set<String> dictFile) throws IOException {
        super(dictFile);  
    }

    private class Node {
        String word;
        Node parent;
        int heuristic;

        public Node(String word, Node parent, int heuristic) {
            this.word = word;
            this.parent = parent;
            this.heuristic = heuristic;
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
    public SimpleEntry<List<String>, Integer> findLadder(String start, String end) {
        if (start.equals(end)) {
            return new SimpleEntry<>(Collections.singletonList(start), 0);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<String> visited = new HashSet<>();
        int nodesVisited = 0;

        Node startNode = new Node(start, null, calculateHeuristic(start, end));
        queue.add(startNode);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            nodesVisited++;

            if (current.word.equals(end)) {
                return new SimpleEntry<>(buildPath(current), nodesVisited);
            }

            List<String> neighbors = getNeighbors(current.word);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    Node nextNode = new Node(neighbor, current, calculateHeuristic(neighbor, end));
                    queue.add(nextNode);
                    visited.add(neighbor);
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
