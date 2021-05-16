package main.java;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class DeathFrame extends JFrame {

    public static final int FRAME_WIDTH = 300;
    public static final int FRAME_HEIGTH = 180;
    public static final int BUTTON_WIDTH = 250;
    public static final int BUTTON_HEIGTH = 50;
    public static final int TEXTFIELD_WIDTH = 250;
    public static final int TEXTFIELD_HEIGTH = 50;
    public static final String leaderboardFilename = "leaderboard.txt";

    JButton saveScoreButton;
    JTextField nameTextField;

    DeathFrame() {
        this.setTitle("Game Over");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.layoutComponents();
        this.setVisible(true);
    }

    private void layoutComponents() {
        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(FRAME_WIDTH/2-TEXTFIELD_WIDTH/2, 20, TEXTFIELD_WIDTH, TEXTFIELD_HEIGTH);

        this.add(nameTextField);

        this.saveScoreButton = new JButton();
        this.saveScoreButton.setBounds(FRAME_WIDTH/2-BUTTON_WIDTH/2, 20+50+20, BUTTON_WIDTH, BUTTON_HEIGTH);
        this.saveScoreButton.setText("SAVE SCORE");
        this.saveScoreButton.addActionListener(e -> submitScore());

        this.add(saveScoreButton);
    }

    private void submitScore() {
        System.out.println("Submiting " + nameTextField.getText());
        this.saveScore(nameTextField.getText());
        this.dispose();
    }

    private void saveScore(String name) {
        try {
            FileWriter myWriter = new FileWriter(leaderboardFilename, true);
            myWriter.write(name + "," + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
