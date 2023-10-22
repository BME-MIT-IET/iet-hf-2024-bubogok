package temalab.test;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public final class Field {
	private Vector pos;
	private Vector2 center;	
	private Color color;
	private Type type;
	
	public enum Type {
		GRASS,
		WATER,
		FOREST,
		BUILDING
	}
	
	public Field(Vector pos, Type t) {
		this.pos = pos;
		float size = Map.instance().squareSize();
		this.center = new Vector2((float)1.5 * size + 2 * pos.x() * size, 
								  (float)1.5 * size + 2 * pos.y() * size);
		this.type = t;
		
		/*
		if(t == Type.GRASS) {
			this.color = new Color(0, 1, 0, 1);
		} else if(t == Type.WATER) {
			this.color = new Color(0, 0, 1, 1);
		} else if(t == Type.FOREST) {
			this.color = new Color(0.11f, 0.5f, 0.11f, 1);
		} else if(t == Type.BUILDING) {
			this.color = new Color(0.2f, 0.2f, 0.2f, 1);
		}
		*/
		
		Random rnd = new Random();
		float r = rnd.nextFloat();
		this.color = new Color(r, r, r, 1);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		float size = Map.instance().squareSize();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(this.color);
		sr.rect(this.center.x - size/2,
				this.center.y - size/2, 
				size, 
				size);
		sr.end();
	}
	
	public Vector2 getCenter() {
		return this.center;
	}
}