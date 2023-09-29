package temalab.test;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Vehicle {
	Vector2 pos;
	Color color;
	Vector2 target;
	float size = 20;
	float speed, direction;
	
	
	public Vehicle(Vector2 pos, String team) {
		this.pos = pos;
		this.target = pos;
		this.speed = 0;
		this.direction = 0;
		if(team == "green") {
			this.color = new Color(0, 255, 0, 255);
		} else if(team == "red") {
			this.color = new Color(255, 0, 0, 255);
		} else {
			this.color = new Color(100, 100, 100, 255);
		}
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		sr.begin(ShapeRenderer.ShapeType.Filled);
		if(!this.nearlyEquals(target)) {
			this.move();
			sr.setColor(new Color(255, 255, 255, 255));
			sr.line(pos, target);
		}
		sr.setColor(this.color);
		sr.rect(this.pos.x - this.size/2, this.pos.y - this.size/2, this.size, this.size);
		sr.end();
	}
	
	public void move() {
		//Vector2 heading = this.target.sub(this.pos);
		//this.pos.add(heading);
		
	}
	
	public void setTarget(Vector2 tar) {
		this.target = tar;
	}
	
	public boolean nearlyEquals(Vector2 target) {
		return pos.epsilonEquals(target, 1.5f*size);
	}
}
