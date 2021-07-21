import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe implements Runnable {

    private String gameInstanceName;
    private Player player1;
    private Player player2;
    private Board board;
    private int remainingSquares;
    private GameState gameState;
    private static final Scanner scanner = new Scanner(System.in);

    TicTacToe(String instanceName, String player1Name, String player2Name) {
        this.gameInstanceName = instanceName;
        this.player1 = new Player(player1Name, CellState.FILLED_PLAYER_1.getMark());
        this.player2 = new Player(player2Name, CellState.FILLED_PLAYER_2.getMark());
        this.board = new Board(Const.DEFAULT_DIMENSION);
        this.remainingSquares = board.getDimension() * board.getDimension();
        this.gameState = GameState.PLAYER1_CHANCE;
    }

    /** This method takes input from the user
     *  At any given time, only one thread can execute the method.
     */
    private static int[] input(String gameName, String playerName) {
        System.out.println("Started input() for instance: " + gameName + "Enter (row, col) player: " + playerName);
        int[] userInput = new int[]{-1, -1};
        try {
            if (scanner.hasNextInt()) { userInput[0] = scanner.nextInt(); }
            else { scanner.next(); }
            if (scanner.hasNextInt()) { userInput[1] = scanner.nextInt(); }
            else { scanner.next(); }
            //userInput[1] = scanner.nextInt();
        } catch (InputMismatchException ex) {
            // Do nothing
        }
        System.out.println("Returning input() for instance: " + Thread.currentThread().getName() +
                " with values: " + userInput[0] + " " + userInput[1]);
        return userInput;
    }

    /** This method draws the board on the console.
     * At any given time, only one thread can execute the given method.
     */
    private static void drawBoard(Board board, String gameName) {
        System.out.println("Started drawBoard() for instance: " + gameName);
        for (int i=0 ; i<board.getDimension() ; i++) {
            for (int j=0 ; j<board.getDimension() ; j++) {
                System.out.print(board.getStateAtPosition(i, j).getMark() + " |");
            }
            System.out.println();
        }
        System.out.println("Returning drawBoard() for instance: " + gameName);
    }

    private Player getWinner() {
        Player winner = Player.NULL_PLAYER;
        winner = checkRowsForWinner();
        if (!Player.NULL_PLAYER.equals(winner)) {return winner;}
        winner = checkColumnsForWinner();
        if (!Player.NULL_PLAYER.equals(winner)) {return winner;}
        winner = checkDiagonal1ForWinner();
        if (!Player.NULL_PLAYER.equals(winner)) {return winner;}
        winner = checkDiagonal2ForWinner();
        return winner;
    }

    private Player checkRowsForWinner() {
        for (int i=0 ; i<board.getDimension() ; i++) {
            Player rowWinner = checkRowForWinner(i);
            if (!Player.NULL_PLAYER.equals(rowWinner)) {
                return rowWinner;
            }
        }
        return Player.NULL_PLAYER;
    }

    private Player checkRowForWinner(int row) {
        int player1Marks = 0, player2Marks = 0;
        for (int i=0 ; i<board.getDimension() ; i++) {
            if (board.getStateAtPosition(row, i) == CellState.EMPTY) {
                return Player.NULL_PLAYER;
            }
            player1Marks += (board.getStateAtPosition(row, i) == CellState.FILLED_PLAYER_1 ? 1 : 0);
            player2Marks += (board.getStateAtPosition(row, i) == CellState.FILLED_PLAYER_2 ? 1 : 0);
        }
        if (player1Marks == board.getDimension()) {return player1;}
        if (player2Marks == board.getDimension()) {return player2;}
        return Player.NULL_PLAYER;
    }

    private Player checkColumnsForWinner() {
        for (int i=0 ; i<board.getDimension() ; i++) {
            Player columnWinner = checkColumnForWinner(i);
            if (!Player.NULL_PLAYER.equals(columnWinner)) {
                return columnWinner;
            }
        }
        return Player.NULL_PLAYER;
    }

    private Player checkColumnForWinner(int column) {
        int player1Marks = 0, player2Marks = 0;
        for (int i=0 ; i<board.getDimension() ; i++) {
            if (board.getStateAtPosition(i, column) == CellState.EMPTY) {
                return Player.NULL_PLAYER;
            }
            player1Marks += (board.getStateAtPosition(i, column) == CellState.FILLED_PLAYER_1 ? 1 : 0);
            player2Marks += (board.getStateAtPosition(i, column) == CellState.FILLED_PLAYER_2 ? 1 : 0);
        }
        if (player1Marks == board.getDimension()) {return player1;}
        if (player2Marks == board.getDimension()) {return player2;}
        return Player.NULL_PLAYER;
    }

    private Player checkDiagonal1ForWinner() {
        int player1Marks = 0, player2Marks = 0;
        for (int i=0 ; i<board.getDimension() ; i++) {
            player1Marks += (board.getStateAtPosition(i, i) == CellState.FILLED_PLAYER_1 ? 1 : 0);
            player2Marks += (board.getStateAtPosition(i, i) == CellState.FILLED_PLAYER_2 ? 1 : 0);
        }
        if (player1Marks == board.getDimension()) {return player1;}
        if (player2Marks == board.getDimension()) {return player2;}
        return Player.NULL_PLAYER;
    }

    private Player checkDiagonal2ForWinner() {
        int player1Marks = 0, player2Marks = 0;
        int dim = board.getDimension();
        for (int i=0 ; i<board.getDimension() ; i++) {
            player1Marks += (board.getStateAtPosition(i, dim-i-1) == CellState.FILLED_PLAYER_1 ? 1 : 0);
            player2Marks += (board.getStateAtPosition(i, dim-i-1) == CellState.FILLED_PLAYER_2 ? 1 : 0);
        }
        if (player1Marks == board.getDimension()) {return player1;}
        if (player2Marks == board.getDimension()) {return player2;}
        return Player.NULL_PLAYER;
    }

    private void gameLoop() {
        while (true) {
            switch (getGameState()) {
                case PLAYER1_CHANCE:
                    int[] inp = input(gameInstanceName, player1.getMark());    // Synchronous call
                    if (inp[0] < 0 || inp[1] < 0) { break;}
                    if (!board.setStateAtPosition(inp[0], inp[1], CellState.FILLED_PLAYER_1)) { break; }
                    drawBoard(board, gameInstanceName); // Synchronous
                    gameState = GameState.PLAYER2_CHANCE;   // parallel
                    remainingSquares--;
                    break;
                case PLAYER2_CHANCE:
                    inp = input(gameInstanceName, player2.getMark());
                    if (inp[0] < 0 || inp[1] < 0) {break;}
                    if (!board.setStateAtPosition(inp[0], inp[1], CellState.FILLED_PLAYER_2)) { break; }
                    drawBoard(board, gameInstanceName);
                    gameState = GameState.PLAYER1_CHANCE;
                    remainingSquares--;
                    break;
                case PLAYER1_WIN:
                    System.out.println(player1.getName() + " wins for " + gameInstanceName);
                    return;
                case PLAYER2_WIN:
                    System.out.println(player2.getName() + " wins for " + gameInstanceName);
                    return;
                case DRAW:
                    System.out.println("Draw for " + gameInstanceName);
                    return;
                default:
                    System.out.println("Error!");
                    return;
            }
        }
    }

    public static void end() {
        synchronized (scanner) {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public GameState getGameState() {
        Player winner = getWinner();
        if (player1.equals(winner)) {return GameState.PLAYER1_WIN;}
        if (player2.equals(winner)) {return GameState.PLAYER2_WIN;}
        if (this.remainingSquares <= 0) {
            return GameState.DRAW;
        } else {
            return gameState;
        }
    }

    @Override
    public void run() {
        gameLoop();
    }
}
