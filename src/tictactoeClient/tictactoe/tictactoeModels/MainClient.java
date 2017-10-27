package tictactoeClient.tictactoe.tictactoeModels;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeClient.tictactoe.tictactoeControllers.StartGameController;
import tictactoeServer.tictactoe.tictactoeModels.Server;

import java.io.IOException;

public class MainClient extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../tictactoeViews/TicTacToe.fxml"));
        primaryStage.setTitle("TicTacToe Client");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
