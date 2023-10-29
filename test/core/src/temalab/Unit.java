package temalab;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Unit {
	private int ID;
	private Position pos;
	
	public Unit(Position pos) {
		this.ID = Test.r.nextInt(1000000);
		this.pos = pos;
	}
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = pos.screenCoords();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.rect(center.x - size/2, center.y - size/2, size, size);
		sr.end();
	}
	
	public void move(int x, int y) {
		if((Math.abs(pos.x() - x) == 1 && pos.y() == y) 
			|| (Math.abs(pos.y() - y) == 1 && pos.x() == x)) {
			this.pos = new Position(x, y);			
		}
	}
	
	public int getUUID() {
		return this.ID;
	}
}