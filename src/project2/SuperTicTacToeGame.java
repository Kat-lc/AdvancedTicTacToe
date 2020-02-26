package project2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*********************************************************************
 * A TicTacToe game with a resizeable board that features an AI to
 * play against.
 *
 * @author Katie Cussans, Jason Kaip
 * @version Fall 2019
 ********************************************************************/

public class SuperTicTacToeGame {
    /** Array containing the cells for the board*/
    private Cell[][] board;

    /** Current status of game */
    private GameStatus status;

    /** Current status of who's turn it is */
    private Cell turn;

    /** Who starts first */
    private Cell startTurn;

    /** How many connections must be made to win */
    private int connections;

    /** The row and column size of the board */
    private int boardSize;

    int aiRow = 0;
    int aiCol = 0;

    boolean played = false;

    private static final boolean AI = true;

    private ArrayList<Point> backup = new ArrayList<Point>();

    Random rand = new Random();

    /*********************************************************************
     * Constructor that sets the board size, connections, and starting player.
     *
     * @param boardSize int Size of the board
     * @param connections int The number of connections needed to win
     * @param inputTurn string Who is taking their turn first
     ********************************************************************/
    public SuperTicTacToeGame(int boardSize, int connections, String inputTurn) {
        this.boardSize = boardSize;
        this.connections = connections;

        // Checks what the beginning turn is based on inputTurn
        if (inputTurn.equals("O")) {
            this.startTurn = Cell.O;
        } else
            this.startTurn = Cell.X;

        status = GameStatus.IN_PROGRESS;
        board = new Cell[this.boardSize][this.boardSize];
        reset();

        if (turn == Cell.O)
            AI();
    }

    /*********************************************************************
     * Get's the board
     *
     * @return board Returns the current board
     ********************************************************************/
    public Cell[][] getBoard() {
        return board;
    }

    /*********************************************************************
     * Selects the cell using the row and column numbers and also
     * calls the AI
     *
     * @param row int The row number to select
     * @param col int The column number to select
     * @return none
     ********************************************************************/
    public void select(int row, int col) {
        if (board[row][col] != Cell.EMPTY)
            return;

        backup.add(new Point(row, col));

        // Sets the turn to the starting turn. Only runs when it's the first move.
        if (backup.size() - 1 == 0 && this.startTurn == Cell.O)
            turn = Cell.O;
        else if (backup.size() - 1 == 0 && this.startTurn == Cell.X)
            turn = Cell.X;

        board[row][col] = turn;

        turn = (turn == Cell.O) ? Cell.X : Cell.O;
        status = isWinner();

        //Calls the AI if conditions are met
        if(turn == Cell.O && status == GameStatus.IN_PROGRESS)
            AI();
    }

    /*********************************************************************
     * After every move checks the number of connections and compares
     * that to what is needed to win
     *
     * @return GameStatus.X_WON Returns the appropriate GameStatus if X wins
     * @return GameStatus.O_WON Returns the appropriate GameStatus if O wins
     * @return GameStatus.CATS Returns the appropriate GameStatus if is Cats game
     * @return GameStatus.IN_PROGRESS Returns the appropriate GameStatus
     * if the number of connections needed to win have not been made and
     * there are open cells on the board
     ********************************************************************/
    private GameStatus isWinner() {
        // This bound prevents certain 'for' loops from going index out of bounds.
        int boardSizeBounds = this.boardSize - (this.connections - 1);
        int check = 0;

        // Checks if X won horizontally
        // Loops and increments 'check'. Returns a win if check equals number of connections
        for (int r = 0; r < this.boardSize; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r][c + i] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if X won vertically
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = 0; c < this.boardSize; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if X won diagonally: positive downwards.
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c + i] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if X won diagonally, negatively upwards
        for (int r = (connections - 1); r < boardSize; r++)
            for (int c = (connections - 1); c < boardSize; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r - i][c - i] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if X won diagonally, positively upwards
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = (connections - 1); c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c - i] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if X won diagonally, negatively downwards
        for (int r = (connections - 1); r < boardSize; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r - i][c + i] == Cell.X)
                        check++;
                if (check == this.connections) {
                    return GameStatus.X_WON;
                }else
                    check = 0;
            }

        // Checks if O won horizontally
        for (int r = 0; r < this.boardSize; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r][c + i] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if O won vertically
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = 0; c < this.boardSize; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if O won diagonally: positive downwards.
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c + i] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if O won diagonally, negatively upwards
        for (int r = (connections - 1); r < boardSize; r++)
            for (int c = (connections - 1); c < boardSize; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r - i][c - i] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if O won diagonally, positively upwards
        for (int r = 0; r < boardSizeBounds; r++)
            for (int c = (connections - 1); c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r + i][c - i] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if O won diagonally, negatively downwards
        for (int r = (connections - 1); r < boardSize; r++)
            for (int c = 0; c < boardSizeBounds; c++) {
                for (int i = 0; i < this.connections; i++)
                    if (board[r - i][c + i] == Cell.O)
                        check++;
                if (check == this.connections) {
                    return GameStatus.O_WON;
                }else
                    check = 0;
            }

        // Checks if game is CATS, or no spots left.
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c] != Cell.EMPTY)
                    check++;
            }
        if (check == (Math.pow(boardSize, 2))) {
            System.out.println(check);
            return GameStatus.CATS;
        }


        return GameStatus.IN_PROGRESS;
    }

    /*********************************************************************
     * Get's the game status
     *
     * @return status Returns the current status of the game
     ********************************************************************/
    public GameStatus getGameStatus() {
        return status;
    }

    /*********************************************************************
     * Resets the board and sets the turn to X
     *
     * @return none
     ********************************************************************/
    public void reset() {
        for (int r = 0; r < this.boardSize; r++)
            for (int c = 0; c < this.boardSize; c++)
                board[r][c] = Cell.EMPTY;

        turn = this.startTurn;
        if(turn == Cell.O)
            AI();
    }

    /*********************************************************************
     * Undos the last two moves made
     *
     * @return none
     ********************************************************************/
    public void undo() {
        int sizeOfArrayList = backup.size();

        if (sizeOfArrayList - 1 > 0) {
            Point pointToUndo1 = backup.get(sizeOfArrayList - 1);
            int row = pointToUndo1.x;
            int col = pointToUndo1.y;

            Point pointToUndo2 = backup.get(sizeOfArrayList - 2);
            int row2 = pointToUndo2.x;
            int col2 = pointToUndo2.y;

            board[row][col] = Cell.EMPTY;
            board[row2][col2] = Cell.EMPTY;

            backup.remove(sizeOfArrayList - 1);
            backup.remove(sizeOfArrayList - 2);
        } else {
            System.out.println("No more moves to undo");
        }
    }

    /*********************************************************************
     * Terminates the program
     *
     * @return none
     ********************************************************************/
    public void quit() {
        System.exit(0);
    }

    /*********************************************************************
     * Asks if a certain cell is able to be selected without issue
     *
     * @param r int The row number
     * @param c int The column number
     * @returns true The cell indicated by r and c is within the
     * confines of the board and the cell is empty.
     * @returns false The cell specified is outside of the board or the
     * cell is already occupied
     ********************************************************************/
    public boolean validMove(int r, int c) {
        if ((r >= 0 && r < boardSize) && (c >= 0 && c < boardSize))
            if (board[r][c] == Cell.EMPTY)
                return true;
            else
                return false;
        else
            return false;
    }

    /*********************************************************************
     * Asks if a certain cell exists in the board.
     *
     * @param r int The row number
     * @param c int The column number
     * @returns true The cell indicated by r and c is within the
     * confines of the board
     * @returns false The cell specified is outside of the board
     ********************************************************************/
    public boolean withinBounds(int r, int c) {
        if ((r >= 0 && r < boardSize) && (c >= 0 && c < boardSize))
            return true;
        else
            return false;
    }

    /*********************************************************************
     * Asks if the player will eventually win based on the direction
     * they are headed with their connections and assigns a
     * counter-play to two variables.
     *
     * @returns true The the player can win within a few moves
     * @returns false The player cannot win within a few moves
     ********************************************************************/
    public boolean canOpponentWin() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c] == Cell.X) {
                    int horizontal = 0;
                    int vertical = 0;
                    int diagNeg = 0;
                    int diagPos = 0;
                    //Checks horizontal axis
                    for (int x = 1; x <= connections; x++) {
                        if (withinBounds(r, c + x))
                            if (board[r][c + x] == Cell.X) {
                                horizontal++;
                                if (horizontal == connections-3) {
                                    if (validMove(r, (c + x) + 1)) {
                                        aiRow = r;
                                        aiCol = (c + x) + 1;
                                        System.out.println("Horizontal +");
                                        return true;
                                    } else if (validMove(r, c - x)) {
                                        aiRow = r;
                                        aiCol = (c - x);
                                        System.out.println("Horizontal -");
                                        return true;
                                    }
                                }
                            }

                        //Checks vertical axis
                        if (withinBounds(r + x, c))
                            if (board[r + x][c] == Cell.X) {
                                vertical++;
                                if (vertical == connections-3) {
                                    if (validMove((r + x) + 1, c)) {
                                        aiRow = (r + x) + 1;
                                        aiCol = c;
                                        System.out.println("Vertical -");
                                        return true;
                                    } else if (validMove(r - x, c)) {
                                        aiRow = r - x;
                                        aiCol = c;
                                        System.out.println("Vertical +");
                                        return true;
                                    }
                                }
                            }

                        //Checks Diagonal-Negative
                        if (withinBounds(r + x, c + x))
                            if (board[r + x][c + x] == Cell.X) {
                                diagNeg++;
                                if (diagNeg == connections-3) {
                                    if (validMove((r + x) + 1, (c + x) + 1)) {
                                        aiRow = (r + x) + 1;
                                        aiCol = (c + x) + 1;
                                        System.out.println("Diag Neg +");
                                        return true;
                                    } else if (validMove(r - x, c - x)) {
                                        aiRow = r - x;
                                        aiCol = c - x;
                                        System.out.println("Diag Neg -");
                                        return true;
                                    }
                                }
                            }

                        //Checks Diagonal-Positive
                        if (withinBounds(r - x, c + x))
                            if (board[r - x][c + x] == Cell.X) {
                                diagPos++;
                                if (diagPos == connections-3) {
                                    if (validMove((r - x) - 1, (c + x) + 1)) {
                                        aiRow = (r - x) - 1;
                                        aiCol = (c + x) + 1;
                                        System.out.println("Diag Pos +");
                                        return true;
                                    } else if (validMove(r + x, c - x)) {
                                        aiRow = r + x;
                                        aiCol = c - x;
                                        System.out.println("Diag Pos -");
                                        return true;
                                    }
                                }
                            }
                    }
                }
            }
        }
        return false;
    }

    /*********************************************************************
     * The AI attempts to make a smart move by forming as many horizontal
     * or vertical connections as possible
     *
     * @param r The row number
     * @param c The column number
     * @returns canAIWin(r, c+1) The AI owns the cell defined by r and c
     * @returns canAIWin(r+1, c-1) The opponent owns the cell defined by r and c
     * @returns true An empty cell was found and a smart play can be made
     * @returns false The cell defined by r and c is out of bounds
     ********************************************************************/
    public boolean canAIWin(int r, int c) {
        if (withinBounds(r, c))
            if (board[r][c] == Cell.O)
                return canAIWin(r, c + 1);
            else if (board[r][c] == Cell.EMPTY) {
                aiRow = r;
                aiCol = c;
                return true;
            }
            else
                return canAIWin(r + 1, c - 1);
        else
            return false;
    }

    /*********************************************************************
     * If the opponent has the possibility of winning within a few moves
     * a counter-play will be executed. If the opponent does not have the
     * chance to win then the AI tries it's best to form a move to try to
     * win the game, if the AI fails at this then a random empty cell is chosen.
     ********************************************************************/
    public void AI() {
        if (canOpponentWin()) {
            if (validMove(aiRow, aiCol)) {
                select(aiRow, aiCol);
                return;
            }
        } else {
            //AI tries to win
            for (int r = 0; r < boardSize; r++) {
                for (int c = 0; c < boardSize; c++) {
                    if (canAIWin(r, c)) {
                        System.out.println("AI Attempting to win");
                        select(aiRow, aiCol);
                        return;
                    }
                }
            }
            //AI randomly selects a square somewhere
            played = false;
            while (!played) {
                int randR = rand.nextInt(boardSize);
                int randC = rand.nextInt(boardSize);
                if (validMove(randR, randC)) {
                    select(randR, randC);
                    System.out.println("Random");
                    played = true;
                }
            }
        }
    }
}