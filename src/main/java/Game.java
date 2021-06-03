package main.java;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Class containing all the information about the state of the game.
 * Starts all the component's threads.
 */
public class Game {
    public Board board;
    public boolean isOver = false;
    public int score = 0;
    public int snakeDirection = 38;
    public int prevSnakeDirection = 38;
    public int pythonDirection = 38;
    final Semaphore processed = new Semaphore(4, true);
    final Lock renderLock = new ReentrantLock();
    final Condition rendered  = renderLock.newCondition();

    Thread frogThread;
    Thread fruitThread;
    Thread snakeThread;
    Thread pythonThread;

    /**
     * Creates new game instance.
     * Initializes board.
     * @see Board
     */
    public Game() {
        this.board = new Board(60, 60);
    }
    
    /**
     * Prepares game for running.
     * Creates threads and spawns components.
     */
    public void initialize() {
        Fruit fruit = new Fruit(this);
        Frog frog = new Frog(this);
        Snake snake = new Snake(this);
        Python python = new Python(this);

        this.fruitThread = new Thread(fruit, "fruit");
        this.frogThread = new Thread(frog, "frog");
        this.snakeThread = new Thread(snake, "snake");
        this.pythonThread = new Thread(python, "python");

        board.spawnObstacles(5);
        snake.spawn();
        python.spawn();
        fruit.spawn();
        frog.spawn();
    }

    /**
     * Starts game by starting components' threads.
     */
    public void start() {        
        this.fruitThread.start();
        this.frogThread.start();
        this.snakeThread.start();
        this.pythonThread.start();
    }
}
