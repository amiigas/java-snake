package main.java;
import java.util.ArrayList;

/**
 * The type of board component.
 * @see BoardComponent
 */
enum ComponentType {
	SNAKE,
    PYTHON,
	FROG,
	FRUIT,
	WALL
}

/**
 * Contains indexes of row and column.
 */
class Coordinate {
    public int i;
    public int j;

    /**
     * Constructs a coordinate with the given indexes.
     * @param i Horizontal index
     * @param j Vertical index
     */
    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

/**
 * Abstract base class for every component on the board.
 * @see Board
 */
abstract class BoardComponent {
    public ArrayList<Coordinate> position;
    public ComponentType type;

    public BoardComponent() {}

    public abstract void spawn();
}
