package chatClient.chat.chatControllers;


import chatClient.chat.chatModels.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ChatController {

    @FXML
    private Button sendButton;
    @FXML
    private TextArea display;
    @FXML
    private TextField dataIn;
    private String username;
    private Client client;
    private FileChooser fileChooser = new FileChooser();


    public void setClient(Client client) {
        this.client = client;
        this.client.setChatController(this);
        dataIn.requestFocus();
        display.setEditable(false);
    }


    public void sendMessages(ActionEvent event) {
        client.b_sendActionPerformed(dataIn.getText());
    }

    public void logOutButton(ActionEvent event) throws IOException {
//        client.b_disconnectActionPerformed();
//        previousPage(event);
    }

    public void previousPage(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatViews/login.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public TextArea getDisplay() {
        return display;
    }

    public TextField getDataIn() {
        return dataIn;
    }

    public void onActionBtnImage(ActionEvent event) {
//      File file = fileChooser.showOpenDialog(null);if (file != null) {
//            try {
//                Image image = new Image("file:///" + file.getAbsolutePath());
//                client.sendPhoto(image);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }


    }
}

