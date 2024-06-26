package temalab.model;

public final class Field {
	private Position pos;
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
	}

	public boolean arrive(Unit u) {
		if (unit != null && unit != u) {
			return false;
		}
		unit = u;
		return true;
	}

	public void leave() {
		unit = null;
	}

	public void takeShot(int damage) {
		if (unit != null) {
			unit.takeShot(damage);
		}
	}

	public boolean isNeighbouring(Field f) {
		return pos.isNeighbouring(f.pos);
	}

	public boolean inDistance(Field f, float radius) {
		return pos.inDistance(f.pos, radius);
	}

	public String toString() {
		return this.pos.toString() + " " + this.type.toString();
	}

	public Field.Type getType() {
		return type;
	}

	public Position pos() {
		return pos;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setPos(Position p) {
		this.pos = p;
	}
}