package main.java;
import java.util.ArrayList;
import java.util.Random;

/**
 * Moving board component that can be collected by both snake and python.
 * Moves randomly by one field at every other frame. Worth 3 points.
 * @see Snake
 * @see Python
 */
public class Frog extends BoardComponent implements Runnable {
    private Game game;
    private boolean hopTurn;

    /**
     * Creates the Frog.
     * @param game Shared Game object that the frog lives in.
     * @see Game
     */
    public Frog(Game game) {
        this.type = ComponentType.FROG;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
        this.hopTurn = true;
    }

    /**
     * Spawns the frog randomly on the board and sets its position.
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
            // 1. setting field type for render
            fields[row][col].setType(FieldType.FROG);
            // 2. setting object's position (currently unused)
            this.setPosition(row, col);
        }
    }

    /**
     * Moves the frog randomly by one field.
     */
    private void hop() {
        synchronized (this.game.board) {
            Field[][] fields = this.game.board.getFields();
            Coordinate c = this.position.get(0);
            fields[c.i][c.j].setType(FieldType.EMPTY);

            ArrayList<Coordinate> options = new ArrayList<Coordinate>();
            if (c.i-1 >= 0) { 
                if (fields[c.i-1][c.j].getType() == FieldType.EMPTY) {
                    options.add(new Coordinate(c.i-1, c.j));
                }
            }
            if (c.i+1 <= 59) {
                if (fields[c.i+1][c.j].getType() == FieldType.EMPTY) {
                    options.add(new Coordinate(c.i+1, c.j));
                }
            }
            if (c.j-1 >= 0) {
                if (fields[c.i][c.j-1].getType() == FieldType.EMPTY) {
                    options.add(new Coordinate(c.i, c.j-1));
                }
            }
            if (c.j+1 <= 59) {
                if (fields[c.i][c.j+1].getType() == FieldType.EMPTY) {
                    options.add(new Coordinate(c.i, c.j+1));
                }
            }

            if (!options.isEmpty()) {
                int rnd = new Random().nextInt(options.size());
                Coordinate newPos = options.get(rnd);
                try {
                    fields[newPos.i][newPos.j].setType(FieldType.FROG);
                    this.setPosition(newPos);
                } catch (NullPointerException e) {}
            }
        }
    }

    /**
     * Checks if frog was eaten.
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
     * Sets the frog position.
     * @param row 
     * @param col
     */
    private void setPosition(int row, int col) {
        if (this.position.isEmpty()) {
            this.position.add(0, new Coordinate(row, col));
        } else {
            this.position.set(0, new Coordinate(row, col));
        }
    }

    /**
     * Sets the frog position.
     * @param c
     */
    private void setPosition(Coordinate c) {
        if (this.position.isEmpty()) {
            this.position.add(0, c);
        } else {
            this.position.set(0, c); 
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
        System.out.printf("Frog started running with game at %s\n", this.game);
        while (!this.game.isOver) {
            if (this.isEaten()) {
                this.spawn();
            }
            if (this.hopTurn) {
                this.hop();
            }
            this.hopTurn = !this.hopTurn;
            this.finished();
        }
        System.out.println("Frog stopped running.");
    }
}
