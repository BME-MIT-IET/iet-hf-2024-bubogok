package temalab;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class Unit {
	private int ID;
	private Position pos;
	private int viewRange = 5;
	private Field[][] seenFields;
	private ArrayList<Unit> seenUnits;
	
	public Unit(Position pos) {
		seenFields = new Field[2*viewRange][2*viewRange];
		seenUnits = new ArrayList<Unit>();
		ID = Test.r.nextInt(1000000);
		this.pos = pos;
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = pos.screenCoords();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant()*size/2);
		sr.end();
	}

	//TODO: nem mozog,mert ...
	public void move(int x, int y) {
		
	}

	public void updatePercievedWorld() {
		seenFields = Map.instance().requestFileds(pos, 2*viewRange + 1);
		seenUnits = Map.instance().requestUnits(pos,  2*viewRange + 1);	
	}
	
	public int getUUID() {
		return this.ID;
	}

	public Position pos() {
		return pos;
	}
}