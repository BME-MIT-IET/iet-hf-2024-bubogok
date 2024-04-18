package temalab.common;

import java.util.ArrayList;
import java.util.List;
import temalab.model.*;
import temalab.model.Unit.Type;

public class MainModel {
    private Map m;
    private Team t1;
    private Team t2;
    private List<Team> teams;
    private List<MainModelListener> listeners;

    public MainModel(int nos) {
        t1 = new Team("white", 5000);
	    t2 = new Team("red", 5000);
        teams = new ArrayList<>();
        teams.add(t1);
        teams.add(t2);
        m = new Map(nos);
		m.makeAllGreenMap();
        m.addTeam(t1);
		m.addTeam(t2);
        listeners = new ArrayList<>();
        testUnits();
        testControlPoints();
    }

    private void testUnits() {
        t1.addUnit(new Unit(new Position(3, 3), t1, Type.INFANTRY));
        t2.addUnit(new Unit(new Position(0, 0), t2, Type.TANK));
    }

    private void testControlPoints() {
        m.addControlPoint(new ControlPoint(new Position(10, 10), 10, 3));
    }


    public void addListener(MainModelListener mml) {
        this.listeners.add(mml);
        mml.mapCreated(m);
        for(var u : t1.units().values()) {
            mml.unitCreated(u);
        }
        for(var u : t2.units().values()) {
            mml.unitCreated(u);
        }
    }

    public void removeistener(MainModelListener mml) {
        this.listeners.remove(mml);
    
    }

    public Team t1() {
        return t1;
    }

    public Team t2() {
        return t2;
    }

    public ArrayList<Unit> requestUnits(Position pos, float size) {
		var view = new ArrayList<Unit>();
		for (var t : teams) {
			view.addAll(t.requestUnits(pos, size));
		}
		return view;
	}

	public ArrayList<PerceivedUnit> requestPerceivedUnits(Position pos, float size) {
		var view = new ArrayList<PerceivedUnit>();
		for (var t : teams) {
			view.addAll(t.requestPerceivedUnits(pos, size));
		}
		return view;
	}
}