package temalab.model;

public class PerceivedUnit {
    protected String pos;
    protected String team;
    protected String type;
    protected int id;
    protected int health;

    public PerceivedUnit(String p, String t, String type, int ID, int hp) {
        pos = p;
        team = t;
        this.type = type;
        this.id = ID;
        this.health =  hp;
    }

    @Override
    public String toString() {
        return pos + " " + team + " " + type + " " + id + " " + health;
    }
}
