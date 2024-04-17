package temalab.model;

public class PerceivedUnit {
    protected String pos;
    protected String team;
    protected String type;

    public PerceivedUnit(String p, String t, String type) {
        pos = p;
        team = t;
        this.type = type;
    }

    @Override
    public String toString() {
        return pos + " " + team + " " + type;
    }
}
