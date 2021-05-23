package main.java;
import javax.swing.*;


import java.awt.*;

public class Field {
	private final int x, y;
	private final int width=10 , height = 10;
	private FieldStatus fieldStatus=FieldStatus.EMPTY;
	protected Image icon;
	
	public Field(int x, int y) {
		this.x = x*width;
		this.y = y*height;
		this.fieldStatus = FieldStatus.EMPTY;
	}
	
	public int GetX(){
		return x;
	}
	public int GetY(){
		return y;
	}
//	public Rectangle FieldRectangle()
//	{
//		return new Rectangle(x,y,width,height);
//	}
	
	public void changeFieldStatus(FieldStatus fieldStatus) {
		this.fieldStatus = fieldStatus;
	}
	
	public FieldStatus getFieldStatus() {
		return fieldStatus;
	}

	
}
