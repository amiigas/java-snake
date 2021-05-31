package main.java;
import java.util.ArrayList;

public class Frog extends BoardComponent implements Runnable {
    private Game game;

    public Frog(Game game) {
        type = ComponentType.FROG;
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
            // 1. setting field type for render
            fields[row][col].setType(FieldType.FROG);
            // 2. setting object's position (currently unused)
            this.setPosition(row, col);
        }
    }

    private void hop() {
        // TODO
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
        System.out.printf("Frog started running with game at %s\n", this.game);
        while (true) {
            synchronized (this.game.board) {
                // hop();
            }
            this.finished();
        }
    }
}
