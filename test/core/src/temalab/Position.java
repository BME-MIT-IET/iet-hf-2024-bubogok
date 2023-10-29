package temalab;

import com.badlogic.gdx.math.Vector2;

public final class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public String toString() {
		return "x: " + x + " y: " + y;
	}
	public Vector2 screenCoords() {
		float size = Map.instance().squareSize();
		return new Vector2((float)(1.5 * size + 1.5 * x * size), 
				  			(float)(1.5 * size + 1.5 * y * size));
	}
}
