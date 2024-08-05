import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    private String name;
    private Position[][] board;
    private final int size = 8;

    public Board(String name) {
        this.name = name;
        board = new Position[size][size];
        if (name != null) {
            initializeBoardFromSave();
        } else {
            initializeEmptyBoard();
        }
    }

    private void initializeBoardFromSave() {
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            for (int i = 0; i < size; i++) {
                String line = br.readLine();
                for (int j = 0; j < size; j++) {
                    char piece = line.charAt(j);
                    if (piece == '*') {
                        board[i][j] = new UnplayablePosition();
                    } else {
                        Position position = new Position();
                        position.setPiece(piece);
                        board[i][j] = position;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeEmptyBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Position();
            }
        }
        // Set initial pieces for a new game (example for Othello/Reversi)
        board[3][3].setPiece('W');
        board[3][4].setPiece('B');
        board[4][3].setPiece('B');
        board[4][4].setPiece('W');
        // Set unplayable positions
        board[3][7] = new UnplayablePosition();
        board[4][7] = new UnplayablePosition();
    }

    public void setFourByFourStartingPosition() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setPiece('.');
            }
        }
        board[2][2].setPiece('W');
        board[2][3].setPiece('W');
        board[3][2].setPiece('W');
        board[3][3].setPiece('W');
        board[4][4].setPiece('W');
        board[4][5].setPiece('W');
        board[5][4].setPiece('W');
        board[5][5].setPiece('W');
        board[2][4].setPiece('B');
        board[2][5].setPiece('B');
        board[3][4].setPiece('B');
        board[3][5].setPiece('B');
        board[4][2].setPiece('B');
        board[4][3].setPiece('B');
        board[5][2].setPiece('B');
        board[5][3].setPiece('B');
        board[3][7].setPiece('*');
        board[4][7].setPiece('*');
    }

    public void setOffsetStartingPosition(int choice) {
        switch (choice) {
            case 1:
                setOffsetPosition1();
                break;
            case 2:
                setOffsetPosition2();
                break;
            case 3:
                setOffsetPosition3();
                break;
            case 4:
                setOffsetPosition4();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Offset Position 1.");
                setOffsetPosition1();
                break;
        }
    }

    private void setOffsetPosition1() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setPiece('.');
            }
        }
        board[2][2].setPiece('W');
        board[2][3].setPiece('B');
        board[3][2].setPiece('B');
        board[3][3].setPiece('W');
        board[3][7].setPiece('*');
        board[4][7].setPiece('*');
    }

    private void setOffsetPosition2() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setPiece('.');
            }
        }
        board[2][4].setPiece('W');
        board[2][5].setPiece('B');
        board[3][4].setPiece('B');
        board[3][5].setPiece('W');
        board[3][7].setPiece('*');
        board[4][7].setPiece('*');
    }

    private void setOffsetPosition3() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setPiece('.');
            }
        }
        board[4][2].setPiece('W');
        board[4][3].setPiece('B');
        board[5][2].setPiece('B');
        board[5][3].setPiece('W');
        board[3][7].setPiece('*');
        board[4][7].setPiece('*');
    }

    private void setOffsetPosition4() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setPiece('.');
            }
        }
        board[4][4].setPiece('W');
        board[4][5].setPiece('B');
        board[5][4].setPiece('B');
        board[5][5].setPiece('W');
        board[3][7].setPiece('*');
        board[4][7].setPiece('*');
    }

    public void drawBoard() {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                char piece = board[i][j].getPiece();
                if (board[i][j].isPlayable()) {
                    System.out.print(piece + " ");
                } else {
                    System.out.print(piece + " ");
                }
            }
            System.out.println();
        }
    }

    public Position getPosition(int row, int col) {
        return board[row][col];
    }

    public int getSize() {
        return size;
    }
}