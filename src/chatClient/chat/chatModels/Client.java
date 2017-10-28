package chatClient.chat.chatModels;


import chatClient.chat.chatControllers.ChatController;
import chatClient.chat.chatControllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utility.Message;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends javax.swing.JFrame {
    String username, address = "localhost";
    private static ArrayList<String> users = new ArrayList<>();
    int port = 2222;
    Boolean isConnected = false;
    LoginController loginController;
    ChatController chatController;
    private Thread IncomingReader;
    Client client;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;



    public void setLoginController(LoginController login, Client client) {
        this.loginController = login;
        this.client = client;
    }

    public void setChatController(ChatController con) {
        this.chatController = con;
    }


    public void ListenThread() {
        IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
//    public void sendPhoto(Image image) throws IOException{
//        Message newMessage = new Message();
//        newMessage.setImage(image);
//        newMessage.isImage = true;
//        newMessage.setOrigin(username);
//        writer.println(newMessage);
//        writer.flush();
//    }


//
//
//    public void userAdd(String data) {
//        users.add(data);
//    }


//    public void userRemove(String data) {
//        client.chatController.getDisplay().appendText(data + " is now offline.\n");
//    }


//    public void writeUsers() {
//        String[] tempList = new String[(users.size())];
//        users.toArray(tempList);
//        for (String token : tempList) {
//            writer.append(token);
//        }
//    }

    public int getPort() {
        return port;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public static ArrayList<String> getUsers() {
        return users;
    }

    public void b_connectActionPerformed(String user, String pass) {
        if (isConnected == false && pass.equals(String.valueOf(port))) {
            username = user;
            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;

            } catch (Exception ex) {
                loginController.getAlert().setText("202 ERROR \n" + ex);
            }

            ListenThread();
        }
    }

    public void b_disconnectActionPerformed() {
        sendDisconnect();
        Disconnect();
    }

    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            client.chatController.getDisplay().appendText("Could not send Disconnect message.\n");
        }
    }


    public void Disconnect(){
        try {
            client.chatController.getDisplay().appendText("Disconnected.\n");
            sock.close();

        } catch (Exception ex) {
            client.chatController.getDisplay().appendText("Failed to disconnect. \n");
        }

        isConnected = false;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    public void b_sendActionPerformed(String messages) {
        if (messages.equals("")) {
            client.chatController.getDataIn().setText("");
            client.chatController.getDataIn().requestFocus();
        } else {
            try {
                writer.println(username + ":" + client.chatController.getDataIn().getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                client.chatController.getDisplay().appendText("Message was not sent. \n");
            }
            client.chatController.getDataIn().setText("");
            client.chatController.getDataIn().requestFocus();
        }
        client.chatController.getDataIn().setText("");
        client.chatController.getDataIn().requestFocus();
    }


    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String[] data;
            String stream = null, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                if (getConnected()) {
                    while ((stream = reader.readLine()) != null) {
                        System.out.println(stream + "first");
                        data = stream.split(":");
                        for (String i : data) {
                            System.out.println(i);
                        }
                        if (data[2].equals(chat)) {
                            client.chatController.getDisplay().appendText(data[0] + ": " + data[1] + "\n");
                        } else if (data[2].equals(connect)) {
                            client.chatController.getDisplay().setText("");
//                            client.userAdd(data[0]);
                        } else if (data[2].equals(disconnect)) {
//                            userRemove(data[0]);
                        } else if (data[2].equals(done)) {
                            //users.setText("");
//                            writeUsers();
                            users.clear();
                        }
                    }
                }
                }catch (SocketException s) {
                    s.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
