package main.java;
import java.awt.*;

enum FieldType {
	EMPTY,
	SNAKE,
	PYTHON,
	FROG,
	FRUIT,
	WALL
}

public class Field {
	private final int x, y;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	private FieldType type;
	protected Image icon;
	
	public Field(int x, int y) {
		this.x = x * WIDTH;
		this.y = y * HEIGHT;
		this.type = FieldType.EMPTY;
	}
	
	public int GetX(){
		return x;
	}

	public int GetY(){
		return y;
	}
	
	public void setType(FieldType type) {
		this.type = type;
	}
	
	public FieldType getType() {
		return type;
	}
}
