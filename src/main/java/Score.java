package main.java;

/**
 * Holds information about player name add its score
 */
public class Score {
    String name;
    int points;

    /**
     * Creates a score instance.
     * @param name Text representing player's name.
     * @param points Number of points 
     */
    Score(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
