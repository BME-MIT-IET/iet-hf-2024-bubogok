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
		float udc = Map.instance().universalDistanceConstant();
		return new Vector2((udc * size + udc * x * size), (udc * size + udc * y * size));
	}

	public boolean inDistance(Position p2, int dist) {
		float tempDist = dist / Map.instance().universalDistanceConstant() * Map.instance().squareSize();
		return this.screenCoords().dst(p2.screenCoords()) < tempDist;
	}
}