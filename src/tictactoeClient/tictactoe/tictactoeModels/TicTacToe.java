package tictactoeClient.tictactoe.tictactoeModels;

public class TicTacToe {

    //turn and player
    private String player = "";
    private String draw = "";
    private boolean checkWin = false;
    private boolean checkWin2 = false;

    //database of each player
    private int[][] tables = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    private int moveCount1 = 0;
    private int moveCount2 = 0;


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getTables(int x, int y) {
        return tables[x][y];
    }

    public void setMoveCount1() {
        this.moveCount1++;
    }

    public void setMoveCount2() {
        this.moveCount2++;
    }

    public boolean isCheckWin() {
        return checkWin;
    }

    public boolean isCheckWin2() {
        return checkWin2;
    }

    public String getDraw() {
        return draw;
    }

    public boolean check1(int x, int y, int[][] p) {
        //check row
        for (int i = 0; i < tables.length; i++) {
            if (p[x][i] != tables[x][i]) {
                break;
            } else if (i == tables.length - 1) {
                checkWin = true;
                break;
            }
        }

        //check column
        for (int i = 0; i < tables.length; i++) {
            if (p[i][y] != tables[i][y]) {
                break;
            } else if (i == tables.length - 1) {
                checkWin = true;
                break;
            }
        }

        //check diagonal
        if (x == y) {
            for (int i = 0; i < tables.length; i++) {
                if (p[i][i] != tables[i][i]) {
                    break;
                } else if (i == tables.length - 1) {
                    checkWin = true;
                    break;
                }
            }
        }

        //check anti diagonal
        if (x + y == tables.length - 1) {
            for (int i = 0; i < tables.length; i++) {
                if (p[i][(tables.length - 1) - i] != tables[i][(tables.length - 1) - i]) {
                    break;
                } else if (i == tables.length - 1) {
                    checkWin = true;
                    break;
                }
            }
        }

        //check draw
        if (moveCount1 >= 5) {
            draw = "Draw";
        }
        return checkWin;
    }


    public boolean check2(int x, int y, int[][] p2) {
        //check row
        for (int i = 0; i < tables.length; i++) {
            if (p2[x][i] != tables[x][i]) {
                break;
            } else if (i == tables.length - 1) {
                checkWin2 = true;
                break;
            }
        }

        //check column
        for (int i = 0; i < tables.length; i++) {
            if (p2[i][y] != tables[i][y]) {
                break;
            } else if (i == tables.length - 1) {
                checkWin2 = true;
                break;
            }
        }

        //check diagonal
        if (x == y) {
            for (int i = 0; i < tables.length; i++) {
                if (p2[i][i] != tables[i][i]) {
                    break;
                } else if (i == tables.length - 1) {
                    checkWin2 = true;
                    break;
                }
            }
        }

        //check anti diagonal
        if (x + y == tables.length - 1) {
            for (int i = 0; i < tables.length; i++) {
                if (p2[i][(tables.length - 1) - i] != tables[i][(tables.length - 1) - i]) {
                    break;
                } else if (i == tables.length - 1) {
                    checkWin2 = true;
                    break;
                }
            }
        }

        //check draw
        if (moveCount2 >= 5) {
            draw = "Draw";
        }
        return checkWin2;
    }
}
