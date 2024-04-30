package temalab.model;

public class PerceivedUnit {
    protected String pos;
    protected String team;
    protected String type;
    protected int id;

    public PerceivedUnit(String p, String t, String type, int ID) {
        pos = p;
        team = t;
        this.type = type;
        this.id = ID;
    }

    @Override
    public String toString() {
        return pos + " " + team + " " + type + " " + id;
    }
}
