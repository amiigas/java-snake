package main.java;
import javax.swing.*;
import java.awt.*;

public class ScreenPanel extends JPanel {
	private Image snake;
	private Image fruit;
	private Image wall;
	private Image frog;
	private Image python;
    private Board board = new Board(60, 60);
	
	public ScreenPanel() {
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.loadIcons();
        repaint();
    }

    public void updateBoard(Board board) {
        this.board = board;
    }
    
    private void loadIcons() {

        ImageIcon isnake = new ImageIcon("src/main/snake.png");
        snake = isnake.getImage();
        
        ImageIcon ipython = new ImageIcon("src/main/python.png");
        python = ipython.getImage();
 
        ImageIcon ifruit = new ImageIcon("src/main/fruit.png");
        fruit = ifruit.getImage();
 
        ImageIcon iwall = new ImageIcon("src/main/wall.png");
        wall = iwall.getImage();
        
        ImageIcon ifrog = new ImageIcon("src/main/frog.png");
        frog = ifrog.getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		printDrawings(g);
    }
    
    public void printDrawings(Graphics g) {
    	Field[][] fields = board.getFields();
        g.setColor(this.getBackground());
    	for (int i=0; i<board.rows-1; i++) {
    		for (int j=0; j<board.cols-1; j++) {
                if (fields[i][j].getType() == FieldType.EMPTY) {
                    g.fillRect(fields[i][j].GetX(), fields[i][j].GetY(), 10, 10);
    			}
    			if (fields[i][j].getType() == FieldType.WALL) {
    				g.drawImage(wall, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
                if (fields[i][j].getType() == FieldType.SNAKE) {
    				g.drawImage(snake, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getType() == FieldType.FRUIT) {
    				g.drawImage(fruit, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getType() == FieldType.FROG) {
    				g.drawImage(frog, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getType() == FieldType.PYTHON) {
    				g.drawImage(python, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    		}
    	}
    }
}
