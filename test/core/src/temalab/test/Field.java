package temalab.test;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Field {
	private Vector2 center;
	private int size;
	private Color color;
	private Vehicle current;
	private Type type;
	
	public enum Type {
		GRASS,
		WATER,
		FOREST
	}
	
	public Field(Vector2 pos, int s, Type t) {
		this.current = null;
		this.center = pos;
		this.size = s;
		this.type = t;
		
		/*
		if(t == Type.GRASS) {
			this.color = new Color(0, 1, 0, 1);
		} else if(t == Type.WATER) {
			this.color = new Color(0, 0, 1, 1);
		} else if(t == Type.FOREST) {
			this.color = new Color(0.11f, 0.5f, 0.11f, 1);
		} */
		
		Random rnd = new Random();
		float r = rnd.nextFloat();
		this.color = new Color(r, r, r, 1);
		
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		if(this.current == null) {
			sr.begin(ShapeRenderer.ShapeType.Filled);
			sr.setColor(this.color);
			sr.rect(this.center.x - this.size/2, this.center.y - this.size/2, this.size, this.size);
			sr.end();
		} else {
			current.render(sr, sb, bf);
		}
	}
	
	public Vector2 getCenter() {
		return this.center;
	}
	
	public void addCurrent(Vehicle v) {
		this.current = v;
	}
}
