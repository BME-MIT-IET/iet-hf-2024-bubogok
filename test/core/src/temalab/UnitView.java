package temalab;

public class UnitView {
    protected Position pos;
    protected Team team;

    public UnitView(Position p, Team t) {
        pos = p;
        team = t;
    }

    @Override
    public String toString() {
        return pos.toString() + " ; " + team.getName();
    }
}
