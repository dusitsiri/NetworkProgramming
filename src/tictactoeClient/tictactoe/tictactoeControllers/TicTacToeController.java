package tictactoeClient.tictactoe.tictactoeControllers;

import javafx.scene.text.Text;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TicTacToeController implements  Runnable{
    private StartGameController startGameController;
    private DatagramSocket datagramSocket;
    private Thread thread;

    public TicTacToeController(StartGameController startGameController){
        this.startGameController = startGameController;
    }

    public void bind(int port){
        try{
            datagramSocket = new DatagramSocket(port);
        }catch (SocketException e){
            System.err.println("200 Error: Address already in use: Cannot bind");
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        datagramSocket.close();
    }

//    public void send(InetSocketAddress address, String message, String chatWith, boolean isChat) {
//        for (int i = 0 ; i< mainController.getContacts().size();i++){
//            if (mainController.getContacts().get(i).getName().equals(chatWith) && isChat){
//                String[] split = message.split("`");
//                String[] name = split[0].split("/");
//                System.out.println(split[1]);
//                if (split[1].equals(":]") || split[1].equals(":/") || split[1].equals(":z") || split[1].equals(":-") || split[1].equals(":8")) {
//                    displayEmojiInChat(name[1]+":"+split[1]);
//                    mainController.getContacts().get(i).setChatLog(mainController.getContacts().get(i).getChatLog() + name[1]+":"+split[1] + '\n');
//                    System.out.println("notChat");
//                } else {
//                    mainController.getMessagePane().getChildren().add(new Text(name[1] + ":" + split[1] + '\n'));
//                    mainController.getContacts().get(i).setChatLog(mainController.getContacts().get(i).getChatLog() + name[1]+":"+split[1] + '\n');
//                    System.out.println("isChat");
//                }
//            }
//        }
//        byte[] buffer = message.getBytes();
//        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//        packet.setSocketAddress(address);
//        try {
//            socket.send(packet);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void run() {

    }
}
