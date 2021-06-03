package main.java;
import java.util.ArrayList;

/**
 * Board component controlled by the AI.
 * Capable of collecting fruits and frogs. Grows on collect.
 * @see BoardComponent
 * @see Fruit
 * @see Frog
 */
public class Python extends BoardComponent implements Runnable {
    private Game game;
	private int makeBigger;
    private int GoUp = 0;
    private int GoDown = 0;
    private int GoLeft = 0;
    private int GoRight = 0;
//    private int PreviousX = 0;
//    private int PreviousY = 0;

	/**
     * Creates python instance.
     * @param game Shared game that the python lives in.
     * @see Game
     */
    public Python(Game game) {
        this.type = ComponentType.PYTHON;
        this.game = game;
        this.position = new ArrayList<Coordinate>();
		this.makeBigger = 0;
    }

	/**
     * Spawns python of length 3 randomly on board in vertical position.
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
                fields[row][col+i].setType(FieldType.PYTHON);
                this.addPosition(row, col+i);
            }
        }
    }

	/**
     * Updates the python position by performing appropriate actions in single frame.
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
	 * Chceck if python is on field of type WALL or SNAKE.
	 * @param x Field's row index
	 * @param y Field's column index
	 * @return Returns true if python is on a field of type WALL or SNAKE, false otherwise.
	 * @see FieldType
	 */
    private boolean checkWalls(int x, int y) {
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.WALL ||
				fields[x][y].getType() == FieldType.SNAKE){
	    		return true;
	    	}
    	}
    	return false;
    }
    
    private boolean checkSelf(int x, int y) {
    	boolean pyton = false;
    	synchronized (this.game.board) {
    		Field[][] fields = this.game.board.getFields();
	    	if (fields[x][y].getType() == FieldType.PYTHON){
	    		pyton = true;
	    	}
    	}
    	return pyton;
    }
    
    private Coordinate randomMove() {
    	double randomMove = Math.random();
    	int x;
    	int y;
    	if(randomMove < 0.25) {
    		x = 0;
    		y = -1;
    	}
    	else if(randomMove >=0.25 && randomMove <0.5) {
    		x = 0;
    		y = 1;
    	}
    	else if(randomMove >=0.5 && randomMove <0.75) {
    		x = -1;
    		y = 0;
    	}
    	else {
    		x = 1;
    		y = 0;
    	}
    	Coordinate c = new Coordinate(x, y);
    	return c;
    }
    	
	/**
	 * TODO
	 * @param i
	 * @param j
	 */
    private void decide_l_r(int i, int j) {
    	for(int k=1;k<9;k++) {
   		 if(this.checkWalls(i, j+k) == false) {
   			this.GoDown = k+1;
   			break;
   		 }
   		 if(this.checkWalls(i, j-k) == false) {
   			this.GoUp = k+1;
   			break;
   		 }
   	 }
    }
    
	/**
	 * TODO
	 * @param i
	 * @param j
	 */
    private void decide_u_d(int i, int j) {
    	for(int k=1;k<9;k++) {
   		 if(this.checkWalls(i+k, j) == false) {
   			 this.GoRight = k+1;
   			 break;
   		 }
   		 if(this.checkWalls(i-k, j) == false) {
   			 this.GoLeft = k+1;
   			 break;
   		 }
   	 }
    }

	/**
     * Moves the python by one field.
     * TODO: - jakie tutaj algo zostało zastosowane w jednym zdaniu
     */
    private void moveHead() {
        synchronized (this.game.board) {
        	int dx = 0;
            int dy = 0;
            int moves_x_fruit;
    		int moves_y_fruit;
    		int moves_x_frog;
    		int moves_y_frog;
            Coordinate c = this.position.get(0);
            Coordinate d = new Coordinate(-1,-1);
            Field[][] fields = this.game.board.getFields();
            Coordinate fruit = this.game.board.findType(FieldType.FRUIT);
            Coordinate frog = this.game.board.findType(FieldType.FROG);
            moves_x_fruit = fruit.i - c.i;
    		moves_y_fruit = fruit.j - c.j;
    		moves_x_frog = frog.i - c.i;
    		moves_y_frog = frog.j - c.j;
    		int moves_x;
        	int moves_y;
    		
            if(this.GoDown != 0 && c.j != 59 && this.checkWalls(c.i, c.j+1) == false ) {
            	this.GoDown = this.GoDown - 1;
            	dx = 0;
            	dy = 1;
            }
            else if(this.GoLeft != 0 && c.i !=0 && this.checkWalls(c.i-1, c.j) == false ) {
            	this.GoLeft = this.GoLeft - 1;
            	dx = -1;
            	dy = 0;
            }
            else if(this.GoUp != 0 && c.j != 0 && this.checkWalls(c.i, c.j-1) == false ) {
            	this.GoUp = this.GoUp - 1;
            	dx = 0;
            	dy = -1;
            }
            else if(this.GoRight != 0 && c.i != 59 && this.checkWalls(c.i+1, c.j) == false ) {
            	this.GoRight = this.GoRight - 1;
            	dx = 1;
            	dy = 0;
            }
            else {
	            if(Math.abs(moves_x_fruit)+Math.abs(moves_y_fruit) < Math.abs(moves_x_frog)+Math.abs(moves_y_frog)) {
	            	moves_x = moves_x_fruit;
	            	moves_y = moves_y_fruit;
	            }
	            else {
	            	moves_x = moves_x_frog;
	            	moves_y = moves_y_frog;
	            }
	            if((Math.random() > 0.5 || moves_x == 0) && moves_y !=0) {
	            	if (moves_y < 0) {
	                     // up
	                     dy = -1;
	                     if(this.checkWalls(c.i, c.j-1) == true ) {
	                    	 this.decide_u_d(c.i, c.j-1);
	                    	 dy=0;
	                    	 dx = 1;
	                    	 if(this.checkWalls(c.i+1, c.j) == true ) {
	                    		 dx = -1;
	                    		 dy=0;
	                    	 }
	                     }
                   
	            	}
	            	else if (moves_y > 0) {
	                     // down
	                     dy = 1;
	                     if(this.checkWalls(c.i, c.j+1) == true ) {
	                    	 this.decide_u_d(c.i, c.j+1);
	                    	 dy=0;
	                    	 dx = -1;
	                     }
	                     if(this.checkWalls(c.i-1, c.j) == true ) {
                    		 dx = 1;
                    		 dy=0;
                    	 }
	            	}
	            }
	            else {
	            	if (moves_x < 0) {
	                     // left
	                     dx = -1;
	                     if(this.checkWalls(c.i-1, c.j) == true ) {
	                    	 this.decide_l_r(c.i-1, c.j);
	                    	 dx = 0;
	                    	 dy = 1;
	                    	 if(this.checkWalls(c.i, c.j+1) == true ) {
	                    		 dx = 0;
	                    		 dy=-1;
	                    	 }
	                     }             
	                 } 
	            	else if (moves_x > 0) {
	                     // right
	                     dx = 1;
	                     if(this.checkWalls(c.i+1, c.j) == true ) {
	                    	 this.decide_l_r(c.i+1, c.j);
	                    	 dx = 0;
	                    	 dy = -1;
	                    	 if(this.checkWalls(c.i, c.j-1) == true ) {
	                    		 dx = 0;
	                    		 dy=1;
	                    	 }
	                     }       
	                 } 
	            }
            }
           
            Coordinate newHead = new Coordinate(c.i+dx, c.j+dy);
//            PreviousX = dx;
//            PreviousY = dy;
            this.checkApple(c.i+dx, c.j+dy);
            this.checkFrog(c.i+dx, c.j+dy);
            fields[c.i+dx][c.j+dy].setType(FieldType.PYTHON);
            this.position.add(0, newHead);
        }
    }

	/** 
     * Adds new element to array of coordinates occupied by python.
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
        System.out.printf("Python started running with game at %s\n", this.game);
        while (!this.game.isOver) {
            this.move();
            this.finished();
        }
        System.out.println("Python stopped running.");
    }
}