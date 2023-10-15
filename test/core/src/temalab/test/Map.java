package temalab.test;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import temalab.test.Field.Type;

public class Map {
	private int mapSize;
	private int numberOfSquares;
	private Field[][] fields;
	
	
	public Map(int size, int nos) {
		this.numberOfSquares = nos;
		this.fields = new Field[numberOfSquares][numberOfSquares];
		this.mapSize = size;
	}
	
	public void makeRandomMap() {
		for(int i = 0; i < fields.length / 2; i++) {
			for(int j = 0; j < fields[i].length/ 2; j++) {
				float squareSize = mapSize / numberOfSquares;
				fields[i][j] = new Field(new Vector2((float)1.5*squareSize + 2 * i * squareSize, (float)1.5*squareSize + 2 * j * squareSize), (int)squareSize, Type.FOREST);			}
			
		}
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		for(int i = 0; i < fields.length / 2; i++) {
			for(int j = 0; j < fields[i].length / 2; j++) {
				fields[i][j].render(sr, sb, bf);
			}
			
		}
	}
	public void addVehicle(Vehicle v, int x, int y) {
		fields[x][y].addCurrent(v);
		v.setPos(fields[x][y]);
	}
}
