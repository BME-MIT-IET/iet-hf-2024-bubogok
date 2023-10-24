package temalab.test;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Vehicle {
	private int ID;
	private Vector pos;
	
	public Vehicle(Vector pos) {
		this.ID = Test.r.nextInt(1000000);
		this.pos = pos;
	}
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = new Vector2((float)1.5 * size + 2 * pos.x() * size, 
				(float)1.5 * size + 2 * pos.y() * size);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.rect(center.x - size/2, 
				center.y - size/2, 
				size, 
				size);
		sr.end();
	}
	
	public void move(int x, int y) {
		this.pos = new Vector(x, y);
	}
	
	public int getUUID() {
		return this.ID;
	}
}