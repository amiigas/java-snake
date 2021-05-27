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
            fields[row][col].setType(FieldType.FROG);
            this.setPosition(row, col);
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

    @Override
    public void run() {
        System.out.printf("Frog started running with game at %s\n", this.game);
        this.spawn();
    }
}
