package tictactoeClient.tictactoe.tictactoeControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tictactoeClient.tictactoe.tictactoeModels.Client;
import tictactoeServer.tictactoe.tictactoeModels.Server;

import java.io.IOException;

public class TicTacToeController {

    @FXML
    private Label alert;
    @FXML
    private TextField username, password;
    private Client client = new Client();

    public void initialize() {
        client.setTictactoeController(this, getClient());
    }

    @FXML
    public void connectionButton(ActionEvent event) throws IOException {
        String user = username.getText();
        String pass = password.getText();
        client.b_connectActionPerformed(user, pass);
        startGame(event);
    }

    public void startGame(ActionEvent event) throws IOException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (client.getConnected()) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../tictactoeViews/StartGame.fxml"));
                    try {
                        stage.setScene(new Scene(loader.load()));
                        StartGameController startGameController = loader.getController();
                        startGameController.setClient(getClient());
                        startGameController.getDisplay().appendText(getUsername().getText()+" is already connected. \n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.show();
                }
            }
        });
    }

    public TextField getUsername() {
        return username;
    }

    public Client getClient() {
        return client;
    }

    public Label getAlert() {
        return alert;
    }
}
