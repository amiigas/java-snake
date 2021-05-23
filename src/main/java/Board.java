package main.java;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import main.java.Field;

public class Board {
	 
    int ROWS, COLS;
    private Field[][] fields;
    
 
    public Board(int given_rows, int given_cols)
    {
    	ROWS = given_rows;
    	COLS = given_cols;
    	
    	fields = new Field[ROWS][COLS];
    	for (int i=0; i<ROWS-1;i++) {
    		for (int j=0; j<COLS-1; j++) {
    			fields[i][j] = new Field(i,j);
    		}
    	}
    	

    	
    }
    public Field[][] getFields(){
    	return fields;
    }
    
    
    public void setFields(Field[][] fields) {
    	this.fields = fields;
    }
    
    
    public void generateObstacles() {
    	double direction = Math.random();
		int randomrow = (int)(Math.random() * (44));
		int randomcol = (int)(Math.random() * (44));
		if (direction < 0.5) {
			for (int i=0;i<15;i++) {
				if (fields[randomrow+i][randomcol] != null)
					fields[randomrow+i][randomcol].changeFieldStatus(FieldStatus.WALL);
			}
			
		}
		else {
			for (int i=0;i<15;i++) {
				if (fields[randomrow][randomcol+i] != null)
					fields[randomrow][randomcol+i].changeFieldStatus(FieldStatus.WALL);
			}
		}
		
    }
    
    public void spawnFruit() {
    	int row = (int)(Math.random() * (ROWS-1));
        int column = (int)(Math.random() * (COLS-1));
    	while(true){
            if(fields[row][column].getFieldStatus()==FieldStatus.EMPTY)
                 break;
            else
            {
            	row = (int)(Math.random() * (ROWS-1));
                column = (int)(Math.random() * (COLS-1));
            }
        }
    	fields[row][column].changeFieldStatus(FieldStatus.FRUIT);
    }
    
    public void spawnFrog() {
    	int row = (int)(Math.random() * (ROWS-1));
        int column = (int)(Math.random() * (COLS-1));
    	while(true){
            if(fields[row][column].getFieldStatus()==FieldStatus.EMPTY)
                 break;
            else
            {
            	row = (int)(Math.random() * (ROWS-1));
                column = (int)(Math.random() * (COLS-1));
            }
        }
    	fields[row][column].changeFieldStatus(FieldStatus.FROG);
    }
    
//   public void moveFrog(String Direction) {
//	   int FrogX = -1;
//	   int FrogY = -1;
//	   for (int i=0; i < this.ROWS-1; i++) {
//   			for (int j=0; j < this.COLS-1; j++) {
//   		
//   				if (fields[i][j].getFieldStatus() == FieldStatus.FROG) {
//   					FrogX = i;
//   					FrogY = j;
//   				}
//   			}
//	   }
//	   if (FrogX != -1){
//		   if (FrogX > 1 && fields[FrogX-1][FrogY].getFieldStatus() == FieldStatus.EMPTY) {
//			   if (Direction == "West")
//			   {
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.EMPTY);
//				   FrogX = FrogX-1;
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.FROG);
//				   
//			   }
//				   
//		   }
//		   if (FrogY > 1 && fields[FrogX][FrogY-1].getFieldStatus() == FieldStatus.EMPTY) {
//			   if (Direction == "North")
//			   {
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.EMPTY);
//				   FrogY = FrogY-1;
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.FROG);
//				   
//			   }
//		   }
//		   if (FrogX < this.COLS && fields[FrogX+1][FrogY].getFieldStatus() == FieldStatus.EMPTY) {
//			   if (Direction == "East")
//			   {
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.EMPTY);
//				   FrogX = FrogX+1;
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.FROG);
//				   
//			   }
//		   }
//		   if (FrogY < this.ROWS && fields[FrogX][FrogY+1].getFieldStatus() == FieldStatus.EMPTY) {
//			   if (Direction == "South")
//			   {
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.EMPTY);
//				   FrogY = FrogY+1;
//				   fields[FrogX][FrogY].changeFieldStatus(FieldStatus.FROG);
//				   
//			   }
//		   }
//		      
//	   }
//	   
//   }
}
