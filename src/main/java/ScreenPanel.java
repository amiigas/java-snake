package main.java;
import javax.swing.*;
import java.awt.*;

public class ScreenPanel extends JPanel {
	private Image fruit;
	private Image wall;
	private Image frog;
    private Board board;
	
	public ScreenPanel(int width, int height) {
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
        this.loadIcons();
        repaint();
//        timer.schedule(myTask, 2000, 2000);
        // repaint();
        
    }
    
//    Timer timer = new Timer();
//    TimerTask myTask = new TimerTask() {
//        @Override
//        public void run() {
//        	board.moveFrog("South");
//        }
//    };
    public void updateBoard(Board board) {
        this.board = board;
    }
    
    private void loadIcons() {
 
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
    	for (int i=0; i<board.rows-1; i++) {
    		for (int j=0; j<board.cols-1; j++) {
    			if (fields[i][j].getType() == FieldType.WALL) {
    				g.drawImage(wall, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getType() == FieldType.FRUIT) {
    				g.drawImage(fruit, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getType() == FieldType.FROG) {
    				g.drawImage(frog, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    		}
    	}
    }
}
