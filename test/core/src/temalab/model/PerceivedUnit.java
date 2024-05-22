package temalab.model;

public class PerceivedUnit {
	protected String pos;
	protected String team;
	protected String type;
	protected int id;
	protected int health;

	public PerceivedUnit(String p, String t, String type, int id, int hp) {
		pos = p;
		team = t;
		this.type = type;
		this.id = id;
		this.health = hp;
	}

	@Override
	public String toString() {
		return pos + " " + team + " " + type + " " + id + " " + health;
	}
}
