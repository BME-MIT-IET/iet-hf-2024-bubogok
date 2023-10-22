package temalab.test;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Vehicle {
	private Field pos;
	private Color color;
	private float size = 20;	
	
	public Vehicle(String team) {
		if(team == "green") {
			this.color = new Color(0, 1, 0, 1);
		} else if(team == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(.8f, .8f, .8f, 1);
		}
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(this.color);
		sr.rect(this.pos.getCenter().x - this.size/2, this.pos.getCenter().y - this.size/2, this.size, this.size);
		sr.end();
	}
	
	public void move(int x, int y) {
		
	}
	
	public void setPos(Field pos) {
		this.pos = pos;	
	}
}
