import java.util.Scanner;

public class MainClass {
    public static void main(String args[]) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the ip of the server to connect...");
//        String ip = scanner.next();
//        System.out.println("Enter the port:");
//        int port = scanner.nextInt();
        System.out.println("Enter player 1 name...");
        String player1Name = scanner.next();
        System.out.println("Enter player 2 name...");
        String player2Name = scanner.next();
        TicTacToe game = new TicTacToe("New Game", player1Name, player2Name, "localhost", 8888);
        game.run();
        game.end();
        scanner.close();
    }
}
