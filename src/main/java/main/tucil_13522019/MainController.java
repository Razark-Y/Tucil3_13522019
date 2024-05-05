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
        messageLabel.setText("Enjoy the results below!!");
        findPath();
    }
    @FXML
    private void initialize(){
        try{
            algorithmChoice.getItems().addAll("UCS", "GBFS");
            Set<String> dict = DictionaryLoader.loadDictionary("src/main/resources/main/tucil_13522019/Dict.txt");
            wordLadderGreedy = new WordLadderGreedy(dict);
            wordLadderUCS = new WordLadderUCS(dict);
        }catch (IOException e){
            messageLabel.setText("Dictionary Not Set Properly");
        }
    }
    @FXML
    private void findPath() {
        long startTime = System.nanoTime();  // Start timing
        try {
            switch (selectedAlgorithm) {
                case "UCS":
                    result = wordLadderUCS.findLadder(startWord, endWord);
                    break;
                case "GBFS":
                    result = wordLadderGreedy.findLadder(startWord, endWord);
                    break;
                // case "A*":
                //     result = wordLadderAStar.findLadder(startWord, endWord);
                //     break;
                default:
                    throw new IllegalArgumentException("Invalid algorithm selection");
            }
            long endTime = System.nanoTime();  // End timing
            long duration = (endTime - startTime) / 1_000_000;  // Convert nanoseconds to milliseconds

            List<String> path = result.getKey();
            int nodesVisited = result.getValue();
            if (path.isEmpty()) {
                messageLabel.setText("No ladder found. Time taken: " + duration + " ms");
                return;
            }
            updateResultGrid(path);
            messageLabel.setText("Found ladder with " + nodesVisited + " nodes visited. Time taken: " + duration + " ms");
        } catch (Exception e) {
            messageLabel.setText("Failed to find ladder: " + e.getMessage());
        }
    }
    private void updateResultGrid(List<String> results) {
        resultGrid.getChildren().clear();
        if (results.isEmpty()) return;
    
        int numRows = results.size();
        int numCols = results.stream().mapToInt(String::length).max().orElse(0);
        resultGrid.getColumnConstraints().clear();
        for (int col = 0; col < numCols; col++) {
            ColumnConstraints cc = new ColumnConstraints(50);
            resultGrid.getColumnConstraints().add(cc);
        }
    
        resultGrid.getRowConstraints().clear();
        for (int row = 0; row < numRows; row++) {
            RowConstraints rc = new RowConstraints(50);
            resultGrid.getRowConstraints().add(rc);
        }
    
        for (int i = 0; i < numRows; i++) {
            String word = results.get(i);
            // System.out.println(word);
            for (int j = 0; j < word.length(); j++) {
                // System.out.println("Adding text: " + word.charAt(j));
                TextField textField = new TextField(Character.toString(word.charAt(j)).toUpperCase());
                textField.setEditable(false);
                textField.setAlignment(Pos.CENTER);
                textField.setMinWidth(80);  
                textField.setMinHeight(80);  
                textField.setFont(Font.font("Cooper Black", 36));
                resultGrid.add(textField, j, i);
                // textField.setStyle("-fx-text-fill: white; -fx-control-inner-background: #333;");
            }
        }
    }
    
    

    
}
