package main.java;
import java.util.ArrayList;

public class Snake extends BoardComponent implements Runnable {
    private Game game;
    private int makeBigger;

    public Snake(Game game) {
        this.type = ComponentType.SNAKE;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
        this.makeBigger = 0;
    }

    public void spawn() {
        synchronized (this.game.board) {
            int row = this.game.board.getRandomRow();
            int col = this.game.board.getRandomCol();
            Field[][] fields = this.game.board.getFields();
            // TODO: can throw null
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
        if (this.makeBigger > 0) {
            this.makeBigger -= 1;
        } else {
            this.deleteTail();
        }
        this.moveHead();
    }

    private void checkApple(int x, int y) {
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.FRUIT){
	    		fields[x][y].setType(FieldType.EMPTY);
	    		this.makeBigger += 1;
                this.game.score += 1;
	    	}
    	}
    }

    private void checkFrog(int x, int y) {
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.FROG){
	    		fields[x][y].setType(FieldType.EMPTY);
	    		this.makeBigger += 3;
                this.game.score += 3;
	    	}
    	}
    }
    
    private void checkCollision(int x, int y) {
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.WALL){
	    		this.game.isOver = true;
	    	}
    	}
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
            int dx = 0;
            int dy = 0;
            if (this.game.snakeDirection == 37) {
                // left
                dx = -1;
            } else if (this.game.snakeDirection == 38) {
                // up
                dy = -1;
            } else if (this.game.snakeDirection == 39) {
                // right
                dx = 1;
            } else if (this.game.snakeDirection == 40) {
                // down
                dy = 1;
            }
            Coordinate newHead = new Coordinate(c.i+dx, c.j+dy);
            this.checkCollision(c.i+dx, c.j+dy);
            this.checkApple(c.i+dx, c.j+dy);
            this.checkFrog(c.i+dx, c.j+dy);
            fields[c.i+dx][c.j+dy].setType(FieldType.SNAKE);
            this.position.add(0, newHead);
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
        while (!this.game.isOver) {
            this.move();
            this.finished();
        }
        System.out.println("Snake stopped running.");
    }
}
