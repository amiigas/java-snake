package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class LeaderboardFrame extends JFrame {
    
    public static final int FRAME_WIDTH = 200;
    public static final int FRAME_HEIGTH = 400;
    public static final String leaderboardFilename = "leaderboard.txt";

    JList leaderboard;

    LeaderboardFrame() {
        this.setTitle("Snake - Leaderboard");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.layoutComponents();
        this.setVisible(true);
    }

    private void layoutComponents() {
        String[] entries = getLeaderboardEntries();
        JList<String> list = new JList<String>(entries);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGTH);
        list.setLayoutOrientation(JList.VERTICAL);

        this.add(scrollPane);
    }

    private String[] getLeaderboardEntries() {
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(leaderboardFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                // String[] data = line.split(",");
                lines.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] result = new String[ lines.size() ];
        lines.toArray(result);
        return result;
    }
}
