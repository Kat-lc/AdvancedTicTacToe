package project2;

import javax.swing.*;

public class SuperTicTacToe {

    private static int size;
    private static int timeLimit;

    public static void main(String[] args) {
        JFrame frame = new JFrame ("Super Tic Tac Toe!");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        SuperTicTacToePanel panel = new SuperTicTacToePanel();

        frame.getContentPane().add(panel);

        frame.setSize(800,800);
        frame.setVisible(true);
    }
}