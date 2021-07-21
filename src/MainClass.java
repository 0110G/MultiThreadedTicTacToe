import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {
    public static void main(String args[]) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(Const.THREAD_POOL_COUNT);

        // Start instances
        executorService.execute(new TicTacToe("Game 1", "P1_1", "P2_1"));
        executorService.execute(new TicTacToe("Game 2", "P1_2", "P2_2"));
        executorService.execute(new TicTacToe("Game 3", "P1_3", "P2_3"));
        executorService.submit(new TicTacToe("", "", " ef"));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(10000);
        }
        TicTacToe.end();
    }
}
