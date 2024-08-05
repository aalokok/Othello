public class Player {
    private String player1;
    private String player2;
    private char currentPlayer;

    public Player(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = 'B'; // Default starting player
    }

    public String getCurrentPlayerName() {
        return (currentPlayer == 'B') ? player1 : player2;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'B') ? 'W' : 'B';
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}