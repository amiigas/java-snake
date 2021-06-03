package main.java;
import java.util.ArrayList;

/**
 * Main board component controlled by the player.
 * Capable of collecting fruits and frogs. Grows on collect.
 * @see BoardComponent
 * @see Fruit
 * @see Frog
 */
public class Snake extends BoardComponent implements Runnable {
    private Game game;
    private int makeBigger;

    /**
     * Creates snake instance.
     * @param game Shared game that the snake lives in.
     * @see Game
     */
    public Snake(Game game) {
        this.type = ComponentType.SNAKE;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
        this.makeBigger = 0;
    }

    /**
     * Spawns snake of length 3 randomly on board in vertical position.
     * @see Board
     */
    public void spawn() {
        synchronized (this.game.board) {
            int row = this.game.board.getRandomRow();
            int col = this.game.board.getRandomCol() % 58;
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

    /**
     * Updates the snake position by performing appropriate actions in single frame.
     */
    private void move() {
        if (this.makeBigger > 0) {
            this.makeBigger -= 1;
        } else {
            this.deleteTail();
        }
        this.moveHead();
    }

    /** 
     * Checks if fruit was collected.
     * Updates the game's score and increments growth counter by 1.
     * @param x Field's row index
     * @param y Field's column index
     * @see Field
     */
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

    /** 
     * Checks if frog was collected.
     * Updates the game's score and increments growth counter by 3.
     * @param x Field's row index
     * @param y Field's column index
     * @see Field
     */
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

    /** 
     * Checks if snake is out of board.
     * @param x Field's row index
     * @param y Field's column index
     * @return boolean Returns true if snake is out of board, false otherwise.
     * @see Field
     * @see Board
     */
    private boolean checkOutOfFrame(int x, int y) {
    	if (x > 59 || x < 0 || y > 59 || y < 0) {
            this.game.isOver = true;
            return true;
        }
        return false;
    }

    /** 
     * Checks if snake collided with another board component.
     * Flags game as over if supplied field is of type WALL, SNAKE or PYTHON.
     * @param x Field's row index
     * @param y Field's column index
     * @see FieldType
     */
    private void checkCollision(int x, int y) {
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.WALL ||
                fields[x][y].getType() == FieldType.PYTHON ||
                fields[x][y].getType() == FieldType.SNAKE){
	    		this.game.isOver = true;
	    	}
    	}
    }

    /**
     * Sets last field occupied by snake as empty.
     */
    private void deleteTail() {
        synchronized (this.game.board) {
            Coordinate c = this.position.get(this.position.size()-1);
            Field[][] fields = this.game.board.getFields();
            fields[c.i][c.j].setType(FieldType.EMPTY);
            this.position.remove(this.position.size()-1);
        }
    }

    /**
     * Moves the sanke by one field.
     * Validates the direction, sets the field type, checks terminating conditions and collectibles.
     */
    private void moveHead() {
        synchronized (this.game.board) {
            Coordinate c = this.position.get(0);
            Field[][] fields = this.game.board.getFields();
            int dx = 0;
            int dy = 0;

            if (isDirectionValid(this.game.snakeDirection, this.game.prevSnakeDirection)) {
                this.game.prevSnakeDirection = this.game.snakeDirection;
            } else {
                this.game.snakeDirection = this.game.prevSnakeDirection;
            }
            
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
            if (!this.checkOutOfFrame(c.i+dx, c.j+dy)) {
                this.checkCollision(c.i+dx, c.j+dy);
                this.checkApple(c.i+dx, c.j+dy);
                this.checkFrog(c.i+dx, c.j+dy);
                fields[c.i+dx][c.j+dy].setType(FieldType.SNAKE);
                this.position.add(0, newHead);
            }
        }
    }

    /** 
     * Calculates if the player's input direction is valid.
     * Only opposite direction is considered invalid.
     * @param d1 Proposed direction.
     * @param d2 Current direction.
     * @return boolean Returns true if direction is valid, false otherwise.
     */
    private boolean isDirectionValid(int d1, int d2) {
        return !(Math.abs(d1 - d2) == 2);
    }
    
    /** 
     * Adds new element to array of coordinates occupied by snake.
     * @param row Field's row index.
     * @param col Field's column index.
     * @see Coordinate
     */
    private void addPosition(int row, int col) {
        this.position.add(this.position.size(), new Coordinate(row, col));
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
        System.out.printf("Snake started running with game at %s\n", this.game);
        while (!this.game.isOver) {
            this.move();
            this.finished();
        }
        System.out.println("Snake stopped running.");
    }
}
