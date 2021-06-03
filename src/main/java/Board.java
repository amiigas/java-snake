package main.java;

/** 
 * Holds information about current state of the game in 2-dimensional Field array.
 * @see Field
 */
public class Board {
    int rows, cols;
    private Field[][] fields;

 	/**
	 * Constructs the board with empty fields
	 * @param rows Number of rows on the board.
	 * @param cols Number of columns on the board.s
	 */
    public Board(int rows, int cols) {
    	this.rows = rows;
    	this.cols = cols;
    	
    	fields = new Field[this.rows][this.cols];
    	for (int i=0; i<this.rows;i++) {
    		for (int j=0; j<this.cols; j++) {
    			fields[i][j] = new Field(i,j);
    		}
    	}
    }
    
	/** 
	 * Returns board's fields.
	 * @return Field[][]
	 */
	public Field[][] getFields() {
    	return this.fields;
    }

	/** 
	 * Sets board's fields.
	 * @param fields The fields to assign.
	 */
	public void setFields(Field[][] fields) {
    	this.fields = fields;
    }
	
	/** 
	 * Returns a random row index.
	 * @return int Row index
	 */
	public int getRandomRow() {
		return (int)(Math.random() * (this.rows-1));
	}

	/** 
	 * Returns a random column index.
	 * @return int Column index
	 */
	public int getRandomCol() {
		return (int)(Math.random() * (this.cols-1));
	}
    
	/** 
	 * Creates amount of obstacles on board.
	 * Obstacles are both vertical and horizontal and can intersect.
	 * @param amount Number of obstacles.
	 */
	public void spawnObstacles(int amount) {
		for (int a = 0; a < amount; a++) {
			double direction = Math.random();
			int randomrow = (int)(Math.random() * (44));
			int randomcol = (int)(Math.random() * (44));
			if (direction < 0.5) {
				for (int i=0;i<15;i++) {
					if (fields[randomrow+i][randomcol] != null)
						fields[randomrow+i][randomcol].setType(FieldType.WALL);
				}
			}
			else {
				for (int i=0;i<15;i++) {
					if (fields[randomrow][randomcol+i] != null)
						fields[randomrow][randomcol+i].setType(FieldType.WALL);
				}
			}
		}
    }
    
	/** 
	 * Returns first occurence of field of a given field type on board.
	 * @param fieldType The field type to search for.
	 * @return Coordinate 
	 * @see FieldType
	 * @see Coordinate
	 */
	public Coordinate findType(FieldType fieldType) {
    	Coordinate c = new Coordinate(-1,-1);
    	for (int i=0; i<this.rows;i++) {
    		for (int j=0; j<this.cols; j++) {
    			if(fields[i][j].getType() == fieldType) {
    				Coordinate d = new Coordinate(i,j);
    				return d;
    			}
    		}
    	}
    	return c;
    }
}
