package main.java;

public class Game {
    public Board board;
    public boolean isOver;
    public int score;
    Thread frogThread;
    Thread fruitThread;

    public Game() {
        this.board = new Board(60, 60);
        this.isOver = false;
        this.score = 0;
    }
    
    public void initialize() {
        Fruit fruit = new Fruit(this);
        Frog frog = new Frog(this);

        this.fruitThread = new Thread(fruit, "fruit");
        this.frogThread = new Thread(frog, "frog");

        board.spawnObstacles(5);
        fruit.spawn();
        frog.spawn();
    }

    public void start() {        
        this.fruitThread.start();
        this.frogThread.start();
    }
}
