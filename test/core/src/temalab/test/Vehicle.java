package temalab.test;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.*;

public class Vehicle {
	private Vector pos;
	private Team team;	
	
	public Vehicle(Vector pos, Team team) {
		this.pos = pos;
		this.team = team;
	}
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		float size = Map.instance().squareSize();
		Vector2 center = new Vector2((float)1.5 * size + 2 * pos.x() * size, 
				(float)1.5 * size + 2 * pos.y() * size);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(this.team.getColor());
		sr.rect(center.x - size/2, 
				center.y - size/2, 
				size, 
				size);
		sr.end();
	}
	
	public void move(int x, int y) {
		
	}
}