package main.java;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class DeathFrame extends JFrame {

    public static final int FRAME_WIDTH = 300;
    public static final int FRAME_HEIGTH = 300;
    public static final int BUTTON_WIDTH = 250;
    public static final int BUTTON_HEIGTH = 50;
    public static final int TEXTFIELD_WIDTH = 250;
    public static final int TEXTFIELD_HEIGTH = 50;
    public static final String leaderboardFilename = "leaderboard.txt";
    private int score;

    JLabel scoreLabel;
    JButton saveScoreButton;
    JTextField nameTextField;

    DeathFrame(int score) {
        this.score = score;

        this.setTitle("Game Over");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.layoutComponents();
        this.setVisible(true);
    }

    private void layoutComponents() {
        this.scoreLabel = new JLabel("Your score: " + Integer.toString(this.score));
        this.scoreLabel.setBounds(FRAME_WIDTH/2-TEXTFIELD_WIDTH/2, 20, TEXTFIELD_WIDTH, TEXTFIELD_HEIGTH);

        this.add(scoreLabel);

        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(FRAME_WIDTH/2-TEXTFIELD_WIDTH/2, 120, TEXTFIELD_WIDTH, TEXTFIELD_HEIGTH);

        this.add(nameTextField);

        this.saveScoreButton = new JButton();
        this.saveScoreButton.setBounds(FRAME_WIDTH/2-BUTTON_WIDTH/2, 120+50+20, BUTTON_WIDTH, BUTTON_HEIGTH);
        this.saveScoreButton.setText("SAVE SCORE");
        this.saveScoreButton.addActionListener(e -> submitScore());

        this.add(saveScoreButton);
    }

    private void submitScore() {
        this.saveScore(nameTextField.getText(), this.score);
        this.dispose();
    }

    private void saveScore(String name, int points) {
        try {
            FileWriter myWriter = new FileWriter(leaderboardFilename, true);
            myWriter.write(name + "," + points + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
