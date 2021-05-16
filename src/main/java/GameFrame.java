package main.java;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGTH = 600;

    // int score;
    
    GameFrame() {
        this.setTitle("Snake - Play");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        // temporary to force game over
        JButton dedButton = new JButton();
        dedButton.setBounds(40, 100, 100, 100);
        dedButton.setText("I DED");
        dedButton.addActionListener(e -> gameOver());

        this.add(dedButton);
    }

    private void gameOver() {
        this.createDeathFrame();
        this.dispose();
    }

    private void createDeathFrame() {
        DeathFrame deathFrame = new DeathFrame();
    }
}
