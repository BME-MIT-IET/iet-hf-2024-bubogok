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
	private ArrayList<Field> seenFields;
	private ArrayList<Unit> seenUnits;
	
	public Unit(Position pos) {
		seenFields = new ArrayList<Field>();
		seenUnits = new ArrayList<Unit>();
		ID = Test.r.nextInt(1000000);
		this.pos = pos;
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = pos.screenCoords();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.circle(center.x, center.y, size/2);
		sr.end();
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(c);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant() * viewRange * size);
		sr.end();
		if(seenFields != null) {
			for(var f : seenFields) {
				if(f != null) {
					f.render(sr, sb, bf);
				}
			}
		}
	}

	//TODO: nem mozog,mert ...
	public void move(int x, int y) {
		
	}

	public void update() {
		seenFields = Map.instance().requestFileds(pos, viewRange + 0.5f);
		seenUnits = Map.instance().requestUnits(pos,  viewRange + 0.5f);
		
	}
	
	public int getUUID() {
		return this.ID;
	}

	public Position pos() {
		return pos;
	}
}