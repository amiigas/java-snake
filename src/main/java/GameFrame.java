package main.java;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    private Game game;
    JButton newGameButton;
    JButton leaderboardButton;
    JButton gameOverButton;
    JPanel buttonsPanel;
    ScreenPanel screenPanel;
    LeaderboardFrame leaderboardFrame;

    public GameFrame() {
        this.setTitle("Snake - Play");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Container pane = this.getContentPane();
        this.buttonsPanel = this.layoutButtons();
        pane.add(buttonsPanel, BorderLayout.PAGE_START);

        this.screenPanel = new ScreenPanel();
        this.screenPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        pane.add(screenPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);

        game = new Game();
        game.initialize();
        startRenderLoop();
    }

    private void startRenderLoop() {
        while(true) {
            try {
                Thread.sleep(100);
                this.screenPanel.updateBoard(game.board);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startGame() {
        game.start();
    }

    private JPanel layoutButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));

        this.newGameButton = new JButton();
        this.newGameButton.setText("START");
        this.newGameButton.addActionListener(e -> startGame());

        this.gameOverButton = new JButton();
        this.gameOverButton.setText("GAME OVER");
        this.gameOverButton.addActionListener(e -> gameOver());
        
        this.leaderboardButton = new JButton();
        this.leaderboardButton.setText("LEADERBOARD");
        this.leaderboardButton.addActionListener(e -> showLeaderboard());

        buttonsPanel.add(newGameButton);
        buttonsPanel.add(gameOverButton);
        buttonsPanel.add(leaderboardButton);

        return buttonsPanel;
    }

    private void showLeaderboard() {
        if (this.leaderboardFrame != null ) {
            this.leaderboardFrame.dispose();
        }
        this.leaderboardFrame = new LeaderboardFrame();
    }

    private void gameOver() {
        this.createDeathFrame();
        // game.stop();
    }

    private void createDeathFrame() {
        new DeathFrame();
    }
}
