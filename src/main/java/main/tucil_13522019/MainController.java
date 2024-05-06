package main.tucil_13522019;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
public class MainController {
    private static WordLadder wordLadder;
    private static WordLadderGreedy wordLadderGreedy;
    private static WordLadderUCS wordLadderUCS;
    private static WordLadderAStar wordLadderAStar;
    private static Set<String> dict;
    String selectedAlgorithm;
    String startWord;
    String endWord;
    SimpleEntry<List<String>, Integer> result;
    @FXML
    private ComboBox<String> algorithmChoice;
    @FXML
    private Label messageLabel; 
    @FXML
    private TextField StartInput;
    @FXML
    private TextField TargetInput;
    @FXML
    private StackPane clickableArea,ResultArea;
    @FXML
    private TextField widthInput;
    @FXML
    private GridPane resultGrid;
    @FXML
    private GridPane grid;
    @FXML
    private Label rectangleLabel;
    @FXML
    private void onRectangleClicked() {
        startWord = StartInput.getText().trim().toLowerCase();
        endWord = TargetInput.getText().trim().toLowerCase();
        selectedAlgorithm = algorithmChoice.getValue();
        if (startWord.isEmpty() || endWord.isEmpty()) {
            messageLabel.setText("Error: Both fields must be filled.");
            return;
        }

        if (startWord.length() != endWord.length()) {
            messageLabel.setText("Error: Words must be of the same length.");
            return;
        }
        if (!dict.contains(startWord) || !dict.contains(endWord)) {
            messageLabel.setText("Error: Both start and end words must be in the dictionary.");
            return;
        }
        messageLabel.setText("Enjoy the results below!!");
        findPath();
    }
    @FXML
    private void initialize(){
        try{
            algorithmChoice.getItems().addAll("UCS", "GBFS","A*");
            dict = DictionaryLoader.loadDictionary("src/main/resources/main/tucil_13522019/Dict.txt");
            wordLadderGreedy = new WordLadderGreedy(dict);
            wordLadderUCS = new WordLadderUCS(dict);
            wordLadderAStar = new WordLadderAStar(dict);
        }catch (IOException e){
            messageLabel.setText("Dictionary Not Set Properly");
        }
    }
    @FXML
    private void findPath() {
        Runtime runtime = Runtime.getRuntime();
        long startMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();  
        try {
            switch (selectedAlgorithm) {
                case "UCS":
                    result = wordLadderUCS.findLadder(startWord, endWord);
                    break;
                case "GBFS":
                    result = wordLadderGreedy.findLadder(startWord, endWord);
                    break;
                case "A*":
                    result = wordLadderAStar.findLadder(startWord, endWord);
                    break;
                default:
                    messageLabel.setText(String.format("Please Select An Algorithm"));
                    break;
            }
            long endTime = System.nanoTime();  
            long duration = (endTime - startTime) / 1_000_000; 
            long endMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = (endMemoryUsage - startMemoryUsage);  
            List<String> path = result.getKey();
            int nodesVisited = result.getValue();
            if (path.isEmpty()) {
                messageLabel.setText(String.format("No ladder found."));
                return;
            }
            updateResultGrid(path);
            messageLabel.setText(String.format("Found ladder with %d nodes visited. Time taken: %d ms. Memory used: %d bytes", nodesVisited, duration, memoryUsed));
        } catch (Exception e) {
            messageLabel.setText(String.format("Please Select An Algorithm"));
        }
    }
    private void updateResultGrid(List<String> results) {
        resultGrid.getChildren().clear();
        if (results.isEmpty()) return;
    
        int numRows = results.size();
        int numCols = results.stream().mapToInt(String::length).max().orElse(0) + 1; 
        resultGrid.getColumnConstraints().clear();
    
        // First column for the numbers
        ColumnConstraints numberColumn = new ColumnConstraints(50); 
        resultGrid.getColumnConstraints().add(numberColumn);
    
        // Columns for each character in the words
        for (int col = 0; col < numCols - 1; col++) {
            ColumnConstraints cc = new ColumnConstraints(80); 
            resultGrid.getColumnConstraints().add(cc);
        }
    
        resultGrid.getRowConstraints().clear();
        for (int row = 0; row < numRows; row++) {
            RowConstraints rc = new RowConstraints(80); 
            resultGrid.getRowConstraints().add(rc);
        }
    
        for (int i = 0; i < numRows; i++) {
            String word = results.get(i);
            TextField rowLabel = new TextField(String.valueOf(i + 1));
            rowLabel.setEditable(false);
            rowLabel.setAlignment(Pos.CENTER);
            rowLabel.setMinWidth(100);
            rowLabel.setMinHeight(80);
            rowLabel.setFont(Font.font("Cooper Black", 36));
            rowLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #2A8278;"); 
            resultGrid.add(rowLabel, 0, i);
    
            for (int j = 0; j < word.length(); j++) {
                TextField textField = new TextField(Character.toString(word.charAt(j)).toUpperCase());
                textField.setEditable(false);
                textField.setAlignment(Pos.CENTER);
                textField.setMinWidth(80);
                textField.setMinHeight(80);
                textField.setFont(Font.font("Cooper Black", 36));
                resultGrid.add(textField, j + 1, i); 
            }
        }
    }
    
    

    
}
