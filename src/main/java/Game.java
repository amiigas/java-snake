package main.java;

public class Game {
    public Board board;
    public boolean isOver;
    public int score;

    public Game() {
        this.board = new Board(60, 60);
        this.isOver = false;
        this.score = 0;
    }
    
    public void initialize() {
        board.spawnObstacles(5);
        // board.spawnFruit();
        // board.spawnFrog();
    }

    public void start() {
        Runnable fruit = new Fruit(this);
        Runnable frog = new Frog(this);

        Thread t1 = new Thread(fruit, "fruit");
        Thread t2 = new Thread(frog, "frog");

        t1.start();
        t2.start();
    }
}
