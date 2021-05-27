package main.java;
import javax.swing.*;


public class GameFrame extends JFrame {
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 600;
    private Game game;

    public GameFrame() {
        this.setTitle("Snake - Play");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        ScreenPanel screenPanel = new ScreenPanel(FRAME_WIDTH, FRAME_HEIGHT);
        this.add(screenPanel);
        this.pack();
      
        // temporary to force game over
        JButton dedButton = new JButton();
        dedButton.setBounds(40, 100, 100, 100);
        dedButton.setText("I DED");
        dedButton.addActionListener(e -> gameOver());
        this.add(dedButton);
        
        game = new Game();
        game.initialize();
        game.start();
        
        screenPanel.updateBoard(game.board);
    }

    private void gameOver() {
        this.createDeathFrame();
        this.dispose();
    }

    private void createDeathFrame() {
        new DeathFrame();
    }
}
