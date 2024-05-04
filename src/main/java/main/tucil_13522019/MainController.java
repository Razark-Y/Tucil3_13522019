package main.tucil_13522019;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
public class MainController {

    @FXML
    private TextField widthInput;

    @FXML
    private GridPane grid;
    @FXML
    private Label rectangleLabel;
    // Arrays to hold the TextFields for start and target inputs
    private TextField[] startInputs;
    private TextField[] targetInputs;
    @FXML
    private void onRectangleClicked() {
        generateGrid();  // Generate grid when rectangle is clicked
        rectangleLabel.setText("Generating...");  // Change text on rectangle label
    }
    @FXML
    private void generateGrid() {
        grid.getChildren().clear();  // Clear existing content in the grid

        int width = Integer.parseInt(widthInput.getText());  // Number of columns

        startInputs = new TextField[width];  // Initialize arrays to hold TextFields
        targetInputs = new TextField[width];

        // Create two rows of TextFields
        for (int j = 0; j < width; j++) {
            createTextFieldForRow(startInputs, j, 0);  // Row 0 for start inputs
            createTextFieldForRow(targetInputs, j, 1);  // Row 1 for target inputs
        }
    }

    // Helper method to create a TextField and place it in the grid
    private void createTextFieldForRow(TextField[] row, int columnIndex, int rowIndex) {
        TextField textField = new TextField();
        textField.setPrefWidth(100);
        textField.setMaxWidth(100);
        textField.setPrefHeight(100);
        textField.setMaxHeight(100);
        GridPane.setConstraints(textField, columnIndex, rowIndex);
        grid.getChildren().add(textField);
        row[columnIndex] = textField;  // Store the TextField in the array
    }

    // Method to convert the contents of TextFields to a string array
    @SuppressWarnings("exports")
    public String[] getTextFromRow(TextField[] row) {
        String[] texts = new String[row.length];
        for (int i = 0; i < row.length; i++) {
            texts[i] = row[i].getText();  // Get text from each TextField
        }
        return texts;
    }

    // Example method that could be called to handle the strings
    public void processInput() {
        String[] startTexts = getTextFromRow(startInputs);
        String[] targetTexts = getTextFromRow(targetInputs);

        // Here you could further process these strings or display them
        System.out.println("Start Inputs: " + String.join(", ", startTexts));
        System.out.println("Target Inputs: " + String.join(", ", targetTexts));
    }
}
