package main.java;

public class Board {
    int rows, cols;
    private Field[][] fields;

 
    public Board(int given_rows, int given_cols) {
    	rows = given_rows;
    	cols = given_cols;
    	
    	fields = new Field[rows][cols];
    	for (int i=0; i<rows;i++) {
    		for (int j=0; j<cols; j++) {
    			fields[i][j] = new Field(i,j);
    		}
    	}
    }

    public Field[][] getFields() {
    	return fields;
    }

    public void setFields(Field[][] fields) {
    	this.fields = fields;
    }

	public int getRandomRow() {
		return (int)(Math.random() * (rows-1));
	}

	public int getRandomCol() {
		return (int)(Math.random() * (cols-1));
	}
    
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
    
    public Coordinate findType(FieldType fieldType) {
    	Coordinate c = new Coordinate(-1,-1);
    	for (int i=0; i<rows-1;i++) {
    		for (int j=0; j<cols-1; j++) {
    			if(fields[i][j].getType() == fieldType) {
    				Coordinate d = new Coordinate(i,j);
    				return d;
    			}
    		}
    	}
    	return c;
    }
}
