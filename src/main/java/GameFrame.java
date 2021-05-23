package main.java;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import java.awt.EventQueue;
import javax.swing.*;

public class GameFrame extends JFrame {

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGTH = 600;

    // int score;
    
    public GameFrame() {
    	
    	
        this.setTitle("Snake - Play");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        
     
        JPanel panel = new ScreenPanel();
        add(panel);
        
        pack();
        this.setVisible(true);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
      
        
        // temporary to force game over
        JButton dedButton = new JButton();
        dedButton.setBounds(40, 100, 100, 100);
        dedButton.setText("I DED");
        dedButton.addActionListener(e -> gameOver());
        this.setLayout(null);
        this.add(dedButton);
    }

    private void gameOver() {
        this.createDeathFrame();
        this.dispose();
    }

    private void createDeathFrame() {
        new DeathFrame();
    }
}
