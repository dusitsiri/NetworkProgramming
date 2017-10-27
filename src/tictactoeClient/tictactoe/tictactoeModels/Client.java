package tictactoeClient.tictactoe.tictactoeModels;


import tictactoeClient.tictactoe.tictactoeControllers.StartGameController;
import tictactoeClient.tictactoe.tictactoeControllers.TicTacToeController;
import tictactoeServer.tictactoe.tictactoeModels.Server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends javax.swing.JFrame {
    String username, address = "localhost";
    public static ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false;
    TicTacToeController tictactoeController;
    StartGameController startGameController;
    private Thread IncomingReader;
    Client client;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;


    public Boolean getConnected() {
        return isConnected;
    }

    public Client(){

    }
    public void setTictactoeController(TicTacToeController con, Client client)
    {
        this.tictactoeController = con;
        this.client = client;
    }
    public void setStartGameController(StartGameController con){
        this.startGameController = con;
    }


    public void ListenThread()
    {
        IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }


    public void userAdd(String data)
    {
        client.users.add(data);
    }


    public void userRemove(String data)
    {
        client.startGameController.getDisplay().appendText(data + " is now offline.\n");
    }


    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token : tempList) {
            //users.append(token + "\n");
        }
    }

    public void b_connectActionPerformed(String user, String pass) {
        if (isConnected == false)
        {
            username = user;
            try
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;

            }
            catch (Exception ex)
            {
                tictactoeController.getAlert().setText("Cannot Connect! Try Again.");
            }

            ListenThread();

        }
    }

    public void b_disconnectActionPerformed() {
        sendDisconnect();
        Disconnect();
    }

    public void sendDisconnect()
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye);
            writer.flush();
        } catch (Exception e)
        {
            client.startGameController.getDisplay().appendText("Could not send Disconnect message.\n");
        }
    }


    public void Disconnect()
    {
        try
        {
            client.startGameController.getDisplay().appendText("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            client.startGameController.getDisplay().appendText("Failed to disconnect. \n");
        }
        isConnected = false;
        System.exit(0);
    }


    public void b_sendActionPerformed(String messages) {
        if (messages.equals("")) {
            client.startGameController.getDataIn().setText("");
            client.startGameController.getDataIn().requestFocus();
        } else {
            try {
                writer.println(username + ":" + client.startGameController.getDataIn().getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                client.startGameController.getDisplay().appendText("Message was not sent. \n");
            }
            client.startGameController.getDataIn().setText("");
            client.startGameController.getDataIn().requestFocus();
        }
        client.startGameController.getDataIn().setText("");
        client.startGameController.getDataIn().requestFocus();
    }

    public class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    System.out.println(stream + "first");
                    data = stream.split(":");
                    for (String i : data){
                        System.out.println(i);
                    }

                    if (data[2].equals(chat))
                    {
                        client.startGameController.getDisplay().appendText(data[0] + ": " + data[1] + "\n");
                    }
                    else if (data[2].equals(connect))
                    {
                        client.startGameController.getDisplay().setText("");
                        client.userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(done))
                    {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
