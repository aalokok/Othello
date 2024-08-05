public class UnplayablePosition extends Position {
    public final char unplayable = '*';

    @Override
    public boolean canPlay(int row, int col, char playerPiece, Board board) {
        return false;
    }

    @Override
    public boolean isPlayable() {
        return false;
    }

    @Override
    public char getPiece() {
        return unplayable;
    }
}