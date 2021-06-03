package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * Presents leaderboard as a sorted list of socres.
 * @see Score
 */
public class LeaderboardFrame extends JFrame {
    
    public static final int FRAME_WIDTH = 300;
    public static final int FRAME_HEIGTH = 400;
    public static final String leaderboardFilename = "leaderboard.txt";

    /**
     * Creates the frame.
     */
    LeaderboardFrame() {
        this.setTitle("Snake - Leaderboard");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.layoutComponents();
        this.setVisible(true);
    }

    /**
     * Creates the interface.
     */
    private void layoutComponents() {
        Score[] entries = getLeaderboardEntries();
        JList<Score> leaderboard = new JList<Score>(entries);
        leaderboard.setCellRenderer(new CellRenderer());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(leaderboard);
        scrollPane.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGTH);
        leaderboard.setLayoutOrientation(JList.VERTICAL);

        this.add(scrollPane);
    }

    /**
     * Reads scores from text file and sorts them.
     * @return Score[] Scores sorted in descending order.
     */
    private Score[] getLeaderboardEntries() {
        ArrayList<Score> scores = new ArrayList<Score>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(leaderboardFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                scores.add(new Score(data[0], Integer.parseInt(data[1])));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        scores.sort((o1, o2) -> -Integer.compare(o1.points, o2.points));
        Score[] result = new Score[ scores.size() ];
        scores.toArray(result);
        return result;
    }
}
