package temalab;

import com.badlogic.gdx.math.Vector2;

public final class Field {
	private Position pos;
	private Vector2 center;	
	private Type type;
	private Unit unit;

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
	}

	public boolean arrive(Unit u) {
		if(unit != null) {
			return false;
		}
		unit = u;
		return true;
	}

	public void leave() {
		unit = null;
	}

	public void takeShot(int damage) {
		if(unit != null) {
			unit.takeShot(damage);
		}
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