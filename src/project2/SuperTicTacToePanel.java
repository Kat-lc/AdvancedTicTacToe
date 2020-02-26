package project2;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToePanel extends JPanel {

    private JButton[][] board;
    private Cell[][] iBoard;

    private ImageIcon xIcon;
    private ImageIcon oIcon;
    private ImageIcon emptyIcon;

    private int boardSize;
    private int connections;
    private String choice;
    private int winCount;
    private int loseCount;

    private JButton quitButton;
    private JButton undoButton;

    private JPanel top;
    private JPanel bottom;
    private JPanel center;

    private ButtonListener listener;

    private JLabel winnerCountLabel;
    private JLabel loserCountLabel;

    private SuperTicTacToeGame game;

    public SuperTicTacToePanel() {

        winCount = 0;
        loseCount = 0;

        quitButton = new JButton("Quit");
        undoButton = new JButton("Undo");

        xIcon = new ImageIcon ("./src/project2/x.jpg");
        oIcon = new ImageIcon ("./src/project2/o.jpg");
        emptyIcon = new ImageIcon ("./src/project2/empty.jpg");

        top = new JPanel();
        bottom = new JPanel();
        center = new JPanel();

        // create game, listeners
        listener = new ButtonListener();

        winnerCountLabel = new JLabel("Winner: 0");
        loserCountLabel = new JLabel("Loser: 0");

        getUserInput();

        game = new SuperTicTacToeGame(this.boardSize, this.connections, this.choice);

        bottom.setLayout(new GridLayout(this.boardSize,this.boardSize,this.boardSize,2));

        Dimension temp = new Dimension(60,60);
        board = new JButton[this.boardSize][this.boardSize];

        for (int row = 0; row < this.boardSize; row++)
            for (int col = 0; col < this.boardSize; col++) {

                Border thickBorder = new LineBorder(Color.blue, 2);

                board[row][col] = new JButton ("", emptyIcon);
                board[row][col].setPreferredSize(temp);
                board[row][col].setBorder(thickBorder);

                board[row][col].addActionListener(listener);
                bottom.add(board[row][col]);
            }

        displayBoard();

        center.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridx = 0;
        position.gridy = 0;
        center.add(undoButton, position);

        position.gridx = 1;
        center.add(quitButton, position);

        position.gridy = 1;
        position.gridx = 0;
        center.add(winnerCountLabel, position);

        position.gridx = 1;
        center.add(loserCountLabel, position);

        top.add(new JLabel("!!!!!!  Super TicTacToe  !!!!"), BorderLayout.PAGE_START);
        add(top, BorderLayout.PAGE_START);
        top.setPreferredSize(new Dimension(800, 30));
        add(center, BorderLayout.CENTER);
        center.setPreferredSize(new Dimension(800, 60));
        add(bottom, BorderLayout.PAGE_END);

        undoButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    private void displayBoard() {
        iBoard = game.getBoard();

        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++) {

                board[r][c].setIcon(emptyIcon);
                if (iBoard[r][c] == Cell.O)
                    board[r][c].setIcon(oIcon);

                if (iBoard[r][c] == Cell.X)
                    board[r][c].setIcon(xIcon);
            }
    }

    private void getUserInput() {
        // Code below asks user for board size
        String userInputBoard = JOptionPane.showInputDialog(null,"Enter size of board: ");
        if(userInputBoard == null)
            System.exit(0);

        int intBoard = 3; // Default value

        int i = 1;
        while(i == 1) { // While loop breaks once there is correct user input.
            if(userInputBoard.matches("[0-9]+")) {  // Checks for integer values
                intBoard = Integer.parseInt(userInputBoard);
                if (intBoard > 2 && intBoard < 15)
                    i = 2;
                else
                    userInputBoard = JOptionPane.showInputDialog(null, "Invalid board size. Re-enter size of board: ");
                if(userInputBoard == null)
                    System.exit(0);
            } else
                userInputBoard = JOptionPane.showInputDialog(null, "Invalid input, integers only. Re-enter size of board: ");
            if(userInputBoard == null)
                System.exit(0);
        }
        this.boardSize = intBoard;

        // Code below asks user for number of connections
        String userInputConnections = JOptionPane.showInputDialog(null,"Enter number of connections: ");
        if(userInputConnections == null)
            System.exit(0);
        int intConnections = 3; // Default

        i = 1;
        while(i == 1) { // While loop breaks once there is correct user input.
            if(userInputConnections.matches("[0-9]+")) { // Makes sure it is an integer
                intConnections = Integer.parseInt(userInputConnections);
                if((intBoard > 3 && intConnections > 3) && intConnections <= intBoard) {
                    System.out.println(intConnections);
                    i = 2;
                }
                else if(intBoard == 3 && intConnections == 3)
                    i = 2;
                else {
                    userInputConnections = JOptionPane.showInputDialog(null, "Invalid number of connections. Re-enter number: ");
                    if(userInputConnections == null)
                        System.exit(0);
                }
            } else {
                userInputConnections = JOptionPane.showInputDialog(null, "Invalid user input, integers only. Re-enter number: ");
                if(userInputConnections == null)
                    System.exit(0);
            }
        }

        this.connections = intConnections;

        // Asks user to select X or O
        String[] choices = {"X", "O"};
        String userInputChoice = (String)JOptionPane.showInputDialog(null,"Who starts first? X or O:",
                "X or O",
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                choices[0]);
        if(userInputChoice == null)
            System.exit(0);

        this.choice = userInputChoice;
    }

    // This code essentially removes the three GUI panels and reinitializes them.
    private void resizeBoard() {
        remove(bottom);
        remove(center);

        remove(top);

        top = new JPanel();
        bottom = new JPanel();
        center = new JPanel();

        // create game, listeners
        listener = new ButtonListener();

        winnerCountLabel = new JLabel("Winner: " + getWins());
        loserCountLabel = new JLabel("Loser: " + getLosses());

        getUserInput();

        game = new SuperTicTacToeGame(this.boardSize, this.connections, this.choice);

        bottom.setLayout(new GridLayout(this.boardSize,this.boardSize,this.boardSize,2));

        Dimension temp = new Dimension(60,60);
        board = new JButton[this.boardSize][this.boardSize];

        for (int row = 0; row < this.boardSize; row++)
            for (int col = 0; col < this.boardSize; col++) {

                Border thickBorder = new LineBorder(Color.blue, 2);

                board[row][col] = new JButton ("", emptyIcon);
                board[row][col].setPreferredSize(temp);
                board[row][col].setBorder(thickBorder);

                board[row][col].addActionListener(listener);
                bottom.add(board[row][col]);
            }

        displayBoard();

        center.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridx = 0;
        position.gridy = 0;
        center.add(undoButton, position);

        position.gridx = 1;
        center.add(quitButton, position);

        position.gridy = 1;
        position.gridx = 0;
        center.add(winnerCountLabel, position);

        position.gridx = 1;
        center.add(loserCountLabel, position);

        top.add(new JLabel("!!!!!!  Super TicTacToe  !!!!"), BorderLayout.PAGE_START);
        add(top, BorderLayout.PAGE_START);
        top.setPreferredSize(new Dimension(800, 60));
        add(center, BorderLayout.CENTER);
        center.setPreferredSize(new Dimension(800, 60));
        add(bottom, BorderLayout.PAGE_END);
        undoButton.addActionListener(listener);

        quitButton.addActionListener(listener);

        revalidate();
    }

    public int getBoardSize() { return boardSize; }

    public void countWins() {
        this.winCount += 1;
    }

    public void countLosses() {
        this.loseCount += 1;
    }

    public int getWins() {
        return this.winCount;
    }

    public int getLosses() {
        return this.loseCount;
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int theBoardSize = getBoardSize();
            for (int r = 0; r < theBoardSize; r++)
                for (int c = 0; c < theBoardSize; c++)
                    if (board[r][c] == e.getSource()) {
                        game.select(r, c);
                    }
            if(e.getSource() == quitButton) {
                game.quit();
            }

            if(e.getSource() == undoButton) {
                game.undo();
            }

            displayBoard();

            if (game.getGameStatus() == GameStatus.X_WON) {
                JOptionPane.showMessageDialog(null, "X won and O lost!\n The game will reset");
                countWins();
                resizeBoard();
                game.reset();
                displayBoard();
            }

            if (game.getGameStatus() == GameStatus.O_WON) {
                JOptionPane.showMessageDialog(null, "O won and X lost!\n The game will reset");
                countLosses();
                resizeBoard();
                game.reset();
                displayBoard();
            }

            if (game.getGameStatus() == GameStatus.CATS) {
                JOptionPane.showMessageDialog(null, "Neither player wins!\n The game will reset");
                resizeBoard();
                game.reset();
                displayBoard();
            }

        }
    }
}