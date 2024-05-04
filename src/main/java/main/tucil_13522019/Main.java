package main.tucil_13522019;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
public class Main extends Application {
    private static WordLadder wordLadder;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // initializeWordLadder("path/to/dictionary.txt");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/tucil_13522019/main-view.fxml"));
        Parent root = loader.load(); 
        primaryStage.setTitle("Dynamic Grid Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }
    private void initializeWordLadder(String dictFile) {
        try {
            wordLadder = new WordLadder(dictFile);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception or exit if necessary
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        launch(args); 
    }
}