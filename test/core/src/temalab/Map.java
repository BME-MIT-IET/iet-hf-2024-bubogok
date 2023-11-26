package temalab;

import java.util.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import temalab.Field.Type;

public final class Map {
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
			instance.makeSimplexNoiseMap();
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
		
	}

	// private void makeRandomMap() {
	// 	for (int i = 0; i < numberOfSquares; i++) {
	// 		for (int j = 0; j < numberOfSquares; j++) {
	// 			var temPos = new Position(i, j);
	// 			fields.put(temPos, new Field(temPos, Type.values()[new Random().nextInt(Type.values().length)]));
	// 		}
	// 	}
	// }

	private void makeSimplexNoiseMap() {
		for (int i = 0; i < numberOfSquares; i++) {
			for (int j = 0; j < numberOfSquares; j++) {
				var temPos = new Position(i, j);
				var noiseProb = SimplexNoise.noise(i, j);
				if(-1 < noiseProb && noiseProb <= 0) {
					fields.put(temPos, new Field(temPos, Type.GRASS));
				} else if(0 < noiseProb && noiseProb <= 0.2) {
					fields.put(temPos, new Field(temPos, Type.WATER));
				} else if(0.2 < noiseProb && noiseProb <= 0.4) {
					fields.put(temPos, new Field(temPos, Type.FOREST));
				} else if(0.4 < noiseProb && noiseProb <= 0.6) {
					fields.put(temPos, new Field(temPos, Type.BUILDING));
				} else if(0.6 < noiseProb && noiseProb <= 1) {
					fields.put(temPos, new Field(temPos, Type.MARSH));
				}
			}
		}
	}

	public void render(ShapeRenderer sr, SpriteBatch sb) {
		var renderFields = new HashMap<Position, Field>();
		renderFields.putAll(fields);
		renderFields.forEach((pos, f) -> {
			f.render(sr, sb);
		});
		var renderTeams = new ArrayList<Team>();
		renderTeams.addAll(teams);
		for (var t : renderTeams) {
			t.render(sr, sb);
		}
		var renderControlPoints = new ArrayList<ControlPoint>();
		renderControlPoints.addAll(controlPoints);
		for (var cp : renderControlPoints) {
			cp.render(sr, sb);
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
			if (pos.inDistance(p, size)) {
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

	public ArrayList<UnitView> requestUnitViews(Position pos, float size) {
		var view = new ArrayList<UnitView>();
		for (var t : teams) {
			view.addAll(t.requestUnitViews(pos, size));
		}
		return view;
	}

	public boolean validateMove(ArrayList<Field.Type> steppables, Position currPos, Position destPos) {
		if(currPos.isNeighbouring(destPos)) {
			Field destPosField = fields.get(destPos);
			if(destPosField != null && steppables.contains(destPosField.getType())) {
				return true;
			}
		}
		return false;
	}

	public void makeShot(int damage, Position p) {
		for(var t : teams) {
			t.makeShot(damage, p);
		}
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
}
