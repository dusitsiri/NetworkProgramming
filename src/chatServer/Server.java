package chatServer;

import chatClient.chat.chatModels.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server extends javax.swing.JFrame {

    private ArrayList clientOutputStreams;
    private ArrayList<String> users = new ArrayList<>();
    private int port = 2222;

    private javax.swing.JButton button_Clear;
    private javax.swing.JButton button_End;
    private javax.swing.JButton button_Start;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea text_Server;


    public Server() {
        initComponents();
        text_Server.setEditable(false);
    }

    public void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        text_Server = new javax.swing.JTextArea();
        button_Start = new javax.swing.JButton();
        button_End = new javax.swing.JButton();
        button_Clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server window");
        setName("server");
        setResizable(false);

        text_Server.setColumns(20);
        text_Server.setRows(5);
        jScrollPane1.setViewportView(text_Server);

        button_Start.setText("START");
        final int[] countStartButton = {0};
        button_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countStartButton[0]++;
                if (countStartButton[0] == 1) {
                    button_StartActionPerformed(evt);
                    System.out.println("Server is on.");
                    System.out.println("Waiting for client.");
                }
            }
        });

        button_End.setText("END");
        button_End.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EndActionPerformed(evt);
            }
        });

        button_Clear.setText("Clear");
        button_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(button_End, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(button_Start, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(button_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(209, 209, 209)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button_Start))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button_Clear)
                                        .addComponent(button_End))
                                .addGap(4, 4, 4)));
        pack();
    }

    public void button_EndActionPerformed(java.awt.event.ActionEvent evt) {
        if (text_Server.getText().equals("Server started...\n")) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (clientOutputStreams != null) {
                tellEveryone("Server:is stopping and all users will be disconnected.\n Chat");
            }
            System.out.println("Server stopping...");
            text_Server.setText("");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.exit(0);
    }

    public void button_StartActionPerformed(java.awt.event.ActionEvent evt) {
        Thread starter = new Thread(new ServerStart());
        starter.start();
        text_Server.append("Server started...\n");
    }


    public void button_ClearActionPerformed(java.awt.event.ActionEvent evt) {
        text_Server.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    //ClientHandler
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
                text_Server.append(reader.readLine() + "\n");
            } catch (Exception ex) {
                text_Server.append("Unexpected error... \n");
            }
        }

        @Override
        public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    text_Server.append("Received: " + message + "\n");
                    data = message.split(":");
                    for (String i : data) {
                        System.out.println(i);
                    }
                    int count = 1;
                    for (String token : data) {
                        if (count == 1) text_Server.append("username: " + token + "\n");
                        else if (count == 2) text_Server.append("text: " + token + "\n");
                        else text_Server.append("type: " + token + "\n");
                        count++;
                    }

                    if (data[2].equals(connect)) {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        tellEveryone((data[0] + " :has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } else if (data[2].equals(chat)) {
                        tellEveryone(message);
                    } else {
                        text_Server.append("No Conditions were met. \n");
                    }
                }
            } catch (SocketException s) {
                text_Server.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                text_Server.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }

    //ServerStart
    public class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            try {
                ServerSocket serverSock = new ServerSocket(port);
                while (true) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);
                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    text_Server.append("Got a connection. \n");
                    System.out.println("Recieve connection from client.");
                }
            } catch (Exception ex) {
                text_Server.append("Error making a connection. \n");
            }
        }
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        text_Server.append("Before " + name + " added. \n");
        users.add(name);
        text_Server.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }


    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                text_Server.append("Sending: " + message + "\n");
                writer.flush();
                text_Server.setCaretPosition(text_Server.getDocument().getLength());

            } catch (Exception ex) {
                text_Server.append("Error telling everyone. \n");
            }
        }
    }
}
