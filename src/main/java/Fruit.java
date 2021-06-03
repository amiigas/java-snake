package main.java;
import java.util.ArrayList;

/**
 * Static board component that can be collected by both snake and python.
 * Worth 1 point.
 * @see Snake
 * @see Python
 */
public class Fruit extends BoardComponent implements Runnable {
    private Game game;
    public boolean isWorking = true;

    /**
     * Creates the Friut.
     * @param game Shared Game object that the fruit lives in.
     * @see Game
     */
    public Fruit(Game game) {
        this.type = ComponentType.FRUIT;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
    }

    /**
     * Spawns the fruit randomly on the board and sets its position.
     */
    public void spawn() {
        synchronized (this.game.board) {
            int row = this.game.board.getRandomRow();
            int col = this.game.board.getRandomCol();
            Field[][] fields = this.game.board.getFields();
            while (fields[row][col].getType() != FieldType.EMPTY) {
                row = this.game.board.getRandomRow();
                col = this.game.board.getRandomCol();
            }
            fields[row][col].setType(FieldType.FRUIT);
            this.setPosition(row, col);
        }
    }

    /**
     * Checks if fruit was eaten.
     * @return boolean
     */
    private boolean isEaten() {
        Coordinate c = this.position.get(0);
        synchronized (this.game.board) {
            Field[][] fields = this.game.board.getFields();
            FieldType fType = fields[c.i][c.j].getType();
            if (fType == FieldType.SNAKE ||
                fType == FieldType.PYTHON) {
                return true;
            } else {
                return false; 
            }
        }
    }

    /**
     * Sets the fruit position.
     * @param row 
     * @param col
     */
    private void setPosition(int row, int col) {
        if (this.position.isEmpty()) {
            this.position.add(0, new Coordinate(row, col));
        } else {
            this.position.get(0).i = row;
            this.position.get(0).j = col;
        }
    }

    /**
     * Signals that the thread is ready with processing.
     */
    private void finished() {
        this.aquireProcessed();
        this.awaitRendered();
    }

    /**
     * Aquires permit from the semaphore that counts how many threads are done processing.
     */
    private void aquireProcessed() {
        synchronized (this.game) {
            try {
                this.game.processed.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Makes thread wait for a signal from the main rendering thread.
     */
    private void awaitRendered() {
        this.game.renderLock.lock();
        try {
            this.game.rendered.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.game.renderLock.unlock();
        }
    }

    /**
     * Starts the thread.
     */
    @Override
    public void run() {
        System.out.printf("Fruit started running with game at %s\n", this.game);
        while(!this.game.isOver) {
            if (this.isEaten()) {
                this.spawn();
            }
        	this.finished();
        }
        System.out.println("Fruit stopped running.");
    }
}
