package main.java;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Game {
    public Board board;
    public boolean isOver;
    public int score;
    public int snakeDirection = 38;
    final Semaphore processed = new Semaphore(3, true);
    final Lock renderLock = new ReentrantLock();
    final Condition rendered  = renderLock.newCondition();

    Thread frogThread;
    Thread fruitThread;
    Thread snakeThread;

    public Game() {
        this.board = new Board(60, 60);
        this.isOver = false;
        this.score = 0;
    }
    
    public void initialize() {
        Fruit fruit = new Fruit(this);
        Frog frog = new Frog(this);
        Snake snake = new Snake(this);

        this.fruitThread = new Thread(fruit, "fruit");
        this.frogThread = new Thread(frog, "frog");
        this.snakeThread = new Thread(snake, "snake");

        board.spawnObstacles(5);
        snake.spawn();
        fruit.spawn();
        frog.spawn();
    }

    public void start() {        
        this.fruitThread.start();
        this.frogThread.start();
        this.snakeThread.start();
    }
}
