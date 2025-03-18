package app.boardMaker.tools;

import java.util.Objects;

/**
 * Class that contains coordinates
 * */
public class Coordinates {

	private int x;
	private int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
		public String toString()
		{
			// TODO Auto-generated method stub
			return "(" + x + "," + y + ")";
		}
	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		Coordinates cmp = (Coordinates) obj;
		return x == cmp.getX() && y == cmp.getY();
	}
}
