package main.java;
import java.util.ArrayList;

enum ComponentType {
	SNAKE,
    PYTHON,
	FROG,
	FRUIT,
	WALL
}

class Coordinate {
    public int i;
    public int j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

abstract class BoardComponent {
    public ArrayList<Coordinate> position;
    public ComponentType type;

    public BoardComponent() {}

    public abstract void spawn();
}
