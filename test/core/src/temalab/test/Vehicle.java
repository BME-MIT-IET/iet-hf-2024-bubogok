package temalab.test;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Vehicle {
	Field pos;
	Color color;
	Field target;
	float size = 20;
	
	
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
	
	public void move() {
		
	}
	
	public void setTarget(Field tar) {
		this.target = tar;
	}
	
	public void setPos(Field pos) {
		this.pos = pos;
	}

}
