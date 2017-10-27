package tictactoeClient.tictactoe.tictactoeControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tictactoeClient.tictactoe.tictactoeModels.Client;
import tictactoeClient.tictactoe.tictactoeModels.TicTacToe;


public class StartGameController {
    TicTacToe ticTacToeClient = new TicTacToe();
    int[][] p1 = new int[3][3];
    int[][] p2 = new int[3][3];
    @FXML
    private Rectangle r1, r2, r3, r4, r5, r6, r7, r8, r9;
    @FXML
    private Label turnplayer, winner;
    //X
    @FXML
    private Label x1, x2, x3, x4, x5, x6, x7, x8, x9;
    //O
    @FXML
    private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9;

    public void mouseClicked(MouseEvent event) {
        if (event.getSource().equals(r1)) {
            int x = 0;
            int y = 0;
            takeTurn(x, y, c1, x1);
        } else if (event.getSource().equals(r2)) {
            int x = 0;
            int y = 1;
            takeTurn(x, y, c2, x2);
        } else if (event.getSource().equals(r3)) {
            int x = 0;
            int y = 2;
            takeTurn(x, y, c3, x3);
        } else if (event.getSource().equals(r4)) {
            int x = 1;
            int y = 0;
            takeTurn(x, y, c4, x4);
        } else if (event.getSource().equals(r5)) {
            int x = 1;
            int y = 1;
            takeTurn(x, y, c5, x5);
        } else if (event.getSource().equals(r6)) {
            int x = 1;
            int y = 2;
            takeTurn(x, y, c6, x6);
        } else if (event.getSource().equals(r7)) {
            int x = 2;
            int y = 0;
            takeTurn(x, y, c7, x7);
        } else if (event.getSource().equals(r8)) {
            int x = 2;
            int y = 1;
            takeTurn(x, y, c8, x8);
        } else if (event.getSource().equals(r9)) {
            int x = 2;
            int y = 2;
            takeTurn(x, y, c9, x9);
        }

    }

    //mouseclick each rectangle
    public void takeTurn(int x, int y, Circle c, Label ex) {
        if (ticTacToeClient.getDraw().equals("") && winner.getText().equals("")) {
            if (ticTacToeClient.getPlayer1().equals("")) {
                c.setVisible(true);
                player2Turn();
            } else if (ticTacToeClient.getPlayer1().equals("play1") && !(c.isVisible())) {
                ex.setVisible(true);
                player1Turn();
            }
            data(x, y, ticTacToeClient.getPlayer1());
        }
    }

    public void player1Turn() {
        ticTacToeClient.setPlayer1("");
        turnplayer.setText("Player1 Turn");
    }

    public void player2Turn() {
        ticTacToeClient.setPlayer1("play1");
        turnplayer.setText("Player2 Turn");
    }

    public void data(int x, int y, String player) {
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p1.length; j++) {
                if (p1[x][y] == 0 && player.equals("play1")) {
                    p1[x][y] = ticTacToeClient.getTables(x, y);
                    ticTacToeClient.setMoveCount1();
                    if (!ticTacToeClient.check1(x, y, p1) && ticTacToeClient.getDraw().equals("Draw")) {
                        winner.setText("Draw");
                        break;
                    } else if (ticTacToeClient.check1(x, y, p1) && ticTacToeClient.getDraw().equals("")) {
                        winner.setText("Player1 Win");
                    }
                    break;
                } else if (p2[x][y] == 0 && player.equals("")) {
                    p2[x][y] = ticTacToeClient.getTables(x, y);
                    ticTacToeClient.setMoveCount2();
                    if (!ticTacToeClient.check2(x, y, p2) && ticTacToeClient.getDraw().equals("Draw")) {
                        winner.setText("Draw");
                        break;
                    } else if (ticTacToeClient.check2(x, y, p2) && ticTacToeClient.getDraw().equals("")) {
                        winner.setText("Player2 Win");
                    }
                    break;
                }
            }
        }
    }


    @FXML
    private Button sendButton;
    @FXML
    private TextArea display;
    @FXML
    private TextField dataIn;

    private Client client;


    public void setClient(Client client){
        this.client = client;
        this.client.setStartGameController(this);
        dataIn.requestFocus();
        display.setEditable(false);
    }


    public void sendMessages(ActionEvent event) {
        client.b_sendActionPerformed(dataIn.getText());
    }

    public void logOutButton(ActionEvent event) {
        client.b_disconnectActionPerformed();
    }



    public TextArea getDisplay() {
        return display;
    }


    public TextField getDataIn() {
        return dataIn;
    }

}

