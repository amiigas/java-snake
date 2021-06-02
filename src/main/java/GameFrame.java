package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    final int GAME_SPEED = 150; //ms
    private Game game;
    JPanel buttonsPanel;
    ScreenPanel screenPanel;
    LeaderboardFrame leaderboardFrame;

    public GameFrame() {
        this.setTitle("Snake - Play");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setFocusTraversalKeysEnabled(false);

        Container pane = this.getContentPane();
        this.buttonsPanel = this.layoutButtons();
        pane.add(buttonsPanel, BorderLayout.PAGE_START);

        while(true) {
            this.screenPanel = new ScreenPanel();
            this.screenPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            pane.add(screenPanel, BorderLayout.CENTER);

            this.pack();
            this.setVisible(true);

            this.game = new Game();
            this.game.initialize();
            this.screenPanel.updateBoard(game.board);
            SwingUtilities.updateComponentTreeUI(this.screenPanel);
            startRenderLoop();
            this.gameOver();
        }
    }

    private void startRenderLoop() {
        while(!this.game.isOver) {
            try {
                Thread.sleep(GAME_SPEED);
                synchronized (this.game.processed) {
                    // if no more permits == all threads finished
                    if (this.game.processed.availablePermits() == 0) {
                        // update and render
                        this.screenPanel.updateBoard(game.board);
                        SwingUtilities.updateComponentTreeUI(this.screenPanel);
                        // release permits for semaphore
                        this.game.processed.release(4);
                        // signal all threads that rendering finished
                        this.game.renderLock.lock();
                        this.game.rendered.signalAll();
                        this.game.renderLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel layoutButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        JButton newGameButton = new JButton();
        newGameButton.setText("START");
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(e -> startGame());
        
        JButton leaderboardButton = new JButton();
        leaderboardButton.setText("LEADERBOARD");
        leaderboardButton.setFocusable(false);
        leaderboardButton.addActionListener(e -> showLeaderboard());

        buttonsPanel.add(newGameButton);
        buttonsPanel.add(leaderboardButton);

        return buttonsPanel;
    }

    private void showLeaderboard() {
        if (this.leaderboardFrame != null) {
            this.leaderboardFrame.dispose();
        }
        this.leaderboardFrame = new LeaderboardFrame();
    }

    private void startGame() {
        this.game.start();
    }

    private void gameOver() {
        this.createDeathFrame();
    }

    private void createDeathFrame() {
        new DeathFrame(this.game.score);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int kc = e.getKeyCode();
        if (kc >= 37 && kc <= 40) {
            synchronized (this.game) {
                this.game.snakeDirection = kc;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
