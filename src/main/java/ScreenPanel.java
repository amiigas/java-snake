package main.java;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.geom.*;
import main.java.Board;

public class ScreenPanel extends JPanel{
	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	private Image fruit;
	private Image wall;
	private Image frog;
	private boolean PanelActive = true;
	Board board = new Board(60, 60);
	
	
	public ScreenPanel() {
        
        initScreenPanel();
    }
     
    private void initScreenPanel() {
    	
        setBackground(Color.black);
        setFocusable(true);
 
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadIcons();
        board.generateObstacles();
        board.generateObstacles();
        board.generateObstacles();
        board.generateObstacles();
        board.generateObstacles();
        board.spawnFruit();
        board.spawnFrog();
        repaint();
//        timer.schedule(myTask, 2000, 2000);
        repaint();
        
    }
    
//    Timer timer = new Timer();
//    TimerTask myTask = new TimerTask() {
//        @Override
//        public void run() {
//        	board.moveFrog("South");
//        }
//    };
    
    private void loadIcons() {
 
        ImageIcon ifruit = new ImageIcon("java-snake-main/src/main/fruit.png");
        fruit = ifruit.getImage();
 
        ImageIcon iwall = new ImageIcon("java-snake-main/src/main/wall.png");
        wall = iwall.getImage();
        
        ImageIcon ifrog = new ImageIcon("java-snake-main/src/main/frog.png");
        frog = ifrog.getImage();
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		printDrawings(g);
    }
    
    public void printDrawings(Graphics g) {
    	Field[][] fields = board.getFields();
    	for (int i=0; i<board.ROWS-1;i++) {
    		for (int j=0; j<board.COLS-1; j++) {
    			if (fields[i][j].getFieldStatus() == FieldStatus.WALL) {
    				g.drawImage(wall, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getFieldStatus() == FieldStatus.FRUIT) {
    				g.drawImage(fruit, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			if (fields[i][j].getFieldStatus() == FieldStatus.FROG) {
    				g.drawImage(frog, fields[i][j].GetX(), fields[i][j].GetY(), this);
    			}
    			
    		}
    	}
    	
    }
	

}
