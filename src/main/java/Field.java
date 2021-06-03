package main.java;
import java.awt.*;

/**
 * The type of field.
 */
enum FieldType {
	EMPTY,
	SNAKE,
	PYTHON,
	FROG,
	FRUIT,
	WALL
}

/**
 * Building block of the board.
 */
public class Field {
	private final int x, y;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	private FieldType type;
	protected Image icon;
	
	/**
	 * Creates field of a given size.
	 * @param x Width
	 * @param y Height
	 */
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
	
	/**
	 * Sets the field type.
	 * @param type The type to set.
	 * @see FieldType
	 */
	public void setType(FieldType type) {
		this.type = type;
	}
	
	/**
	 * Gets the field type.
	 * @see FieldType
	 */
	public FieldType getType() {
		return type;
	}
}
