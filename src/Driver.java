import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start a new game or load from a save file? (new/load):");
        String choice = scanner.nextLine();

        String boardFileName = null;
        if (choice.equalsIgnoreCase("load")) {
            System.out.println("Enter the save file name:");
            boardFileName = scanner.nextLine();
        }

        Game game = new Game(boardFileName);
        game.game();
    }
}