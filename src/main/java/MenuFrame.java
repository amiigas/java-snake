package main.java;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuFrame extends JFrame {

    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGTH = 400;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGTH = 50;

    JButton newGameButton;
    JButton leaderboardButton;
    JFrame gameFrame;
    JFrame leaderboardFrame;

    MenuFrame() {
        this.setTitle("Snake - Main Menu");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.layoutButtons();
        this.setVisible(true);
    }

    private void layoutButtons() {
        this.newGameButton = new JButton();
        this.newGameButton.setBounds(FRAME_WIDTH/2-BUTTON_WIDTH/2, 100, BUTTON_WIDTH, BUTTON_HEIGTH);
        this.newGameButton.setText("NEW GAME");
        this.newGameButton.addActionListener(e -> newGame());

        this.add(newGameButton);

        this.leaderboardButton = new JButton();
        this.leaderboardButton.setBounds(FRAME_WIDTH/2-BUTTON_WIDTH/2, 100+50+20, BUTTON_WIDTH, BUTTON_HEIGTH);
        this.leaderboardButton.setText("LEADERBOARD");
        this.leaderboardButton.addActionListener(e -> showLeaderboard());

        this.add(leaderboardButton);
    }

    private void newGame() {
        this.createGameFrame();
    }

    private void createGameFrame() {
        if (this.gameFrame != null ) {
            this.gameFrame.dispose();
        }
        this.gameFrame = new GameFrame();
    }

    private void showLeaderboard() {
        if (this.leaderboardFrame != null ) {
            this.leaderboardFrame.dispose();
        }
        this.leaderboardFrame = new LeaderboardFrame();
    }
}
