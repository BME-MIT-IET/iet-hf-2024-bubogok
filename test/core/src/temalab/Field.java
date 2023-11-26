package temalab;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public final class Field {
	private Position pos;
	private Vector2 center;	
	private Color color;
	private Type type;
	
	public enum Type {
		GRASS,
		WATER,
		FOREST,
		BUILDING,
		MARSH
	}
	
	public Field(Position pos, Type t) {
		type = t;
		this.pos = pos;
		this.center = pos.screenCoords();
		if(t == Type.GRASS) {
			this.color = new Color(0, 1, 0, 1);
		} else if(t == Type.WATER) {
			this.color = new Color(0, 0, 1, 1);
		} else if(t == Type.FOREST) {
			this.color = new Color(0.11f, 0.5f, 0.11f, 1);
		} else if(t == Type.BUILDING) {
			this.color = new Color(0.5f, 0.5f, 0.5f, 1);
		} else if(t == Type.MARSH) {
			this.color = new Color(0.47f, 0.35f, 0.23f, 1);
		}
		
		/*
		Random rnd = new Random();
		float r = rnd.nextFloat();
		this.color = new Color(r, r, r, 1);*/
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		float size = Map.instance().squareSize();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(this.color);
		sr.rect(this.center.x - size/2, this.center.y - size/2,	size, size);
		sr.end();
	}
	
	public Vector2 getCenter() {
		return this.center;
	}
	public String toString() {
		return this.pos.toString();
	}

	public Field.Type getType() {
		return type;
	}
	public Position pos() {
		return pos;
	}
}