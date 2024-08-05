import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player;

    public Game(String boardFileName) {
        Scanner scanner = new Scanner(System.in);
        if (boardFileName != null) {
            loadGame(boardFileName);
        } else {
            System.out.println("Enter name for Player 1 (Black):");
            String player1 = scanner.nextLine();
            System.out.println("Enter name for Player 2 (White):");
            String player2 = scanner.nextLine();
            player = new Player(player1, player2);
            board = new Board(null);
            chooseStartingPosition();
        }
    }

    private void loadGame(String boardFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(boardFileName))) {
            String player1 = reader.readLine();
            String player2 = reader.readLine();
            char currentPlayer = reader.readLine().charAt(0);
            player = new Player(player1, player2);
            if (currentPlayer == 'W') {
                player.switchPlayer();
            }
            board = new Board(null);
            for (int i = 0; i < board.getSize(); i++) {
                String line = reader.readLine();
                for (int j = 0; j < board.getSize(); j++) {
                    board.getPosition(i, j).setPiece(line.charAt(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseStartingPosition() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a starting position:");
        System.out.println("1. Four-by-Four Centered Starting Position");
        System.out.println("2. Offset Starting Position");
        int choice = scanner.nextInt();
        if (choice == 1) {
            board.setFourByFourStartingPosition();
        } else if (choice == 2) {
            System.out.println("Choose an offset starting position:");
            System.out.println("1. Offset Position 1");
            System.out.println("2. Offset Position 2");
            System.out.println("3. Offset Position 3");
            System.out.println("4. Offset Position 4");
            int offsetChoice = scanner.nextInt();
            board.setOffsetStartingPosition(offsetChoice);
        }
    }

    public boolean isValidMove(int row, int col, char playerPiece) {
        return board.getPosition(row, col).canPlay(row, col, playerPiece, board);
    }

    public void flipTokens(int row, int col, char playerPiece) {
        char opponentPiece = (playerPiece == 'B') ? 'W' : 'B';
        int[] directions = {-1, 0, 1};
        for (int dRow : directions) {
            for (int dCol : directions) {
                if (dRow == 0 && dCol == 0) continue;
                int r = row + dRow, c = col + dCol;
                boolean hasOpponentPiece = false;
                while (r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize() && board.getPosition(r, c).getPiece() == opponentPiece) {
                    hasOpponentPiece = true;
                    r += dRow;
                    c += dCol;
                }
                if (hasOpponentPiece && r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize() && board.getPosition(r, c).getPiece() == playerPiece) {
                    r = row + dRow;
                    c = col + dCol;
                    while (board.getPosition(r, c).getPiece() == opponentPiece) {
                        board.getPosition(r, c).setPiece(playerPiece);
                        r += dRow;
                        c += dCol;
                    }
                }
            }
        }
    }

    public boolean hasValidMove(char playerPiece) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (isValidMove(i, j, playerPiece)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void takeTurn() {
        Scanner scanner = new Scanner(System.in);
        String currentPlayerName = player.getCurrentPlayerName();
        char currentPlayer = player.getCurrentPlayer();
        System.out.println(currentPlayerName + " (" + currentPlayer + "), enter your move (row and column) or type 'save', 'concede', 'forfeit', or 'exit':");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("save")) {
            saveGame();
            System.out.println("Game saved.");
        } else if (input.equalsIgnoreCase("concede")) {
            System.out.println(currentPlayerName + " has conceded. Game over.");
            System.exit(0);
        } else if (input.equalsIgnoreCase("forfeit")) {
            if (!hasValidMove(currentPlayer)) {
                System.out.println(currentPlayerName + " has no valid moves and forfeits the turn.");
                player.switchPlayer();
            } else {
                System.out.println("You have valid moves. You cannot forfeit.");
            }
        } else if (input.equalsIgnoreCase("exit")) {
            System.out.println("Game over.");
            displayWinner();
            System.exit(0);
        } else {
            try {
                String[] parts = input.split(" ");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                if (isValidMove(row, col, currentPlayer)) {
                    board.getPosition(row, col).setPiece(currentPlayer);
                    flipTokens(row, col, currentPlayer);
                    player.switchPlayer();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    public void saveGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name to save the game:");
        String fileName = scanner.nextLine();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(player.getPlayer1() + "\n");
            writer.write(player.getPlayer2() + "\n");
            writer.write(player.getCurrentPlayer() + "\n");
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    writer.write(board.getPosition(i, j).getPiece());
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void game() {
        boolean gameRunning = true;
        while (gameRunning) {
            board.drawBoard();
            if (hasValidMove(player.getCurrentPlayer())) {
                takeTurn();
            } else {
                System.out.println("No valid moves for " + player.getCurrentPlayer() + ". Skipping turn.");
                player.switchPlayer();
                if (!hasValidMove(player.getCurrentPlayer())) {
                    gameRunning = false;
                    displayWinner();
                }
            }
        }
    }

    public void displayWinner() {
        int blackCount = 0;
        int whiteCount = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                char piece = board.getPosition(i, j).getPiece();
                if (piece == 'B') {
                    blackCount++;
                } else if (piece == 'W') {
                    whiteCount++;
                }
            }
        }
        System.out.println("Final Board State:");
        board.drawBoard();
        if (blackCount > whiteCount) {
            System.out.println(player.getPlayer1() + " (Black) wins with " + blackCount + " pieces");
        } else if (whiteCount > blackCount) {
            System.out.println(player.getPlayer2() + " (White) wins with " + whiteCount + " pieces");
        } else {
            System.out.println("It's a tie!");
        }
    }
}