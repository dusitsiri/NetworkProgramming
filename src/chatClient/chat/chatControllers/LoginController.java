package chatClient.chat.chatControllers;

import chatClient.chat.chatModels.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label alert;
    @FXML
    private TextField username, portText;
    private Client client = new Client();

    public void initialize() {
        client.setLoginController(this, getClient());

        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                {
                    try{
                        client.b_connectActionPerformed(username.getText(),portText.getText());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    public void connectionButton(ActionEvent event) throws IOException {
        String user = username.getText();
        String port = portText.getText();
        client.b_connectActionPerformed(user, port);
        try{
            int check = Integer.parseInt(port);
            if (client.getPort() == check) startGame(event);
        }catch (NumberFormatException e){
            alert.setText("201 ERROR \n"+e);
        }
    }

    public void startGame(ActionEvent event) throws IOException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (client.getConnected()) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatViews/chatingroom.fxml"));
                    try {
                        stage.setScene(new Scene(loader.load()));
                        ChatController chatController = loader.getController();
                        chatController.setClient(getClient());
                        chatController.getDisplay().appendText(getUsername().getText()+" is already connected. \n");
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
