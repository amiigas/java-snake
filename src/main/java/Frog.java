package main.java;
import java.util.ArrayList;
import java.util.Random;

public class Frog extends BoardComponent implements Runnable {
    private Game game;
    private boolean hopTurn;

    public Frog(Game game) {
        this.type = ComponentType.FROG;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
        this.hopTurn = true;
    }

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

    private void hop() {
        synchronized (this.game.board) {
            Field[][] fields = this.game.board.getFields();
            Coordinate c = this.position.get(0);
            fields[c.i][c.j].setType(FieldType.EMPTY);

            ArrayList<Coordinate> options = new ArrayList<Coordinate>();
            if (c.i-1 >= 0) {
                options.add(new Coordinate(c.i-1, c.j));
            }
            if (c.i+1 <= 59) {
                options.add(new Coordinate(c.i+1, c.j));
            }
            if (c.j-1 >= 0) {
                options.add(new Coordinate(c.i, c.j-1));
            }
            if (c.j+1 <= 59) {
                options.add(new Coordinate(c.i, c.j+1));
            }

            int rnd = new Random().nextInt(options.size());
            Coordinate newPos = options.get(rnd);
            fields[newPos.i][newPos.j].setType(FieldType.FROG);
            this.setPosition(newPos);
        }
    }

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

    private void setPosition(int row, int col) {
        if (this.position.isEmpty()) {
            this.position.add(0, new Coordinate(row, col));
        } else {
            this.position.set(0, new Coordinate(row, col));
        }
    }

    private void setPosition(Coordinate c) {
        if (this.position.isEmpty()) {
            this.position.add(0, c);
        } else {
            this.position.set(0, c); 
        }
    }

    private void finished() {
        this.aquireProcessed();
        this.awaitRendered();
    }

    private void aquireProcessed() {
        synchronized (this.game) {
            try {
                this.game.processed.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
