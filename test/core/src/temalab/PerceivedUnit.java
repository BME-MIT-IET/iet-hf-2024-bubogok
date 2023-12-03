package temalab;

public class PerceivedUnit {
    protected Position pos;
    protected Team team;

    public PerceivedUnit(Position p, Team t) {
        pos = p;
        team = t;
    }

    @Override
    public String toString() {
        return pos.toString() + " ; " + team.getName();
    }
}
