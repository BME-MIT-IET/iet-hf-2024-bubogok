package temalab;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class Team {
	private Color color;
	private HashMap<Integer, Unit> units;
	
	public Team(String name) {
		units = new HashMap<Integer, Unit>();
		if(name == "green") {
			this.color = new Color(0, 1, 0, 1);
		} else if(name == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(0f, 0f, 0f, 1);
		}
	}
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		units.forEach((id, u) -> {
			u.render(sr, sb, bf, color);
		});
	}
	public Color getColor() {
		return this.color;
	}
	public void addUnit(Unit v) {
		this.units.put(v.getUUID(), v);
	}
	public HashMap<Integer, Unit> units() {
		return this.units;
	}
	public ArrayList<Integer> unitIDs() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		units.forEach((id, u) -> {
			ids.add(u.getUUID());
		});
		return ids;
	}
	
	//TODO: ki kellene javítani, ez így végtelen bohóckodás
	//TODO: parsolni csak a TeamLeaderben kellene
	public void doAction(String[] answer) {
		
		
		Unit v = units.get(Integer.parseInt(answer[0]));
		Position vec = new Position(Integer.parseInt(answer[1]), Integer.parseInt(answer[2]));
		v.move(vec.x(), vec.y());
	}
}
