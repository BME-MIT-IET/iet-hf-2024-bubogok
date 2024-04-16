package temalab;

import java.util.*;

import temalab.Field.Type;

public final class Map {
	public Random r;
	private static Map instance;
	private int numberOfSquares;
	private HashMap<Position, Field> fields;
	private ArrayList<Team> teams;
	private ArrayList<ControlPoint> controlPoints;
	private float squareSize;
	private float universalDistanceConstant;

	public static Map instance() throws RuntimeException {
		if (instance == null) {
			throw new RuntimeException("not inited");
		}
		return instance;
	}

	public static Map init(int size, int nos, float sizingFactor) throws RuntimeException {
		if (instance == null) {
			instance = new Map(size, nos, sizingFactor);
			return instance;
		} else {
			throw new RuntimeException("already inited");
		}
	}

	private Map(int size, int nos, float sizingFactor) {
		fields = new HashMap<Position, Field>();
		teams = new ArrayList<Team>();
		controlPoints = new ArrayList<ControlPoint>();
		universalDistanceConstant = sizingFactor;
		numberOfSquares = nos;
		squareSize = (size / sizingFactor) / numberOfSquares;
		r = new Random();
	}

	public void makeSimplexNoiseMap() {
		for (int i = 0; i < numberOfSquares; i++) {
			for (int j = 0; j < numberOfSquares; j++) {
				var temPos = new Position(i, j);
				var noiseProb = SimplexNoise.noise(i/5.0, j/5.0, 10);
				if (-1 < noiseProb && noiseProb <= 0) {
					fields.put(temPos, new Field(temPos, Type.GRASS));
				} else if (0 < noiseProb && noiseProb <= 0.2) {
					fields.put(temPos, new Field(temPos, Type.WATER));
				} else if (0.2 < noiseProb && noiseProb <= 0.4) {
					fields.put(temPos, new Field(temPos, Type.FOREST));
				} else if (0.4 < noiseProb && noiseProb <= 0.6) {
					fields.put(temPos, new Field(temPos, Type.BUILDING));
				} else if (0.6 < noiseProb && noiseProb <= 1) {
					fields.put(temPos, new Field(temPos, Type.MARSH));
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Type.GRASS));
			}
		}
		for (int i = numberOfSquares - 1; i > numberOfSquares - 3; i--) {
			for (int j = numberOfSquares - 1; j > numberOfSquares - 3; j--) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Type.GRASS));
			}
		}

	}

	public void makeAllGreenMap() {
		for (int i = 0; i < numberOfSquares; i++) {
			for (int j = 0; j < numberOfSquares; j++) {
				var temPos = new Position(i, j);
				fields.put(temPos, new Field(temPos, Type.GRASS));
			}
		}
	}

	public void addTeam(Team t) {
		teams.add(t);
	}

	public void addControlPoint(ControlPoint cp) {
		controlPoints.add(cp);
	}

	public void ControlPointsUpdate() {
		for (var cp : controlPoints) {
			cp.updateNearbyUnits();
		}
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

	public void addField(Field f) {
		fields.put(f.pos(), f);
	}

	public Field getField(Position pos) {
		return fields.get(pos);
	}

	public float squareSize() {
		return squareSize;
	}

	public int numberOfSquares() {
		return numberOfSquares;
	}

	public float universalDistanceConstant() {
		return universalDistanceConstant;
	}

	public ArrayList<Field> giveFields() {
		ArrayList<Field> res = new ArrayList<Field>();
		fields.forEach((p, f) -> {
			res.add(f);
		});
		return res;
	}

	public void registerListener(MapView mapView) {
		var res = new ArrayList<Field>();
		fields.forEach((p, f) -> {
			res.add(f);
		});
		mapView.fields(res);
	}
}
