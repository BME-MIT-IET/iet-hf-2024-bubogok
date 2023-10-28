package temalab.test;


import java.util.*;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import temalab.test.Field.Type;

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
				fields[i][j] = new Field(new Vector(i, j), Type.FOREST);			
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
	public float squareSize() {
		return this.squareSize;
	}
	/*
	public List<Integer> getUUIDs(Team t) {
		List<Integer> asdf = new ArrayList<Integer>();
		for(var u : units) {
			if(u.getTeam().equals(t)) {
				
				asdf.add(u.getUUID());
			}
		}
		return asdf;
	}
	
	public Vehicle findByUUID(int id) {
		for(var u : units) {
			if(u.getUUID() == id) {
				return u;
			}
		}
		return null;
	}*/
}
