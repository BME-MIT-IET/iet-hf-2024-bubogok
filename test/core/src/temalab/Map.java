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
	private Field[][] fields;
	private ArrayList<Team> teams; 
	private float squareSize;
	
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
		this.numberOfSquares = nos;
		this.fields = new Field[numberOfSquares][numberOfSquares];
		this.teams = new ArrayList<Team>();
		this.mapSize = size;
		this.squareSize = mapSize / numberOfSquares;
	}
	
	public void makeRandomMap() {
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
				fields[i][j] = new Field(new Position(i, j),
								Type.values()[new Random().nextInt(Type.values().length)]);			
			}
		}
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
				fields[i][j].render(sr, sb, bf);
			}
		}
		for(var t : teams) {
			t.render(sr, sb, bf);
		}
	}
	
	public void addTeam(Team t) {
		teams.add(t);
	}

	public Field[][] requestFileds(Position pos, int size) {
		var view = new Field[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				var x = pos.x() + i - (size / 2);
				var y = pos.y() + j - (size / 2);
				if(pos.inDistance(x, y, size) && isValid(x, y)) {
					view[i][j] = fields[x][y];
				} 
			}
		}
		return view;
	}
	
	private Boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < numberOfSquares && y < numberOfSquares;
	}
	
	public ArrayList<Unit> requestUnits() {
		return null;
	}


	public float squareSize() {
		return squareSize;
	}
	public int numberOfSquares() {
		return numberOfSquares;
	}
}
