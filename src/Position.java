
public class Position {
    private char piece;
    public final char empty = '.';
    public final char black = 'B';
    public final char white = 'W';

    public Position() {
        this.piece = empty;
    }

    public boolean canPlay(int row, int col, char playerPiece, Board board) {
        if (piece != empty) {
            return false;
        }
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
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPlayable() {
        return piece == empty;
    }

    public char getPiece() {
        return piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }
}