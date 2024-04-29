package temalab.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import temalab.communicator.Communicator;
import temalab.communicator.MainCommunicatorListener;
import temalab.model.*;
import temalab.model.Unit.Type;
import temalab.util.SimplexNoise;

public class MainModel {
    //measured in fields
    private int mapSize;

    private Map<String, Team> teams;
	private Map<Position, Field> fields;
	private ArrayList<ControlPoint> controlPoints;
    private List<MainModelListener> listeners;
    private MainCommunicatorListener mcl;

    public MainModel(int w) {
        mapSize = w;
		fields = new HashMap<Position, Field>();
		controlPoints = new ArrayList<ControlPoint>();
        teams = new HashMap<>();
        teams.put("white", new Team("white", "dummy", 5000, this));
        teams.put("red", new Team("red","heuristic", 5000, this));
        listeners = new ArrayList<>();
        makeAllGreenMap();
        testUnits();
        testControlPoints();
    }

    private void testUnits() {
        new Unit(fields.get(new Position(10, 10)), teams.get("white"), Type.INFANTRY);
        new Unit(fields.get(new Position(0, 0)), teams.get("red"), Type.TANK);
    }

    private void testControlPoints() {
        controlPoints.add(new ControlPoint(new Position(10, 10), 10, 3, this));
    }

    private void makeSimplexNoiseMap() {
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				var temPos = new Position(i, j);
				var noiseProb = SimplexNoise.noise(i/5.0, j/5.0, 10);
				if (-1 < noiseProb && noiseProb <= 0) {
					fields.put(temPos, new Field(temPos, Field.Type.GRASS));
				} else if (0 < noiseProb && noiseProb <= 0.2) {
					fields.put(temPos, new Field(temPos, Field.Type.WATER));
				} else if (0.2 < noiseProb && noiseProb <= 0.4) {
					fields.put(temPos, new Field(temPos, Field.Type.FOREST));
				} else if (0.4 < noiseProb && noiseProb <= 0.6) {
					fields.put(temPos, new Field(temPos, Field.Type.BUILDING));
				} else if (0.6 < noiseProb && noiseProb <= 1) {
					fields.put(temPos, new Field(temPos, Field.Type.MARSH));
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Field.Type.GRASS));
			}
		}
		for (int i = mapSize - 1; i > mapSize - 3; i--) {
			for (int j = mapSize - 1; j > mapSize - 3; j--) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Field.Type.GRASS));
			}
		}

	}

	private void makeAllGreenMap() {
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				var temPos = new Position(i, j);
				fields.put(temPos, new Field(temPos, Field.Type.GRASS));
			}
		}
	}

    public void addListener(MainModelListener mml) {
        this.listeners.add(mml);

        for(var t : teams.values()) {
            for(var u : t.units().values()) {
                mml.unitCreated(u);
            }
        }
        for(var cp : controlPoints) {
            mml.controlPointCreated(cp);
        }
        for(var f : fields.values()) {
            mml.fieldCreated(f);
        }
        for(var t: teams.values()) {
            mml.teamCreated(t);
        }
    }

    public void removeistener(MainModelListener mml) {
        this.listeners.remove(mml);
    
    }

    public Team team(String name) {
        return teams.get(name);
    }

    public ArrayList<Unit> requestUnits(Position pos, float size) {
		var view = new ArrayList<Unit>();
		for (var t : teams.values()) {
			view.addAll(t.requestUnits(pos, size));
		}
		return view;
	}

	public ArrayList<PerceivedUnit> requestPerceivedUnits(Position pos, float size) {
		var view = new ArrayList<PerceivedUnit>();
		for (var t : teams.values()) {
			view.addAll(t.requestPerceivedUnits(pos, size));
		}
		return view;
	}

    public ArrayList<ControlPoint> requestControlPoints(Position pos, float size) {
		var view = new ArrayList<ControlPoint>();
		for (var cp : controlPoints) {
			if (pos.inDistance(cp.pos(), size)) {
				view.add(cp);
			}
		}
		return view;
	}

	public ArrayList<Field> requestFileds(Position pos, float size) {
		var view = new ArrayList<Field>();
		fields.forEach((p, f) -> {
			if (pos.inDistance(p, size) && pos.hashCode() != p.hashCode()) {
				view.add(f);
			}
		});
		return view;
	}

    public void ControlPointsUpdate() {
		for (var cp : controlPoints) {
			cp.updateNearbyUnits();
		}
	}

    public Field getField(Position pos) {
		return fields.get(pos);
	}

    public int width() {
        return mapSize;
    }

    public List<Team> getTeams() {
        return new ArrayList<Team>(teams.values());
    }
}