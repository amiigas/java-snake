package main.java;
import java.util.ArrayList;

public class Snake extends BoardComponent implements Runnable {
    private Game game;

    public Snake(Game game) {
        this.type = ComponentType.SNAKE;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
    }

    public void spawn() {
        synchronized (this.game.board) {
            int row = this.game.board.getRandomRow();
            int col = this.game.board.getRandomCol();
            Field[][] fields = this.game.board.getFields();
            while (fields[row][col].getType() != FieldType.EMPTY ||
                   fields[row][col+1].getType() != FieldType.EMPTY ||
                   fields[row][col+2].getType() != FieldType.EMPTY) {
                row = this.game.board.getRandomRow();
                col = this.game.board.getRandomCol();
            }
            for (int i = 0; i < 3; i++) {
                fields[row][col+i].setType(FieldType.SNAKE);
                this.addPosition(row, col+i);
            }
        }
    }

    private void move() {
        this.deleteTail();
        this.moveHead();
    }

    private void deleteTail() {
        synchronized (this.game.board) {
            Coordinate c = this.position.get(this.position.size()-1);
            Field[][] fields = this.game.board.getFields();
            fields[c.i][c.j].setType(FieldType.EMPTY);
            this.position.remove(this.position.size()-1);
        }
    }

    private void moveHead() {
        synchronized (this.game.board) {
            Coordinate c = this.position.get(0);
            Field[][] fields = this.game.board.getFields();
            if (this.game.snakeDirection == 38) {
                Coordinate newHead = new Coordinate(c.i, c.j-1);
                fields[c.i][c.j-1].setType(FieldType.SNAKE);
                this.position.add(0, newHead);
            }
        }
    }

    private void addPosition(int row, int col) {
            this.position.add(this.position.size(), new Coordinate(row, col));
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
        System.out.printf("Snake started running with game at %s\n", this.game);
        while (true) {
            this.move();
            this.finished();
        }
    }
}
