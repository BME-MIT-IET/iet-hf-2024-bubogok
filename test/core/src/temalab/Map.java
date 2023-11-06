package temalab;


import java.util.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import temalab.Field.Type;

public final class Map {
	private static Map instance;
	private int mapSize;
	private int numberOfSquares;
	private HashMap<Position, Field> fields;
	private ArrayList<Team> teams;
	private ArrayList<ControlPoint> controlPoints; 
	private float squareSize;
	private float universalDistanceConstant = 1.5f;
	
	public static Map instance() throws RuntimeException {
		if(instance == null) {
			throw new RuntimeException("not inited");
		}
		return instance;
	}
	
	public static Map init(int size, int nos) throws RuntimeException {
		if(instance == null) {
			instance = new Map(size, nos);
			return instance;
		} else {
			throw new RuntimeException("already inited");
		}
	}
	
	private Map(int size, int nos) {
		numberOfSquares = nos;
		fields = new HashMap<Position, Field>();
		teams = new ArrayList<Team>();
		controlPoints = new ArrayList<ControlPoint>();
		mapSize = size;
		squareSize = mapSize / numberOfSquares;
	}
	
	public void makeRandomMap() {
		for(int i = 0; i < numberOfSquares; i++) {
			for(int j = 0; j < numberOfSquares; j++) {
				var temPos = new Position(i, j);
				fields.put(temPos, new Field(temPos,
								Type.values()[new Random().nextInt(Type.values().length)]));			
			}
		}
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		fields.forEach((pos, f) -> {
			//f.render(sr, sb, bf);
		});
		for(var t : teams) {
			t.render(sr, sb, bf);
		}
		for(var cp : controlPoints) {
			cp.render(sr, sb, bf);
		}
	}
	
	public void addTeam(Team t) {
		teams.add(t);
	}

	public void addControlPoint(ControlPoint cp) {
		controlPoints.add(cp);
	}

	public ArrayList<Field> requestFileds(Position pos, float size) {
		var view = new ArrayList<Field>();
		fields.forEach((p, f) -> {
			if(pos.inDistance(p, size)) {
				view.add(f);
			}
		});
		return view;
	}
	
	public ArrayList<Unit> requestUnits(Position pos, float size) {
		var view = new ArrayList<Unit>();
		for(var t : teams) {
			view.addAll(t.requestUnits(pos, size));
		}
		return view;
	}
	
	private Boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < numberOfSquares && y < numberOfSquares;
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
