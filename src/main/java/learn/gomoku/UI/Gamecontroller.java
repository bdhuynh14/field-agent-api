package learn.gomoku.UI;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Gamecontroller {
    private final Scanner sc = new Scanner(System.in);
    Gomoku game;
    char[][] board = new char[15][15];
    private static Player player1;
    private static Player player2;


    public void run() {
        System.out.println("Welcome to Gomoku");
        System.out.println("=".repeat(14));

        boolean end = false;

        while (end == false) {
            getPlayer();
            this.game = new Gomoku(player1, player2);
            ArrayList<Stone> stones = new ArrayList<>();
            char[][] board = new char[15][15];
            System.out.println("Randomizing the game...Please wait");
            System.out.println(game.getCurrent().getName() + " will go first");
            play();
            playAgain();
        }
    }

    private void getPlayer() {
        System.out.println("Player 1 is: \n" +
                "1. Human\n" +
                "2. Random Player \n" +
                "Select [1-2]: ");
        int input1 = Integer.parseInt(sc.nextLine());
        if (input1 == 1) {
            String name = readRequiredString("Player 1 Name: ");
            player1 = new HumanPlayer(name);
        } else {
            player1 = new RandomPlayer();
            System.out.println("Player 1 is: " + player1.getName());
        }
        System.out.println("PLayer 2 is: \n" +
                "1. Human\n" +
                "2. Random Player\n" +
                "Select [1-2]: ");
        int input2 = Integer.parseInt(sc.nextLine());
        if (input2 == 1) {
            String name = readRequiredString("Player 2 Name:");
            player2 = new HumanPlayer(name);
        } else {
            player2 = new RandomPlayer();
            System.out.println("Opponent player (Player 2) is: " + player2.getName());
        }
    }

    private void play() {
        do {
            Stone Gamestone = game.getCurrent().generateMove(game.getStones());
            Result result = null;
            if (Gamestone == null) {
                int Boardrow = readInt("Choose a row co-ordinate from 1-15", 1, 15);
                int Boardcol = readInt("Choose a column co-ordinate from 1-15", 1, 15);
                Gamestone = new Stone(Boardrow - 1, Boardcol - 1, game.isBlacksTurn());
                result = game.place(Gamestone);
            } else {
                result = game.place(Gamestone);
            }
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }
            printBoard();
        } while (!game.isOver());
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println(winner.getName() + " has won this game!");
            System.out.println("*".repeat(14));
        }
    }

    private void printBoard() {
        printColumn();
        System.out.println("");
        printRow();
    }

    private void printColumn() {
        System.out.println("  ");
        for (int columnIndex = 0; columnIndex < Gomoku.WIDTH; columnIndex++) {
            System.out.printf(" %02d", columnIndex + 1);
        }
    }

    private void printRow() {
        for (int Boardrow = 0; Boardrow < Gomoku.WIDTH; Boardrow++) {
            for (int Boardcol = 0; Boardcol < Gomoku.WIDTH; Boardcol++) {
                board[Boardrow][Boardcol] = '-';
            }
        }
        for (Stone stone : this.game.getStones()) {
            board[stone.getRow()][stone.getColumn()] = stone.isBlack() ? '$' : '#';
        }
        for (int Boardrow = 0; Boardrow < Gomoku.WIDTH; Boardrow++) {
            System.out.printf("%02d ", (Boardrow + 1));
            for (int Boardcol = 0; Boardcol < Gomoku.WIDTH; Boardcol++) {
                System.out.print(" " + board[Boardrow][Boardcol] + " ");
            }
            System.out.println();

        }
    }

    private String readRequiredString(String message) {
        System.out.println(message);
        String Playerinput = sc.nextLine();
        while (Playerinput.equals("")) {
            System.out.println("This is not a valid entry. Try again.");
            Playerinput = sc.nextLine();
        }
        return Playerinput;
    }

    private int readInt(String message, int row, int col) {
        String Playerinput = readRequiredString(message);
        int playerPosition = Integer.parseInt(Playerinput);
        while (playerPosition < row || playerPosition > col) {
            Playerinput = readRequiredString("This is outside of the boundaries of the game. Try again.");
            playerPosition = Integer.parseInt(Playerinput);
        }
        return playerPosition;
    }

    private boolean playAgain() {
        Scanner console = new Scanner(System.in);
        String Playerinput = readRequiredString("Would you like to play again? [y/n]: ");
        boolean response = false;
        if (Playerinput.equalsIgnoreCase("y")) {
            response = true;
        } else if (Playerinput.equalsIgnoreCase("n")) {
            response = false;
        } else {
            System.out.println("Enter y or n below");
            playAgain();
        }
        return response;
    }
}