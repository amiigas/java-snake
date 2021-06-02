package main.java;
import java.util.ArrayList;

public class Fruit extends BoardComponent implements Runnable {
    private Game game;
    public boolean isWorking = true;

    public Fruit(Game game) {
        this.type = ComponentType.FRUIT;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
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
            fields[row][col].setType(FieldType.FRUIT);
            this.setPosition(row, col);
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
            this.position.get(0).i = row;
            this.position.get(0).j = col;
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
