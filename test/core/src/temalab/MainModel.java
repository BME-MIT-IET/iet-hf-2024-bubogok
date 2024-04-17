package temalab;

import java.util.ArrayList;
import java.util.List;
import temalab.model.*;
import temalab.model.Unit.Type;
import temalab.common.MainModelListener;

public class MainModel {
    Map m;
    Team t1 = new Team("white", 5000);
	Team t2 = new Team("red", 5000);
    List<MainModelListener> listeners;
    private int nos = 32;

    public MainModel() {
        m = Map.init(nos);
		m.makeAllGreenMap();
        m.addTeam(t1);
		m.addTeam(t2);
        listeners = new ArrayList<>();
        t1.addUnit(new Unit(new Position(0, 0), t1, Type.TANK));
    }

    public void addListener(MainModelListener mml) {
        this.listeners.add(mml);
        mml.mapCreated(m);
        for(var u : t1.units().values()) {
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
}